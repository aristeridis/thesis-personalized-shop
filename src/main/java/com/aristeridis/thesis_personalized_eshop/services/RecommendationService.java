package com.aristeridis.thesis_personalized_eshop.services;

import com.aristeridis.thesis_personalized_eshop.entities.Product;
import com.aristeridis.thesis_personalized_eshop.entities.UserInteraction;
import com.aristeridis.thesis_personalized_eshop.repositories.ProductRepository;
import com.aristeridis.thesis_personalized_eshop.repositories.UserInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    @Autowired
    private UserInteractionRepository interactionRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getRecommendationsForUser(Long userId) {
        List<UserInteraction> history = interactionRepository.findByUserId(userId);

        Map<Long, Integer> categoryScores = new HashMap<>();
        List<Long> interactedProductIds = new ArrayList<>();

        for (UserInteraction interaction : history) {
            Product p = interaction.getProduct();
            interactedProductIds.add(p.getId());

            Long categoryId = p.getCategoryId();
            int currentScore = categoryScores.getOrDefault(categoryId, 0);

            if ("CART".equals(interaction.getInteractionType())) {
                categoryScores.put(categoryId, currentScore + 1);
            } else if ("PURCHASE".equals(interaction.getInteractionType())) {
                categoryScores.put(categoryId, currentScore + 2);
            }
        }

        if (categoryScores.isEmpty()) {
            return new ArrayList<>();
        }

        Long topCategoryId = null;
        int maxScore = -1;
        for (Map.Entry<Long, Integer> entry : categoryScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                topCategoryId = entry.getKey();
            }
        }
        List<Product> recommendedProducts = new ArrayList<>();
        List<Product> categoryProducts = productRepository.findByCategoryId(topCategoryId);

        for (Product p : categoryProducts) {
            if (!interactedProductIds.contains(p.getId())) {
                recommendedProducts.add(p);
            }
        }

        return recommendedProducts;
    }
}