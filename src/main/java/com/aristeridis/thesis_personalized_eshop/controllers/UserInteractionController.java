package com.aristeridis.thesis_personalized_eshop.controllers;

import com.aristeridis.thesis_personalized_eshop.dto.InteractionRequest;
import com.aristeridis.thesis_personalized_eshop.entities.UserInteraction;
import com.aristeridis.thesis_personalized_eshop.services.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*")
public class UserInteractionController {

    private final UserInteractionService interactionService;

    @Autowired
    public UserInteractionController(UserInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping
    public ResponseEntity<UserInteraction> recordInteraction(@RequestBody InteractionRequest request) {
        UserInteraction savedInteraction = interactionService.recordInteraction(request);
        return ResponseEntity.ok(savedInteraction);
    }
}
