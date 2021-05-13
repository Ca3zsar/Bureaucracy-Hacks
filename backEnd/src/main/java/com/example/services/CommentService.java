package com.example.services;

import com.example.models.Comment;
import com.example.models.User;
import com.example.repositories.CommentRepository;
import com.example.requests.CommentRequest;
import com.example.requests.DeleteCommentRequest;
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
        comment.setQ1(commentRequest.getQ1());
        comment.setQ2(commentRequest.getQ2());
        comment.setQ3(commentRequest.getQ3());
        comment.setQ4(commentRequest.getQ4());
        comment.setDay(commentRequest.getDay());
        comment.setShow(true);
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

    @Transactional
    @Modifying
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        if (!commentRepository.findById(deleteCommentRequest.getId()).isPresent())
            throw new IllegalStateException("Nonexistent comment id");
        else
            commentRepository.deleteComment(deleteCommentRequest.getId());
        JSONObject jo = new JSONObject();
        jo.put("message", "Comment successfully deleted.");
        return jo.toString();
    }

    @Transactional
    @Modifying
    public String showComment(DeleteCommentRequest deleteCommentRequest) {
        if (!commentRepository.findById(deleteCommentRequest.getId()).isPresent())
            throw new IllegalStateException("Nonexistent comment id");
        else
            commentRepository.showComment(deleteCommentRequest.getId());
        JSONObject jo = new JSONObject();
        jo.put("message", "Comment successfully showed.");
        return jo.toString();
    }
}
