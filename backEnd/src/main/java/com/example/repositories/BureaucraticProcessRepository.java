package com.example.repositories;

import com.example.models.BureaucraticProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BureaucraticProcessRepository extends JpaRepository<BureaucraticProcess, Integer> {

    Optional<BureaucraticProcess> findByName(String name);
    Optional<BureaucraticProcess> findById(Integer id);

    @Query("Select b from BureaucraticProcess b")
    List<BureaucraticProcess> getProcessesList();

    @Transactional
    @Modifying
    @Query("UPDATE BureaucraticProcess b " + "SET b.name = ?1, b.institution=?2,b.usefulInformation=?3 " + "WHERE b.name = ?4")
    int updateBureaucraticProcess(String name, Integer idInstitution, String usefulInformation, String oldName);
}
