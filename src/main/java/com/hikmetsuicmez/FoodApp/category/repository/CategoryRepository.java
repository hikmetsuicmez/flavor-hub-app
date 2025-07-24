package com.hikmetsuicmez.FoodApp.category.repository;

import com.hikmetsuicmez.FoodApp.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    // Method to check if a category exists by its name
    boolean existsByName(String name);
}
