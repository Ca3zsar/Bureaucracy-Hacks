package com.example.repositories;

import com.example.models.Comment;
import com.example.models.Review;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {

    @Override
    Optional<Comment> findById(Integer integer);

    @Query("Select c from Comment c where c.process=?1")
    List<Comment> findAll(String processName);

    @Query("Select u from User u where u.username=?1")
    User findIdByUsername(String username);

    @Transactional
    @Modifying
    @Query("Update Comment c set c.show = false where c.id=?1")
    int deleteComment(Integer id);

    @Transactional
    @Modifying
    @Query("Update Comment c set c.show = true where c.id=?1")
    int showComment(Integer id);
}
