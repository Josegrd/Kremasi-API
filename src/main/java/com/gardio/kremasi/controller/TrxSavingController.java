package com.gardio.kremasi.controller;


import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.entity.TrxSaving;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.model.request.TrxSavingRequest;
import com.gardio.kremasi.service.TrxSavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.BasePath.TRX_SAVING)
@AllArgsConstructor
public class TrxSavingController {

    private final TrxSavingService trxSavingService;

    @PostMapping
    public ResponseEntity<WebResponse<TrxSaving>> createTrxSaving(@RequestBody TrxSavingRequest trxSavingRequest){
        TrxSaving newTrxSaving = trxSavingService.createTrxSaving(trxSavingRequest);
        WebResponse<TrxSaving> response = WebResponse.<TrxSaving>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success Create Transaction Saving")
                .data(newTrxSaving)
                .build();
        return ResponseEntity.ok(response);
    }

}
