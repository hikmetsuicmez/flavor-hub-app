package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.menu.dtos.MenuDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Menu API", description = "Endpoints for menu management and operations")
public interface MenuApiDocs {

    @Operation(summary = "Create a new menu item", description = "Creates a new menu item with the provided details and image file.")
    @ApiResponse(responseCode = "200", description = "Menu item created successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<MenuDTO>> createMenu(@Parameter MenuDTO menuDTO, @Parameter MultipartFile imageFile);

    @Operation(summary = "Update an existing menu item", description = "Updates the details of an existing menu item, optionally with a new image file.")
    @ApiResponse(responseCode = "200", description = "Menu item updated successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<MenuDTO>> updateMenu(@Parameter MenuDTO menuDTO, @Parameter MultipartFile imageFile);

    @Operation(summary = "Get a menu item by ID", description = "Retrieves a menu item by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Menu item retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "id", description = "Unique identifier of the menu item", required = true)
    ResponseEntity<Response<MenuDTO>> getMenuById(@Parameter Long id);

    @Operation(summary = "Delete a menu item", description = "Deletes a menu item by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Menu item deleted successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "id", description = "Unique identifier of the menu item to be deleted", required = true)
    ResponseEntity<Response<?>> deleteMenu(@Parameter Long id);

    @Operation(summary = "Get a list of menu items", description = "Retrieves a list of menu items, optionally filtered by category ID or search term.")
    @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "categoryId", description = "Optional category ID to filter menu items", required = false)
    @Parameter(name = "search", description = "Optional search term to filter menu items by name or description", required = false)
    ResponseEntity<Response<List<MenuDTO>>> getMenus(@Parameter Long categoryId, @Parameter String search);
}
