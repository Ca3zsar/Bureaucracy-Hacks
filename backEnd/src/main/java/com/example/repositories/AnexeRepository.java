package com.example.repositories;

import com.example.models.Anexa;
import com.example.models.BureaucraticProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnexeRepository extends JpaRepository<Anexa, Integer> {

    @Query("Select f.fileLink from Anexa f where f.fileName = ?1")
    String getFileLink(String fileName);
}
