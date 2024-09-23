package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.TrxSaving;
import com.enigmacamp.enigmacoop.model.request.TrxSavingRequest;

import java.util.List;

public interface TrxSavingService {
    TrxSaving createTrxSaving(TrxSavingRequest trxSavingRequest);
    List<TrxSaving> getListTrxSaving();
}
