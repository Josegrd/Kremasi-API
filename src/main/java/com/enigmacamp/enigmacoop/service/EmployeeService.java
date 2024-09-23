package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.Employee;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.model.request.EmployeeRequest;
import com.enigmacamp.enigmacoop.model.response.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Page<EmployeeResponse> getAll(Integer page, Integer size);
    EmployeeResponse create(EmployeeRequest payload);
    EmployeeResponse update(EmployeeRequest payload);
    EmployeeResponse get(String id);
    void delete(String id);
    long count();
}
