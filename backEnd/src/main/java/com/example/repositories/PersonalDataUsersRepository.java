package com.example.repositories;

import com.example.models.PersonalDataUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PersonalDataUsersRepository extends JpaRepository<PersonalDataUsers, Integer> {

    @Query("select i.key from PersonalDataUsers i where i.userId = ?1")
    String getKey(Integer userId);

    @Query("select count(i) from PersonalDataUsers i where i.userId = ?1")
    int findByUser(Integer userId);

    @Query("select i from PersonalDataUsers i where i.userId = ?1")
    PersonalDataUsers findByUserId(Integer userId);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE PersonalDataUsers i " + "SET i.address=?1, i.phone=?2,i.dataNastere=?3, i.cnp = ?5, i.judet=?6, i.localitate=?7, i.numarBuletin=?8, i.serieBuletin=?9 " + "WHERE i.userId= ?4")
    void updatePersonalData(String address, String phone, String dataNastere, Integer userId, String cnp, String judet, String localitate, String numarBuletin, String serieBuletin);
}
