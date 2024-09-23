package com.enigmacamp.enigmacoop.controller;

import com.enigmacamp.enigmacoop.constant.PathApi;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.model.request.NasabahRequest;
import com.enigmacamp.enigmacoop.model.response.PagingResponse;
import com.enigmacamp.enigmacoop.model.response.WebResponse;
import com.enigmacamp.enigmacoop.service.NasabahService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.BasePath.NASABAH)
@AllArgsConstructor
public class NasabahController {

    private final NasabahService nasabahService;


    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<?> getAllNasabah(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Nasabah> nasabahList = nasabahService.getAllNasabah(page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(nasabahList.getTotalPages())
                .totalElements(nasabahList.getTotalElements())
                .build();
        WebResponse<List<Nasabah>> response = WebResponse.<List<Nasabah>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List Nasabah")
                .paging(pagingResponse)
                .data(nasabahList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = PathApi.SubBashPath.BY_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CUSTOMER')")
    public ResponseEntity<?> getNasabahById(@PathVariable String id){
        Nasabah findNasabah = nasabahService.getById(id);
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Nasabah")
                .data(findNasabah)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','CUSTOMER')")
    public ResponseEntity<?> updateNasabahById(@RequestBody NasabahRequest nasabah){
        Nasabah updatedNasabah = nasabahService.update(nasabah) ;
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success update Nasabah")
                .data(updatedNasabah)
                .build();
        return ResponseEntity.ok(response);
    }
}

// JPA :  if Req -> id (Primary key | UUID) as Update data
//        if !id as Create data

/**
 * 100jt data user -> 100jt return ke client  ?? NOOOOO!!!
 * semakin banyak data yang diload maka akan berpengaruh terhadap performance dari BE/service
 * filter limitation data list , ini harus adaa!!
 */