package com.hikmetsuicmez.FlavorHub.review.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO {

    private Long id;
    private Long menuId;
    private Long orderId;

    private String userName;

    @NotNull(message = "Rating is required")
    @Min(1)
    @Max(10)
    private Integer rating; // e.g., 1 to 10 stars

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    private String menuName;
    private String createdAt;
}
