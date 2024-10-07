package com.gardio.kremasi.controller;

import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.entity.InstallmentType;
import com.gardio.kremasi.model.request.InstallmentTypeRequest;
import com.gardio.kremasi.model.response.InstallmentTypeResponse;
import com.gardio.kremasi.model.response.PagingResponse;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.service.InstallmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.BasePath.INSTALLMENT_TYPE)
@RequiredArgsConstructor
public class InstallmentTypeController {
    private final InstallmentTypeService installmentTypeService;

    @PostMapping
    public ResponseEntity<WebResponse<InstallmentTypeResponse>> createInstallmentType(
            @RequestBody InstallmentTypeRequest installmentTypeRequest){
        InstallmentTypeResponse installmentTypeResponse = installmentTypeService.createInstallmentType(installmentTypeRequest);
        WebResponse<InstallmentTypeResponse> response = WebResponse.<InstallmentTypeResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success create installment type")
                .data(installmentTypeResponse)
                .build();
        return ResponseEntity.ok(response);
    }

//    @GetMapping
//    public ResponseEntity<WebResponse<InstallmentTypeResponse>> getAll(){
//        List<InstallmentTypeResponse> installmentTypeResponses = installmentTypeService.getAllInstallmentType();
//        WebResponse<InstallmentTypeResponse> response = WebResponse.<InstallmentTypeResponse>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("Success get all installment type")
//                .data((InstallmentTypeResponse) installmentTypeResponses)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @GetMapping
    public ResponseEntity<?> getAllInstallmentType(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<InstallmentType> installmentTypeList = installmentTypeService.getAllInstallmentType(page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(installmentTypeList.getTotalPages())
                .totalElements(installmentTypeList.getTotalElements())
                .build();

        WebResponse<List<InstallmentType>> response = WebResponse.<List<InstallmentType>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List Installment Type")
                .paging(pagingResponse)
                .data(installmentTypeList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping
    public ResponseEntity<WebResponse<InstallmentTypeResponse>> deleteInstallmentType(@RequestParam String id){
        installmentTypeService.deleteInstallmentType(id);
        WebResponse<InstallmentTypeResponse> response = WebResponse.<InstallmentTypeResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success delete installment type")
                .build();
        return ResponseEntity.ok(response);
    }
}
