package com.example.repositories;

import com.example.models.Comment;
import com.example.models.Review;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
    @Query("Select u from User u where u.username=?1")
    User findIdByUsername(String username);
}
