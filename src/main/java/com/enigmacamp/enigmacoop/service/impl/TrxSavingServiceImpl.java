package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.SavingType;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.entity.Saving;
import com.enigmacamp.enigmacoop.entity.TrxSaving;
import com.enigmacamp.enigmacoop.model.request.TrxSavingRequest;
import com.enigmacamp.enigmacoop.repository.TrxSavingRepository;
import com.enigmacamp.enigmacoop.service.NasabahService;
import com.enigmacamp.enigmacoop.service.SavingService;
import com.enigmacamp.enigmacoop.service.TrxSavingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TrxSavingServiceImpl implements TrxSavingService {

    private final TrxSavingRepository trxSavingRepository;
    private final SavingService savingService;
    private final NasabahService nasabahService;

    // Register nasabah - data saving
    @Override
    @Transactional(rollbackOn = Exception.class)
    public TrxSaving createTrxSaving(TrxSavingRequest trxSavingRequest) {
        Saving saving = savingService.getSavingById(trxSavingRequest.getSavingId());
        if (trxSavingRequest.getSavingType().equals(SavingType.DEBIT)){
           saving.setBalance(saving.getBalance()+trxSavingRequest.getAmount());
        }else{
            if (trxSavingRequest.getAmount() < saving.getBalance()) {
                saving.setBalance(saving.getBalance()-trxSavingRequest.getAmount());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Saldo Tidak cukup");
            }
        }
        TrxSaving newTrxSaving = TrxSaving.builder()
                .amount(trxSavingRequest.getAmount())
                .saving(saving)
                .savingType(trxSavingRequest.getSavingType())
                .build();
        return trxSavingRepository.saveAndFlush(newTrxSaving);
    }

    @Override
    public List<TrxSaving> getListTrxSaving() {
        return trxSavingRepository.findAll();
    }
}