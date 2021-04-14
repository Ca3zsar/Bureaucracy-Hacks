package com.example.services;

import com.example.models.Feedback;
import com.example.repositories.FeedbackRepository;
import com.example.requests.FeedbackRequest;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    @Modifying
    public String giveFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        feedback.setComment(feedbackRequest.getComment());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setUser(feedbackRepository.findIdByUsername(feedbackRequest.getUsername()));
        feedbackRepository.save(feedback);
        JSONObject jo = new JSONObject();
        jo.put("message", "Thanks for the feedback.");

        return jo.toString();
    }
}
