package com.example.datatransferobjects.repositories;

import com.example.datatransferobjects.entities.Employee;
import com.example.datatransferobjects.entities.dtos.EmployeeNamesAndSalaryDTO;
import com.example.datatransferobjects.entities.dtos.EmployeeNamesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT new com.example.datatransferobjects.entities.dtos.EmployeeNamesDTO(e.firstName, e.lastName)" +
            " FROM Employee AS e WHERE e.id = :id")
    EmployeeNamesDTO findNamesById(Long id);

    EmployeeNamesAndSalaryDTO findFirstNameAndSalaryById(long id);
}
