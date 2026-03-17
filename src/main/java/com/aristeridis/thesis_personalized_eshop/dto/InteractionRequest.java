package com.aristeridis.thesis_personalized_eshop.dto;

import lombok.Data;

    @Data
    public class InteractionRequest {
        private Long userId;
        private Long productId;
        private String interactionType;
        private Integer ratingScore;
    }
