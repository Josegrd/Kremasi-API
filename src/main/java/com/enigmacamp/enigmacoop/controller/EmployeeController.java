package com.enigmacamp.enigmacoop.controller;

import com.enigmacamp.enigmacoop.constant.PathApi;
import com.enigmacamp.enigmacoop.entity.Employee;
import com.enigmacamp.enigmacoop.model.request.EmployeeRequest;
import com.enigmacamp.enigmacoop.model.response.EmployeeResponse;
import com.enigmacamp.enigmacoop.model.response.PagingResponse;
import com.enigmacamp.enigmacoop.model.response.WebResponse;
import com.enigmacamp.enigmacoop.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathApi.BasePath.EMPLOYEE)
@Slf4j
//@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class EmployeeController {
    private final EmployeeService service;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<WebResponse<?>> create(
            @RequestPart(name = "employee") String jsonEmployee,
            @RequestPart(name = "image", required = false) MultipartFile image
    ){
        WebResponse.WebResponseBuilder<EmployeeResponse> responseBuilder = WebResponse.builder();
        try{
            EmployeeRequest request = objectMapper.readValue(jsonEmployee, new TypeReference<EmployeeRequest>() {});
            if (image != null && !image.isEmpty()){
                request.setImage(image);
            }
            EmployeeResponse response = service.create(request);
            responseBuilder.status(HttpStatus.CREATED.getReasonPhrase());
            responseBuilder.message("Success Create New Employee");
            responseBuilder.data(response);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());
        }catch (Exception e){
            responseBuilder.message(e.getMessage());
            responseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<EmployeeResponse> employees = service.getAll(page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(employees.getTotalPages())
                .totalElements(employees.getTotalElements())
                .build();
        WebResponse<List<EmployeeResponse>> response = WebResponse.<List<EmployeeResponse>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List Employee")
                .data(employees.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<?>> update(
            @RequestPart(name = "employee") String jsonEmployee,
            @RequestPart(name = "image") MultipartFile image
    ){
        WebResponse.WebResponseBuilder<EmployeeResponse> responseBuilder = WebResponse.builder();
        try{
            EmployeeRequest request = objectMapper.readValue(jsonEmployee, new TypeReference<EmployeeRequest>() {});
            request.setImage(image);
            log.error("error: {}", request);
            EmployeeResponse response = service.update(request);
            responseBuilder.status(HttpStatus.OK.getReasonPhrase());
            responseBuilder.message("Success Update Employee");
            responseBuilder.data(response);
            return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
        }catch (Exception e){
            responseBuilder.status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            responseBuilder.message(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @GetMapping(PathApi.SubBashPath.BY_ID)
    public ResponseEntity<?> getEmployee(@PathVariable String id){
        EmployeeResponse findEmp = service.get(id);
        WebResponse<EmployeeResponse> response = WebResponse.<EmployeeResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Employee")
                .data(findEmp)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(PathApi.SubBashPath.BY_ID)
    public ResponseEntity<?> delete(@PathVariable String id){
        service.delete(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete Employee")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

}
