package com.pom.TrabalhoFinalAPI.repository;

import com.pom.TrabalhoFinalAPI.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> searchByFilters(String title, String criticName, String genre, Boolean isTopCritic, Pageable pageable);
}
