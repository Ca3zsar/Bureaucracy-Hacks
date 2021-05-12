package com.example.repositories;

import com.example.models.Institution;
import com.example.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Integer> {

    Optional<Office> findByName(String name);

    Optional<Office> findById(Integer id);

    @Query("Select i from Office i")
    List<Office> getOfficeList();

    @Query("Select i.latitude  from Office i where upper(i.name) = upper(?1)")
    Double getLatitude(String officeName);

    @Query("Select i.longitude from Office i where upper(i.name) = upper(?1)")
    Double getLongitude(String officeName);

}
