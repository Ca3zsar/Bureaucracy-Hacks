package com.example.services;

import com.example.models.Comment;
import com.example.models.User;
import com.example.repositories.CommentRepository;
import com.example.requests.CommentRequest;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    @Modifying
    public String giveComment(String process, CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());
        comment.setProcess(process);
        User user = commentRepository.findIdByUsername(commentRequest.getUsername());
        if (user == null)
            throw new IllegalStateException("Inexistent username");
        else
            comment.setUser(commentRepository.findIdByUsername(commentRequest.getUsername()));
        commentRepository.save(comment);
        JSONObject jo = new JSONObject();
        jo.put("message", "Thanks for the feedback.");

        return jo.toString();
    }
}
