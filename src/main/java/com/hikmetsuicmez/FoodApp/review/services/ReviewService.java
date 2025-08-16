package com.hikmetsuicmez.FoodApp.review.services;

import com.hikmetsuicmez.FoodApp.response.Response;
import com.hikmetsuicmez.FoodApp.review.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {

    Response<ReviewDTO> createReview(ReviewDTO reviewDTO);
    Response<List<ReviewDTO>> getReviewsForMenu(Long menuId);
    Response<Double> getAverageRating(Long menuId);
}
