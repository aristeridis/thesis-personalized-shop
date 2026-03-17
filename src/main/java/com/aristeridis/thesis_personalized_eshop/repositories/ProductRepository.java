package com.aristeridis.thesis_personalized_eshop.repositories;

import com.aristeridis.thesis_personalized_eshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
