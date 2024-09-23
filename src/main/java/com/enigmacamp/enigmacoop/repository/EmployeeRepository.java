package com.enigmacamp.enigmacoop.repository;

import com.enigmacamp.enigmacoop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
