package com.aristeridis.thesis_personalized_eshop.repositories;

import com.aristeridis.thesis_personalized_eshop.entities.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {

    List<UserInteraction> findByUserId(Long userId);

    List<UserInteraction> findByProductId(Long productId);

    List<UserInteraction> findByUserIdOrderByTimestampDesc(Long userId);

}
