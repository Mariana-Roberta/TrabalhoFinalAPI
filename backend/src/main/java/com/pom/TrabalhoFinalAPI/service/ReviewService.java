package com.pom.TrabalhoFinalAPI.service;

import com.pom.TrabalhoFinalAPI.model.Review;
import com.pom.TrabalhoFinalAPI.repository.ReviewRepository;
import com.pom.TrabalhoFinalAPI.repository.ReviewRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewRepositoryCustom reviewRepositoryCustom;

    public List<Review> getReviews(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Page<Review> getFilteredReviews(String title, String criticName, String genre, Boolean isTopCritic, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepositoryCustom.searchByFilters(title, criticName, genre, isTopCritic, pageable);
    }


    public Map<String, Object> getStatistics() {
        // Lógica real aqui — mock de exemplo:
        return Map.of("totalReviews", reviewRepository.count());
    }

    public List<Review> getTopCriticsReviews() {
        // Exemplo fictício — ajuste conforme sua lógica real
        return reviewRepository.findAll()
                .stream()
                .filter(r -> "Top Critic".equalsIgnoreCase(r.getCriticName()))
                .toList();
    }

    public Map<String, Integer> getSentimentDistribution() {
        List<Review> all = reviewRepository.findAll();

        int positive = (int) all.stream().filter(r -> "positive".equalsIgnoreCase(r.getScoreSentiment())).count();
        int neutral = (int) all.stream().filter(r -> "neutral".equalsIgnoreCase(r.getScoreSentiment())).count();
        int negative = (int) all.stream().filter(r -> "negative".equalsIgnoreCase(r.getScoreSentiment())).count();

        return Map.of(
                "positive", positive,
                "neutral", neutral,
                "negative", negative
        );
    }

    public List<Review> findAll() {
        return this.reviewRepository.findAll();
    }
}

