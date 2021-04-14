package com.example.repositories;

import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a" + " set a.isEnabled=true where a.email=?1")
    int enableUser(String email);

    @Query("Select u from User u where u.email=?1")
    User getUserInfo(String email);

    @Query("Select u.isEnabled from User u where u.email=?1")
    boolean getEnabledUser(String email);
}
