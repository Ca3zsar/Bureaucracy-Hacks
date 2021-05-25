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
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User a" + " set a.isEnabled=true where a.email=?1")
    int enableUser(String email);

    @Query("Select u from User u where u.email=?1")
    User getUserInfo(String email);

    @Query("Select u.isEnabled from User u where u.email=?1")
    boolean getEnabledUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a" + " set a.isAdmin = 1 where a.email=?1")
    int makeAdmin(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a" + " set a.name = ?1, a.surname = ?2, a.email =?3 where a.registrationId=?4")
    int updateSettings(String name, String surname, String email, Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE User a" + " set a.isAdmin = 0 where a.email=?1")
    int makeNotAdmin(String email);

}
