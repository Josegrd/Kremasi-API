package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.PositionEmployee;
import com.enigmacamp.enigmacoop.model.request.EmployeeRequest;
import com.enigmacamp.enigmacoop.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final EmployeeService employeeService;
    @PostConstruct
    public void init() {
        if (employeeService.count() == 0) {
            log.info("Initializing database with sample employee data");

            EmployeeRequest emp1 = new EmployeeRequest();
            emp1.setFullName("John Doe");
            emp1.setEmail("john.doe@example.com");
            emp1.setPhoneNumber("08476926893");
            emp1.setHireDate(new Date());
            emp1.setPosition(PositionEmployee.CASHIER);
            emp1.setSalary(5000000L);

            EmployeeRequest emp2 = new EmployeeRequest();
            emp2.setFullName("Diana");
            emp2.setEmail("diana@example.com");
            emp2.setPhoneNumber("087367829873");
            emp2.setHireDate(new Date());
            emp2.setPosition(PositionEmployee.MANAGER);
            emp2.setSalary(8000000L);

            employeeService.create(emp1);
            employeeService.create(emp2);
            log.info("Sample employee data initialized");
        } else {
            log.info("Employee data already exists, no initialization required");
        }
    }
}
