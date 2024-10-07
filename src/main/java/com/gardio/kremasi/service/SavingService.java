package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.Saving;

import java.util.List;

public interface SavingService {
    Saving createSaving(Saving saving);
    List<Saving> getListSaving();
    Saving getSavingById(String id);
    Saving getSavingByNasabahId(String id);
}
