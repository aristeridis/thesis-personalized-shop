package com.aristeridis.thesis_personalized_eshop.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data // Το Lombok φτιάχνει αυτόματα Getters/Setters!
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private String role; // π.χ. 'USER', 'ADMIN'

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}