package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.PositionEmployee;
import com.enigmacamp.enigmacoop.entity.Employee;
import com.enigmacamp.enigmacoop.entity.Image;
import com.enigmacamp.enigmacoop.model.request.EmployeeRequest;
import com.enigmacamp.enigmacoop.model.response.EmployeeResponse;
import com.enigmacamp.enigmacoop.model.response.ImageResponse;
import com.enigmacamp.enigmacoop.repository.EmployeeRepository;
import com.enigmacamp.enigmacoop.service.EmployeeService;
import com.enigmacamp.enigmacoop.service.ImageService;
import com.enigmacamp.enigmacoop.utils.ValidationUtil;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final ValidationUtil validationUtil;
    private final ImageService imageService;

    @Override
    public EmployeeResponse get(String id) {
        Employee employee = findByIdOrThrowNotFound(id);
        return mapToEmployeeResponse(employee);
    }

    private Employee findByIdOrThrowNotFound(String id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not Found"));
    }

    @Override
    public Page<EmployeeResponse> getAll(Integer page, Integer size) {
        if (page <= 0 ) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1,size);
        return repo.findAll(pageable).map(this::mapToEmployeeResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse create(EmployeeRequest payload) {
        log.info("Check Payload : ",payload.toString());
        validationUtil.validate(payload);
//        if (payload.getImage().isEmpty()) throw new ConstraintViolationException("image is required",null);
        Employee employee = mapToEmployee(payload);
        if (payload.getImage() != null && !payload.getImage().isEmpty() ) {
            Image image = imageService.create(payload.getImage());
            employee.setImage(image);
        }
        repo.saveAndFlush(employee);
        return mapToEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse update(EmployeeRequest payload) {
        Employee employee = findByIdOrThrowNotFound(payload.getId());
        employee.setFullName(payload.getFullName());
        employee.setEmail(payload.getEmail());
        employee.setPhoneNumber(payload.getPhoneNumber());
        employee.setHireDate(payload.getHireDate());
        employee.setPosition(payload.getPosition());
        employee.setSalary(payload.getSalary());
        employee.setStatus(payload.getStatus());
        if (payload.getImage() != null && !payload.getImage().isEmpty()){
            if(employee.getImage() != null){
                    repo.delete(employee);
                    imageService.deleteById(employee.getImage().getId());
            }
            Image image = imageService.create(payload.getImage());
            employee.setImage(image);
        }
        return mapToEmployeeResponse(repo.saveAndFlush(employee));
    }

    @Override
    public void delete(String id) {
        Employee employee = findByIdOrThrowNotFound(id);
        repo.delete(employee);
        if (employee.getImage() != null){
            String imageId = employee.getImage().getId();
            imageService.deleteById(imageId);
        }
    }

    @Override
    public long count() {
        return repo.count();
    }

    private Employee mapToEmployee(EmployeeRequest payload){
        return Employee.builder()
                .fullName(payload.getFullName())
                .email(payload.getEmail())
                .phoneNumber(payload.getPhoneNumber())
                .hireDate(payload.getHireDate())
                .position(payload.getPosition())
                .salary(payload.getSalary())
                .status(true)
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee){
        ImageResponse imageResponse = ImageResponse.builder().build();
        if (employee.getImage() != null){
            imageResponse.setName(employee.getImage().getName());
            imageResponse.setUrl("/api/v1/image" + "/" + employee.getImage().getId());
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .hireDate(employee.getHireDate())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .status(employee.getStatus())
                .imageResponse(imageResponse)
                .build();
    }
}
