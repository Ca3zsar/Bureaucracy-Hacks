package com.example.repositories;

import com.example.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionsRepository extends JpaRepository<Institution, Integer> {

    Optional<Institution> findByName(String name);

    Optional<Institution> findById(Integer id);

    @Query("Select i from Institution i")
    List<Institution> getInstitutionsList();

    @Query("Select i.latitude  from Institution i where upper(i.name) = upper(?1)")
    Double getLatitude(String institutionName);

    @Query("Select i.longitude from Institution i where upper(i.name) = upper(?1)")
    Double getLongitude(String institutionName);

    @Transactional
    @Modifying(flushAutomatically = true)
//    @Query("UPDATE Institution i " + "SET i.name = ?1, i.phone=?2,i.email=?3, i.site=?5, i.address=?6 " + "WHERE i.name = ?4")
    @Query("UPDATE Institution i " + "SET i.name = ?1, i.phone=?2,i.email=?3, i.site=?5, i.address=?6 " + "WHERE i.id = ?4")
    void updateInstitution(String name, String phone, String email, Integer id, String url, String address);

}
