package com.hikmetsuicmez.FlavorHub.category.services;

import com.hikmetsuicmez.FlavorHub.category.dtos.CategoryDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;

import java.util.List;

public interface CategoryService {

    Response<CategoryDTO> createCategory(CategoryDTO categoryDTO);
    Response<CategoryDTO> updateCategory(CategoryDTO categoryDTO);
    Response<CategoryDTO> getCategoryById(Long id);
    Response<List<CategoryDTO>> getAllCategories();
    Response<?> deleteCategory(Long id);
}
