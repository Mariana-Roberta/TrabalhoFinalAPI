package com.pom.TrabalhoFinalAPI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pom.TrabalhoFinalAPI.model.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Page<Review> findAll(Pageable pageable);

    Page<Review> findByIsTopCriticTrue(Pageable pageable);

    long countByIsTopCriticTrue();
}
