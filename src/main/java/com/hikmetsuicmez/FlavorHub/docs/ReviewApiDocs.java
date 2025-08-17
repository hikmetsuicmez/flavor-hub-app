package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.response.Response;
import com.hikmetsuicmez.FlavorHub.review.dtos.ReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Review API", description = "Endpoints for review management")
public interface ReviewApiDocs {

    @Operation(summary = "Create a new review", description = "Allows authenticated users to create a review for a menu item.")
    @ApiResponse(responseCode = "200", description = "Review created successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<ReviewDTO>> createReview(@Parameter ReviewDTO reviewDTO);

    @Operation(summary = "Get reviews for a menu item", description = "Retrieves all reviews for a specific menu item.")
    @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "menuId", description = "ID of the menu item to retrieve reviews for", required = true, schema = @Schema(type = "integer"))
    ResponseEntity<Response<List<ReviewDTO>>> getReviewsForMenu(@Parameter Long menuId);

    @Operation(summary = "Get average rating for a menu item", description = "Calculates the average rating for a specific menu item based on its reviews.")
    @ApiResponse(responseCode = "200", description = "Average rating retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "menuId", description = "ID of the menu item to calculate average rating for", required = true, schema = @Schema(type = "integer"))
    ResponseEntity<Response<Double>> getAverageRating(@Parameter Long menuId);
}
