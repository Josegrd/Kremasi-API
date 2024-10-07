package com.gardio.kremasi.service;

import com.gardio.kremasi.model.request.EmployeeRequest;
import com.gardio.kremasi.model.response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Page<EmployeeResponse> getAll(Integer page, Integer size);
    EmployeeResponse create(EmployeeRequest payload);
    EmployeeResponse update(EmployeeRequest payload);
    EmployeeResponse get(String id);
    void delete(String id);
    long count();
}
