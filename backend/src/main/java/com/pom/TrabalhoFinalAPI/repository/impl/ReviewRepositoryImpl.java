package com.pom.TrabalhoFinalAPI.repository.impl;

import com.pom.TrabalhoFinalAPI.model.Review;
import com.pom.TrabalhoFinalAPI.repository.ReviewRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Review> searchByFilters(String title, String criticName, String genre, Boolean isTopCritic, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            criteriaList.add(Criteria.where("title").regex(title, "i"));
        }

        if (criticName != null && !criticName.isBlank()) {
            criteriaList.add(Criteria.where("criticName").regex(criticName, "i"));
        }

        if (genre != null && !genre.isBlank()) {
            criteriaList.add(Criteria.where("genre").regex(genre, "i"));
        }

        if (isTopCritic != null) {
            criteriaList.add(Criteria.where("isTopCritic").is(isTopCritic));
        }

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        query.with(pageable);

        List<Review> results = mongoTemplate.find(query, Review.class);
        return PageableExecutionUtils.getPage(results, pageable, () -> mongoTemplate.count(query.skip(-1).limit(-1), Review.class));
    }
}
