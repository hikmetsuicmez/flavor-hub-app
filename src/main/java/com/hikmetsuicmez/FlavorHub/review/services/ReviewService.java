package com.hikmetsuicmez.FlavorHub.review.services;

import com.hikmetsuicmez.FlavorHub.response.Response;
import com.hikmetsuicmez.FlavorHub.review.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {

    Response<ReviewDTO> createReview(ReviewDTO reviewDTO);
    Response<List<ReviewDTO>> getReviewsForMenu(Long menuId);
    Response<Double> getAverageRating(Long menuId);
}
