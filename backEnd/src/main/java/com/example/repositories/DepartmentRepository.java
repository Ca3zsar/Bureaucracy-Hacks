package com.example.repositories;

import com.example.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(String name);

    @Query("Select d from Department d where UPPER(d.institution.name)=UPPER(?1)")
    List<Department> getDepartmentsList(String institutionName);

}
