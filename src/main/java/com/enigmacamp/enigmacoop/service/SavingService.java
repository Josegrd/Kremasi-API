package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.Saving;

import java.util.List;

public interface SavingService {
    Saving createSaving(Saving saving);
    List<Saving> getListSaving();
    Saving getSavingById(String id);
    Saving getSavingByNasabahId(String id);
}
