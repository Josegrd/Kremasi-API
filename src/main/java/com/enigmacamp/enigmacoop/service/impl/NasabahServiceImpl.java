package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.NasabahStatus;
import com.enigmacamp.enigmacoop.entity.Employee;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.entity.Saving;
import com.enigmacamp.enigmacoop.entity.UserCredential;
import com.enigmacamp.enigmacoop.model.request.NasabahRequest;
import com.enigmacamp.enigmacoop.repository.NasabahRepository;
import com.enigmacamp.enigmacoop.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NasabahServiceImpl implements NasabahService {

    private final NasabahRepository nasabahRepository;
    private final SavingService savingService;

    @Override
    public Nasabah createNasabah(NasabahRequest nasabahRequest, UserCredential userCredential) {
        Nasabah nasabah = Nasabah.builder()
                .fullName(nasabahRequest.getFullName())
                .email(nasabahRequest.getEmail())
                .phoneNumber(nasabahRequest.getPhoneNumber())
                .birthDate(nasabahRequest.getBirthDate())
                .nik(nasabahRequest.getNik())
                .address(nasabahRequest.getAddress())
                .status(NasabahStatus.ACTIVE)
                .userCredential(userCredential)
                .build();
        Nasabah newNasabah = nasabahRepository.saveAndFlush(nasabah);
        // setiap register nasabah, maka akan dibuatkan data saving secara otomatis dengan
        // data awal saldonya adalah 0
        Saving newSaving = Saving.builder()
                .balance(0L)
                .nasabah(newNasabah)
                .build();
        savingService.createSaving(newSaving);
        return newNasabah;
    }

    @Override
    public Page<Nasabah> getAllNasabah(Integer page, Integer size) {
        // Page berapa
        // Size berapa per page
        if (page <= 0 ) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1,size);
        return nasabahRepository.findAll(pageable);
    }

    @Override
    public Nasabah getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public Nasabah update(NasabahRequest payload) {
        Nasabah nasabah = findByIdOrThrowNotFound(payload.getId());
        nasabah.setFullName(payload.getFullName());
        nasabah.setEmail(payload.getEmail());
        nasabah.setPhoneNumber(payload.getPhoneNumber());
        nasabah.setAddress(payload.getAddress());
        nasabah.setNik(payload.getNik());
        nasabah.setBirthDate(payload.getBirthDate());
        return nasabahRepository.saveAndFlush(nasabah);
    }

    @Override
    public void deleteById(String id) {
        this.getById(id);
        nasabahRepository.deleteById(id);
    }

    @Override
    public Nasabah findByUsername(String username) {
        Optional<Nasabah> optionalNasabah = Optional.ofNullable(nasabahRepository.getNasabahByUserCredential_Username(username));
        if (optionalNasabah.isPresent()) return  optionalNasabah.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nasabah Not Found");
    }

    private Nasabah findByIdOrThrowNotFound(String id){
        return nasabahRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not Found"));
    }


}
