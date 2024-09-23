package com.enigmacamp.enigmacoop.controller;


import com.enigmacamp.enigmacoop.constant.PathApi;
import com.enigmacamp.enigmacoop.entity.TrxSaving;
import com.enigmacamp.enigmacoop.model.response.WebResponse;
import com.enigmacamp.enigmacoop.model.request.TrxSavingRequest;
import com.enigmacamp.enigmacoop.service.TrxSavingService;
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
