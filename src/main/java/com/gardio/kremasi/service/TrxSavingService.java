package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.TrxSaving;
import com.gardio.kremasi.model.request.TrxSavingRequest;

import java.util.List;

public interface TrxSavingService {
    TrxSaving createTrxSaving(TrxSavingRequest trxSavingRequest);
    List<TrxSaving> getListTrxSaving();
}
