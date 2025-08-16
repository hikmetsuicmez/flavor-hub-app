package com.hikmetsuicmez.FoodApp.review.services;

import com.hikmetsuicmez.FoodApp.auth_users.entity.User;
import com.hikmetsuicmez.FoodApp.auth_users.services.UserService;
import com.hikmetsuicmez.FoodApp.enums.OrderStatus;
import com.hikmetsuicmez.FoodApp.exceptions.BadRequestException;
import com.hikmetsuicmez.FoodApp.exceptions.NotFoundException;
import com.hikmetsuicmez.FoodApp.menu.entity.Menu;
import com.hikmetsuicmez.FoodApp.menu.repository.MenuRepository;
import com.hikmetsuicmez.FoodApp.order.entity.Order;
import com.hikmetsuicmez.FoodApp.order.repository.OrderItemRepository;
import com.hikmetsuicmez.FoodApp.order.repository.OrderRepository;
import com.hikmetsuicmez.FoodApp.response.Response;
import com.hikmetsuicmez.FoodApp.review.dtos.ReviewDTO;
import com.hikmetsuicmez.FoodApp.review.entity.Review;
import com.hikmetsuicmez.FoodApp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Response<ReviewDTO> createReview(ReviewDTO reviewDTO) {
        log.info("Inside createReview()");

        User user = userService.getCurrentLoggedInUser();

        if (reviewDTO.getOrderId() == null || reviewDTO.getMenuId() == null) {
            throw new BadRequestException("Order ID and Menu Item ID are required");
        }

        // Validate menu item exists
        Menu menu = menuRepository.findById(reviewDTO.getMenuId())
                .orElseThrow(() -> new NotFoundException("Menu Item Not Found"));

        // Validate order exists
        Order order = orderRepository.findById(reviewDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order Not Found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Order does not belong to the current user");
        }

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new BadRequestException("You can only review items from delivered orders");
        }

        boolean itemInOrder = orderItemRepository.existsByOrderIdAndMenuId(
                reviewDTO.getOrderId(),
                reviewDTO.getMenuId());

        if (!itemInOrder) {
            throw new BadRequestException("Menu item not found in the specified order");
        }

        // Check if a review already exists for this menu item and order
        if (reviewRepository.existsByUserIdAndMenuIdAndOrderId(
                user.getId(),
                reviewDTO.getOrderId(),
                reviewDTO.getMenuId())) {
            throw new BadRequestException("You have already reviewed this menu item for this order");
        }

        Review review = Review.builder()
                .user(user)
                .menu(menu)
                .orderId(reviewDTO.getOrderId())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        log.info("Review created successfully for menu item ID:");

        ReviewDTO responseDTO = modelMapper.map(savedReview, ReviewDTO.class);
        responseDTO.setUserName(user.getName());
        responseDTO.setMenuName(menu.getName());

        return Response.<ReviewDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Review created successfully")
                .data(responseDTO)
                .build();

    }

    @Override
    public Response<List<ReviewDTO>> getReviewsForMenu(Long menuId) {
        log.info("Inside getReviewsForMenu()");

        List<Review> reviews = reviewRepository.findByMenuIdOrderByIdDesc(menuId);

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .toList();

        return Response.<List<ReviewDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reviews retrieved successfully")
                .data(reviewDTOs)
                .build();
    }

    @Override
    public Response<Double> getAverageRating(Long menuId) {
        log.info("Inside getAverageRating()");

        Double averageRating = reviewRepository.calculateAverageRatingByMenuId(menuId);

        return Response.<Double>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Average rating retrieved successfully")
                .data(averageRating != null ? averageRating : 0.0)
                .build();

    }
}
