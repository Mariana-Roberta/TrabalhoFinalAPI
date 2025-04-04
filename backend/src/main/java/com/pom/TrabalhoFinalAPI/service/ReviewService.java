package com.pom.TrabalhoFinalAPI.service;

import com.pom.TrabalhoFinalAPI.model.Review;
import com.pom.TrabalhoFinalAPI.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> getReviews(int page, int size) {
        return reviewRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Review> getFilteredReviews(String title, String criticName, String genre, Boolean isTopCritic, int page, int size) {
        // Implementação de filtro pode ser adicionada conforme a lógica de pesquisa desejada.
        return reviewRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Review> getTopCriticsReviews(int page, int size) {
        return reviewRepository.findByIsTopCriticTrue(PageRequest.of(page, size));
    }

    public Map<String, Integer> getSentimentDistribution(int page, int size) {
        List<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size)).getContent();
        Map<String, Integer> sentimentCount = new HashMap<>();

        for (Review review : reviews) {
            String sentiment = review.getScoreSentiment(); // Supondo que Review tem um campo `sentiment`
            if (sentiment != null) {
                sentiment = sentiment.trim().toLowerCase();
                sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }
        }
        System.out.println(sentimentCount);

        return sentimentCount;
    }

    public Map<String, Integer> getGenreDistribution(int page, int size) {
        List<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size)).getContent();
        Map<String, Integer> genreCount = new HashMap<>();

        for (Review r : reviews) {
            if (r.getGenre() != null) {
                String[] genres = r.getGenre().split(",");
                for (String g : genres) {
                    String genreTrimmed = g.trim();
                    genreCount.put(genreTrimmed, genreCount.getOrDefault(genreTrimmed, 0) + 1);
                }
            }
        }
        return genreCount;
    }

    public Map<String, Double> getScoreComparison(int page, int size) {
        List<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size)).getContent().stream()
                .filter(r -> r.getAudienceScore() != null && r.getTomatoMeter() != null)
                .filter(r -> !Double.isNaN(r.getAudienceScore()))
                .toList();;

        double avgAudience = reviews.stream()
                .mapToDouble(Review::getAudienceScore)
                .average()
                .orElse(0.0);

        double avgTomato = reviews.stream()
                .mapToInt(Review::getTomatoMeter)
                .average()
                .orElse(0.0);

        return Map.of(
                "audienceAverage", avgAudience,
                "tomatoAverage", avgTomato
        );
    }

    public List<Map<String, Object>> getScoreEvolution(int page, int size) {
        List<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size)).getContent();

        return reviews.stream()
                .filter(r ->
                        r.getReleaseDateTheaters() != null &&
                                r.getAudienceScore() != null &&
                                !r.getAudienceScore().isNaN() &&
                                r.getTomatoMeter() != null
                )
                .sorted(Comparator.comparing(Review::getReleaseDateTheaters))
                .map(r -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("date", r.getReleaseDateTheaters());
                    result.put("audienceScore", r.getAudienceScore());
                    result.put("tomatoMeter", r.getTomatoMeter());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRuntimeDistribution(int page, int size, int bucketSize) {
        List<Review> reviews = reviewRepository.findAll(PageRequest.of(page, size)).getContent();

        Map<String, Long> grouped = reviews.stream()
                .filter(r -> r.getRuntimeMinutes() != null && r.getRuntimeMinutes() > 0)
                .collect(Collectors.groupingBy(
                        r -> {
                            int bucket = r.getRuntimeMinutes() / bucketSize;
                            int start = bucket * bucketSize;
                            int end = start + bucketSize - 1;
                            return start + "-" + end + " min";
                        },
                        Collectors.counting()
                ));

        return grouped.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("range", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

}
