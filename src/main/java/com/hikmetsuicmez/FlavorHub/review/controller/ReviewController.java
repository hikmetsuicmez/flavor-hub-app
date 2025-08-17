package com.hikmetsuicmez.FlavorHub.review.controller;

import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.ReviewApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import com.hikmetsuicmez.FlavorHub.review.dtos.ReviewDTO;
import com.hikmetsuicmez.FlavorHub.review.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.Review.BASE)
@RequiredArgsConstructor
public class ReviewController implements ReviewApiDocs {

    private final ReviewService reviewService;

    @PostMapping(ApiEndpoints.Review.CREATE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<ReviewDTO>> createReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @GetMapping(ApiEndpoints.Review.GET_BY_MENU_ID)
    public ResponseEntity<Response<List<ReviewDTO>>> getReviewsForMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getReviewsForMenu(menuId));
    }

    @GetMapping(ApiEndpoints.Review.GET_AVERAGE_RATING)
    public ResponseEntity<Response<Double>> getAverageRating(@PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getAverageRating(menuId));
    }
}
