package com.example.catsapi.controller;

import com.example.catsapi.entity.CatImage;
import com.example.catsapi.response.ResponseAPI;
import com.example.catsapi.service.CatImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cat-images")
@RequiredArgsConstructor
@Tag(name = "Cat Images API")
public class CatImageController {

    @Autowired
    CatImageService catImageService;

    @PostMapping
    public CompletableFuture<ResponseEntity<ResponseAPI<CatImage>>> uploadCatImage(@RequestParam("file") MultipartFile file) {
        return catImageService.saveCatImage(file)
                .thenApply(savedImage -> ResponseEntity.ok(ResponseAPI.success(savedImage)))
                .exceptionally(ex -> {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error uploading image: " + ex.getMessage(),
                            ex
                    );
                });
    }


    @GetMapping
    @Operation(summary = "Get All Cat Images")
    public CompletableFuture<ResponseEntity<ResponseAPI<List<CatImage>>>> getAllCatImages() {
        return catImageService.getAllCatImages()
                .thenApply(catImages -> {
                    if (catImages.isEmpty()) {
                        // Handle case when no images are found
                        return ResponseEntity.ok(new ResponseAPI<>("No cat images found", catImages));
                    }
                    // Return the response with the list of images
                    return ResponseEntity.ok(new ResponseAPI<>("Cat images retrieved successfully", catImages));
                })
                .exceptionally(ex -> {
                    // Handle exceptions during asynchronous processing
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error retrieving cat images",
                            ex
                    );
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Cat Image")
    public CompletableFuture<ResponseEntity<ResponseAPI<Object>>> deleteCatImage(@PathVariable String id) {
        return catImageService.deleteCatImage(id)
                .thenApply(v -> ResponseEntity.ok(ResponseAPI.success(null)))
                .exceptionally(ex -> {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error deleting cat image",
                            ex
                    );
                });
    }


    @PostMapping("/populate")
    @Operation(summary = "Populate Cat Images from API")
    public CompletableFuture<ResponseEntity<ResponseAPI<List<CatImage>>>> populateCatImages() {
        return catImageService.populateCatImages()
                .thenApply(images -> ResponseEntity.ok(ResponseAPI.success(images)));
    }
}
