package com.example.repositories;

import com.example.models.ChangePassToken;
import com.example.models.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChangePasswordRepository extends JpaRepository<ChangePassToken, Integer> {


    Optional<ChangePassToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ChangePassToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query("UPDATE User u " + "SET u.hashPassword = ?2 " + "WHERE u.email = ?1")
    int updatePassword(String email, String password, String confirmPassword);

    @Query("Select t.email from ChangePassToken t where t.token=?1 and t.confirmedAt is not null")
    String findConfirmedEmailByToken(String token);

    @Query("Select t.email from ChangePassToken t where t.token=?1")
    String findEmailByToken(String token);
}
