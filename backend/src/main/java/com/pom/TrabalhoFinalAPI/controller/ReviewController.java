package com.pom.TrabalhoFinalAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Review> reviewPage = reviewService.getReviews(page, size); // Obtém a página
        List<Review> reviews = reviewPage.getContent(); // Extrai apenas a lista de objetos

        return ResponseEntity.ok(reviews);
    }


    @Operation(summary = "Lista avaliações filtradas")
    @GetMapping("/filter")
    public ResponseEntity<Page<Review>> getFilteredReviews(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String criticName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Boolean isTopCritic,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getFilteredReviews(title, criticName, genre, isTopCritic, page, size));
    }

    @Operation(summary = "Evolução dos scores ao longo do tempo paginada")
    @GetMapping("/score-evolution")
    public ResponseEntity<List<Map<String, Object>>> getScoreEvolution(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getScoreEvolution(page, size));
    }

    @Operation(summary = "Distribuição de sentimentos")
    @GetMapping("/sentiment")
    public ResponseEntity<Map<String, Integer>> getSentimentDistribution(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getSentimentDistribution(page, size));
    }

    @Operation(summary = "Distribuição de gêneros paginada")
    @GetMapping("/genre-distribution")
    public ResponseEntity<Map<String, Integer>> getGenreDistribution(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getGenreDistribution(page, size));
    }

    @Operation(summary = "Distribuição de gêneros paginada")
    @GetMapping("/score-comparison")
    public ResponseEntity<Map<String, Double>> getScoreComparison(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getScoreComparison(page, size));
    }

    @GetMapping("/runtime-distribution")
    public List<Map<String, Object>> getRuntimeDistribution(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "30") int bucketSize
    ) {
        return reviewService.getRuntimeDistribution(page, size, bucketSize);
    }

}
