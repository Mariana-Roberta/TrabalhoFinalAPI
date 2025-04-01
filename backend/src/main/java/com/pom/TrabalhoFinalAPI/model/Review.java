package com.pom.TrabalhoFinalAPI.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "moviesReviews")
public class Review {
    @Id
    private String _id;

    private String id;
    private String title;
    private Double audienceScore;         // pode ser null (NaN no MongoDB vira null)
    private Integer tomatoMeter;
    private String  rating;                // pode ser null
    private String ratingContents;
    private LocalDate releaseDateTheaters;
    private String releaseDateStreaming;  // como é NaN no MongoDB, melhor manter como String
    private Integer runtimeMinutes;
    private String genre;
    private String originalLanguage;
    private String director;
    private String writer;
    private String boxOffice;             // pode ser null
    private String distributor;
    private String soundMix;
    private String criticName;
    private Boolean isTopCritic;
    private String originalScore;
    private String reviewState;
    private String publicatioName;
    private String reviewText;
    private String scoreSentiment;
    private String reviewUrl;

    public Review() {
    }

    public Integer getTomatoMeter() {
        return tomatoMeter;
    }

    public void setTomatoMeter(Integer tomatoMeter) {
        this.tomatoMeter = tomatoMeter;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(Double audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String  getRating() {
        return rating;
    }

    public void setRating(String  rating) {
        this.rating = rating;
    }

    public String getRatingContents() {
        return ratingContents;
    }

    public void setRatingContents(String ratingContents) {
        this.ratingContents = ratingContents;
    }

    public LocalDate getReleaseDateTheaters() {
        return releaseDateTheaters;
    }

    public void setReleaseDateTheaters(LocalDate releaseDateTheaters) {
        this.releaseDateTheaters = releaseDateTheaters;
    }

    public String getReleaseDateStreaming() {
        return releaseDateStreaming;
    }

    public void setReleaseDateStreaming(String releaseDateStreaming) {
        this.releaseDateStreaming = releaseDateStreaming;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getSoundMix() {
        return soundMix;
    }

    public void setSoundMix(String soundMix) {
        this.soundMix = soundMix;
    }

    public String getCriticName() {
        return criticName;
    }

    public void setCriticName(String criticName) {
        this.criticName = criticName;
    }

    public Boolean getTopCritic() {
        return isTopCritic;
    }

    public void setTopCritic(Boolean topCritic) {
        isTopCritic = topCritic;
    }

    public String getOriginalScore() {
        return originalScore;
    }

    public void setOriginalScore(String originalScore) {
        this.originalScore = originalScore;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getPublicatioName() {
        return publicatioName;
    }

    public void setPublicatioName(String publicatioName) {
        this.publicatioName = publicatioName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getScoreSentiment() {
        return scoreSentiment;
    }

    public void setScoreSentiment(String scoreSentiment) {
        this.scoreSentiment = scoreSentiment;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}
