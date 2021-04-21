package com.example.services;

import com.example.models.Review;
import com.example.models.User;
import com.example.repositories.ReviewRepository;
import com.example.requests.ReviewRequest;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    @Modifying
    public String giveReview(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        User user = reviewRepository.findIdByUsername(reviewRequest.getUsername());
        if (user == null)
            throw new IllegalStateException("Inexistent username");
        else
        review.setUser(reviewRepository.findIdByUsername(reviewRequest.getUsername()));
        reviewRepository.save(review);
        JSONObject jo = new JSONObject();
        jo.put("message", "Thanks for the review.");

        return jo.toString();
    }
}
