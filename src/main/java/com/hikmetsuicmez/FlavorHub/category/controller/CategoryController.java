package com.hikmetsuicmez.FlavorHub.category.controller;

import com.hikmetsuicmez.FlavorHub.category.dtos.CategoryDTO;
import com.hikmetsuicmez.FlavorHub.category.services.CategoryService;
import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.CategoryApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Category.BASE)
public class CategoryController implements CategoryApiDocs {

    private final CategoryService categoryService;

    @PostMapping(ApiEndpoints.Category.CREATE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<CategoryDTO>> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping(ApiEndpoints.Category.UPDATE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<CategoryDTO>> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO));
    }

    @GetMapping(ApiEndpoints.Category.GET_BY_ID)
    public ResponseEntity<Response<CategoryDTO>> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping(ApiEndpoints.Category.GET_ALL)
    public ResponseEntity<Response<List<CategoryDTO>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping(ApiEndpoints.Category.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
}
