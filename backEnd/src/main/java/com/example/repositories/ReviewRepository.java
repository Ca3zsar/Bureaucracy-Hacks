package com.example.repositories;

import com.example.models.Review;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("Select r from Review r")
    List<Review> getAllReviews();

    @Query("Select u from User u where u.username=?1")
    User findIdByUsername(String username);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("delete from Review r where r.idFeedback = ?1")
    int deleteByIdFeedback(Integer id);

}
