package com.hikmetsuicmez.FlavorHub.review.entity;

import com.hikmetsuicmez.FlavorHub.auth_users.entity.User;
import com.hikmetsuicmez.FlavorHub.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer rating; // e.g.. 1 to 10 stars

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;

    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
