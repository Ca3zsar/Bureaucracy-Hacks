package com.example.repositories;

import com.example.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionsRepository extends JpaRepository<Institution, Integer> {
    @Query("Select i.name from Institution i")
    List<String> getInstitutionsList();

    @Query("Select i.latitude  from Institution i where upper(i.name) = upper(?1)")
    Double getLatitude(String institutionName);

    @Query("Select i.longitude from Institution i where upper(i.name) = upper(?1)")
    Double getLongitude(String institutionName);

}
