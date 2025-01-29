package com.example.catsapi.repository;

import com.example.catsapi.entity.CatImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatImageRepository extends JpaRepository<CatImage, String> {
}