package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.category.dtos.CategoryDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Category API", description = "Endpoints for category management")
public interface CategoryApiDocs {

    @Operation(summary = "Create a new category", description = "Creates a new category with the provided details.")
    @ApiResponse(responseCode = "200", description = "Category created successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - Only admins can create categories", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<CategoryDTO>> createCategory(@Parameter CategoryDTO categoryDTO);

    @Operation(summary = "Update an existing category", description = "Updates the details of an existing category.")
    @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - Only admins can update categories", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<CategoryDTO>> updateCategory(@Parameter CategoryDTO categoryDTO);

    @Operation(summary = "Get category by ID", description = "Retrieves a category by its ID.")
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the category to retrieve", required = true)
    ResponseEntity<Response<CategoryDTO>> getCategoryById(@Parameter Long id);

    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories.")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "No categories found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - Access denied", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<List<CategoryDTO>>> getAllCategories();

    @Operation(summary = "Delete a category", description = "Deletes a category by its ID.")
    @ApiResponse(responseCode = "200", description = "Category deleted successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - Only admins can delete categories", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the category to delete", required = true)
    ResponseEntity<Response<?>> deleteCategory(@Parameter Long id);
}
