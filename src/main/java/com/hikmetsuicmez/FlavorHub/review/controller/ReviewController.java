package com.hikmetsuicmez.FlavorHub.review.controller;

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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewApiDocs {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<ReviewDTO>> createReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @GetMapping("/menu-item/{menuId}")
    public ResponseEntity<Response<List<ReviewDTO>>> getReviewsForMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getReviewsForMenu(menuId));
    }

    @GetMapping("/menu-item/{menuId}/average-rating")
    public ResponseEntity<Response<Double>> getAverageRating(@PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getAverageRating(menuId));
    }
}
