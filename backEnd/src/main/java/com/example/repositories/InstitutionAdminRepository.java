package com.example.repositories;

import com.example.models.InstitutionAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface InstitutionAdminRepository extends JpaRepository<InstitutionAdmin, Integer> {

    Optional<InstitutionAdmin> findByIdInstitution(Integer integer);

    Optional<InstitutionAdmin> findByIdUser(Integer integer);

    @Transactional
    @Modifying
    @Query("delete from InstitutionAdmin i where i.idInstitution = ?1 and i.idUser = ?2")
    int makeNotInstitutionAdmin(Integer institution, Integer user);

}
