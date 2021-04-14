package com.example.repositories;

import com.example.models.BureaucraticProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BureaucraticProcessRepository extends JpaRepository<BureaucraticProcess, Integer> {
    @Query("Select b.name from BureaucraticProcess b")
    List<String> getProcessesList();
}
