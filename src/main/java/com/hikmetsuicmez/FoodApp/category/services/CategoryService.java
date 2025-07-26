package com.hikmetsuicmez.FoodApp.category.services;

import com.hikmetsuicmez.FoodApp.category.dtos.CategoryDTO;
import com.hikmetsuicmez.FoodApp.response.Response;

import java.util.List;

public interface CategoryService {

    Response<CategoryDTO> createCategory(CategoryDTO categoryDTO);
    Response<CategoryDTO> updateCategory(CategoryDTO categoryDTO);
    Response<CategoryDTO> getCategoryById(Long id);
    Response<List<CategoryDTO>> getAllCategories();
    Response<?> deleteCategory(Long id);
}
