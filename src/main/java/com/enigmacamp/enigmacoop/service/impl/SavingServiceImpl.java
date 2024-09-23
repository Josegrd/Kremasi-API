package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.entity.Saving;
import com.enigmacamp.enigmacoop.repository.SavingRepository;
import com.enigmacamp.enigmacoop.service.SavingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;

    @Override
    public Saving createSaving(Saving saving) {
        return savingRepository.saveAndFlush(saving);
    }

    @Override
    public List<Saving> getListSaving() {
        return savingRepository.findAll();
    }

    @Override
    public Saving getSavingById(String id) {
        Optional<Saving> optionalSaving = savingRepository.findById(id);
        if (optionalSaving.isPresent()) return optionalSaving.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Saving is not found");
    }

    @Override
    public Saving getSavingByNasabahId(String id) {
        Optional<Saving> optionalSaving = Optional.ofNullable(savingRepository.getSavingByNasabahId(id));
        if (optionalSaving.isPresent()) return optionalSaving.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Saving is not found");
    }

}
