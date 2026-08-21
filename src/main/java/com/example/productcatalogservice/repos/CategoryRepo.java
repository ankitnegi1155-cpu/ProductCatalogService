package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);

    @Override
    List<Category> findAll();
}
