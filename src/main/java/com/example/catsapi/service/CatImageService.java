package com.example.catsapi.service;

import com.example.catsapi.config.MultipartInputStreamFileResource;
import com.example.catsapi.dto.CatImageDto;
import com.example.catsapi.entity.CatImage;
import com.example.catsapi.exception.AnimalClassificationException;
import com.example.catsapi.repository.CatImageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatImageService {

    @Autowired
    CatImageRepository catImageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${catapi.upload.url}")
    private String uploadUrl;

    @Value("${catapi.search.url}")
    private String searchUrl;

    @Value("${catapi.key}")
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(CatImageService.class);


    @Async
    @Transactional
    public CompletableFuture<CatImage> saveCatImage(MultipartFile imageFile) {
        try {
            logger.info("Starting the upload process for image: {}", imageFile.getOriginalFilename());

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Prepare the request body with the file
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new MultipartInputStreamFileResource(imageFile.getInputStream(), imageFile.getOriginalFilename()));

            // Create the request entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send the POST request to the Cat API
            ResponseEntity<CatImageDto> response = restTemplate.postForEntity(uploadUrl, requestEntity, CatImageDto.class);

            // Log the response status
            logger.info("Cat API response status: {}", response.getStatusCode());

            // Validate response
            if (response.getStatusCode() != HttpStatus.CREATED || response.getBody() == null) {
                logger.error("Failed to upload image to Cat API. Response body: {}", response.getBody());
                throw new AnimalClassificationException("Failed to upload image to Cat API.");
            }

            // Process the response
            CatImageDto imageDto = response.getBody();

            // Save the image metadata to the database
            CatImage catImage = new CatImage();
            catImage.setId(imageDto.getId());
            catImage.setUrl(imageDto.getUrl());
            catImage.setWidth(imageDto.getWidth());
            catImage.setHeight(imageDto.getHeight());

            // Save the entity in the repository
            catImage = catImageRepository.save(catImage);

            logger.info("Image saved successfully with ID: {}", catImage.getId());

            // Return the saved entity wrapped in a CompletableFuture
            return CompletableFuture.completedFuture(catImage);

        } catch (Exception e) {
            logger.error("Error occurred while uploading image: {}", e.getMessage(), e);
            throw new AnimalClassificationException("Failed to save image: " + e.getMessage(), e);
        }
    }


    public CompletableFuture<List<CatImage>> getAllCatImages() {
        try {
            // Fetch all images from the database asynchronously
            List<CatImage> catImages = catImageRepository.findAll();
            return CompletableFuture.completedFuture(catImages);
        } catch (Exception e) {
            throw new AnimalClassificationException("Failed to fetch cat images: " + e.getMessage(), e);
        }
    }



    @Async
    @Transactional
    public CompletableFuture<Void> deleteCatImage(String id) {
        catImageRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional
    public CompletableFuture<List<CatImage>> populateCatImages() {
        CatImageDto[] images = restTemplate.getForObject(
                searchUrl + "?limit=10",
                CatImageDto[].class
        );

        if (images == null) {
            throw new AnimalClassificationException("No images found from Cat API");
        }

        List<CatImage> savedImages = List.of(images).stream()
                .map(dto -> {
                    CatImage catImage = new CatImage();
                    catImage.setId(dto.getId());
                    catImage.setUrl(dto.getUrl());
                    catImage.setWidth(dto.getWidth());
                    catImage.setHeight(dto.getHeight());
                    return catImage;
                })
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(
                savedImages.stream()
                        .map(catImageRepository::save)
                        .collect(Collectors.toList())
        );
    }

}