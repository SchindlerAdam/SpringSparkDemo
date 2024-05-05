package com.schindler.springsparkdemo.repository;

import com.schindler.springsparkdemo.repository.entitiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Employee WHERE id = ? AND is_deleted = FALSE")
    Optional<Employee> findEmployeeById(final Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM Employee WHERE is_Deleted = FALSE")
    List<Employee> findAllEmployee();
}
