package com.gardio.kremasi.controller;

import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.entity.LoanType;
import com.gardio.kremasi.model.request.LoanTypeRequest;
import com.gardio.kremasi.model.response.LoanTypeResponse;
import com.gardio.kremasi.model.response.PagingResponse;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.BasePath.LOAN_TYPE)
@RequiredArgsConstructor
public class LoanTypeContoller {
    private final LoanTypeService loanTypeService;

    @PostMapping
    public ResponseEntity<WebResponse<LoanTypeResponse>> createLoanType(
            @RequestBody LoanTypeRequest loanTypeRequest){
        LoanTypeResponse loanTypeResponse = loanTypeService.createLoanType(loanTypeRequest);
        WebResponse<LoanTypeResponse> response = WebResponse.<LoanTypeResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success create loan type")
                .data(loanTypeResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllLoanType(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<LoanType> loanTypeList = loanTypeService.getAllLoanType(page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(loanTypeList.getTotalPages())
                .totalElements(loanTypeList.getTotalElements())
                .build();

        WebResponse<List<LoanType>> response = WebResponse.<List<LoanType>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get List Nasabah")
                .paging(pagingResponse)
                .data(loanTypeList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<WebResponse<LoanTypeResponse>> deleteLoanType(@RequestParam String id){
        loanTypeService.deleteLoanType(id);
        WebResponse<LoanTypeResponse> response = WebResponse.<LoanTypeResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success delete loan type")
                .build();
        return ResponseEntity.ok(response);
    }
}
