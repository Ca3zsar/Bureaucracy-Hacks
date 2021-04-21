package com.example.repositories;

import com.example.models.Review;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("Select u from User u where u.username=?1")
    User findIdByUsername(String username);

}
