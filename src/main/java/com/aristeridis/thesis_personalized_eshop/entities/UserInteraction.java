package com.aristeridis.thesis_personalized_eshop.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_interactions")
@Data
public class UserInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String interactionType; // π.χ. 'VIEW', 'CART', 'PURCHASE', 'RATING'

    private Integer ratingScore; // 1-5 αστέρια (αν υπάρχει)

    private LocalDateTime timestamp = LocalDateTime.now();
}