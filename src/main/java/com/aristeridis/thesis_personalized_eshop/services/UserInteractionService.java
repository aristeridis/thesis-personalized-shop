package com.aristeridis.thesis_personalized_eshop.services;

import com.aristeridis.thesis_personalized_eshop.dto.InteractionRequest;
import com.aristeridis.thesis_personalized_eshop.entities.Product;
import com.aristeridis.thesis_personalized_eshop.entities.User;
import com.aristeridis.thesis_personalized_eshop.entities.UserInteraction;
import com.aristeridis.thesis_personalized_eshop.repositories.ProductRepository;
import com.aristeridis.thesis_personalized_eshop.repositories.UserInteractionRepository;
import com.aristeridis.thesis_personalized_eshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserInteractionService {

    private final UserInteractionRepository interactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserInteractionService(UserInteractionRepository interactionRepository,
                                  UserRepository userRepository,
                                  ProductRepository productRepository) {
        this.interactionRepository = interactionRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public UserInteraction recordInteraction(InteractionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Ο χρήστης δεν βρέθηκε"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Το προϊόν δεν βρέθηκε"));

        UserInteraction interaction = new UserInteraction();
        interaction.setUser(user);
        interaction.setProduct(product);
        interaction.setInteractionType(request.getInteractionType());
        interaction.setRatingScore(request.getRatingScore());
        interaction.setTimestamp(LocalDateTime.now());

        return interactionRepository.save(interaction);
    }

}
