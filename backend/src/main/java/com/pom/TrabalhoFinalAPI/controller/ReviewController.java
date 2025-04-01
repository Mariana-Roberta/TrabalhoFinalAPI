package com.pom.TrabalhoFinalAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pom.TrabalhoFinalAPI.model.Review;
import com.pom.TrabalhoFinalAPI.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Reviews", description = "Endpoints para análise de críticas de filmes")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Lista avaliações paginadas")
    @GetMapping
    public ResponseEntity<List<Review>> getReviews(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getReviews(page, size));
    }

    @Operation(summary = "Lista avaliações filtradas")
    @GetMapping("/filter")
    public ResponseEntity<Page<Review>> getFilteredReviews(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String criticName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Boolean isTopCritic,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Review> result = reviewService.getFilteredReviews(title, criticName, genre, isTopCritic, page, size);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Estatísticas das avaliações")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getReviewStatistics() {
        return ResponseEntity.ok(reviewService.getStatistics());
    }

    @Operation(summary = "Críticas de top críticos")
    @GetMapping("/top-critics")
    public ResponseEntity<List<Review>> getTopCriticsReviews() {
        return ResponseEntity.ok(reviewService.getTopCriticsReviews());
    }

    @Operation(summary = "Distribuição de sentimentos")
    @GetMapping("/sentiment")
    public ResponseEntity<Map<String, Integer>> getSentimentDistribution() {
        return ResponseEntity.ok(reviewService.getSentimentDistribution());
    }

    @GetMapping("/genre-distribution")
    public ResponseEntity<Map<String, Integer>> getGenreDistribution() {
        List<Review> all = reviewService.findAll();
        Map<String, Integer> genreCount = new HashMap<>();

        for (Review r : all) {
            if (r.getGenre() != null) {
                String[] genres = r.getGenre().split(",");
                for (String g : genres) {
                    String genreTrimmed = g.trim();
                    genreCount.put(genreTrimmed, genreCount.getOrDefault(genreTrimmed, 0) + 1);
                }
            }
        }
        return ResponseEntity.ok(genreCount);
    }

    @GetMapping("/score-comparison")
    public ResponseEntity<Map<String, Double>> getScoreComparison() {
        List<Review> reviews = reviewService.findAll().stream()
                .filter(r -> r.getAudienceScore() != null && r.getTomatoMeter() != null)
                .filter(r -> !Double.isNaN(r.getAudienceScore())) // ✅ evita valores inválidos
                .toList();

        double avgAudience = reviews.stream()
                .mapToDouble(Review::getAudienceScore)
                .average()
                .orElse(0.0);

        double avgTomato = reviews.stream()
                .mapToInt(Review::getTomatoMeter)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(Map.of(
                "audienceAverage", avgAudience,
                "tomatoAverage", avgTomato
        ));
    }




}

