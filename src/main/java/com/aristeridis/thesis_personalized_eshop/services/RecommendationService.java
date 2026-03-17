package com.aristeridis.thesis_personalized_eshop.services;

import com.aristeridis.thesis_personalized_eshop.entities.Product;
import com.aristeridis.thesis_personalized_eshop.entities.UserInteraction;
import com.aristeridis.thesis_personalized_eshop.repositories.ProductRepository;
import com.aristeridis.thesis_personalized_eshop.repositories.UserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final UserInteractionRepository interactionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RecommendationService(UserInteractionRepository interactionRepository, ProductRepository productRepository) {
        this.interactionRepository = interactionRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getRecommendationsForUser(Long userId) {
        List<UserInteraction> userInteractions = interactionRepository.findByUserIdOrderByTimestampDesc(userId);

        if (userInteractions.isEmpty()) {
            return productRepository.findAll().stream().limit(4).collect(Collectors.toList());
        }

        Set<Long> seenProductIds = userInteractions.stream()
                .map(interaction -> interaction.getProduct().getId())
                .collect(Collectors.toSet());

        Long lastViewedProductId = userInteractions.get(0).getProduct().getId();

        List<UserInteraction> othersInteractions = interactionRepository.findByProductId(lastViewedProductId);

        Set<Long> similarUserIds = othersInteractions.stream()
                .map(interaction -> interaction.getUser().getId())
                .filter(id -> !id.equals(userId)) // Εξαιρούμε τον εαυτό μας
                .collect(Collectors.toSet());

        Set<Product> recommendedProducts = new HashSet<>();
        for (Long similarUserId : similarUserIds) {
            List<UserInteraction> similarUserHistory = interactionRepository.findByUserIdOrderByTimestampDesc(similarUserId);
            for (UserInteraction interaction : similarUserHistory) {
                Product potentialProduct = interaction.getProduct();
                if (!seenProductIds.contains(potentialProduct.getId())) {
                    recommendedProducts.add(potentialProduct);
                }
            }
        }

        if (recommendedProducts.isEmpty()) {
            return productRepository.findAll().stream()
                    .filter(p -> !seenProductIds.contains(p.getId()))
                    .limit(4)
                    .collect(Collectors.toList());
        }

        return recommendedProducts.stream().limit(4).collect(Collectors.toList());
    }
}