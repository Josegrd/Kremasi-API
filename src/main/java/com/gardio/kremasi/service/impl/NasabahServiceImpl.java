package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.constant.NasabahStatus;
import com.gardio.kremasi.entity.Nasabah;
import com.gardio.kremasi.entity.Saving;
import com.gardio.kremasi.entity.UserCredential;
import com.gardio.kremasi.model.request.NasabahRequest;
import com.gardio.kremasi.repository.NasabahRepository;
import com.gardio.kremasi.service.NasabahService;
import com.gardio.kremasi.service.SavingService;
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
        if (nasabahRepository.findByEmail(payload.getEmail()).filter(n -> !n.getId().equals(payload.getId())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (nasabahRepository.findByPhoneNumber(payload.getPhoneNumber()).filter(n -> !n.getId().equals(payload.getId())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        if (nasabahRepository.findByNik(payload.getNik()).filter(n -> !n.getId().equals(payload.getId())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "NIK already exists");
        }
        nasabah.setFullName(payload.getFullName());
        nasabah.setEmail(payload.getEmail());
        nasabah.setPhoneNumber(payload.getPhoneNumber());
        nasabah.setAddress(payload.getAddress());
        nasabah.setNik(payload.getNik());
        nasabah.setBirthDate(payload.getBirthDate());
        return nasabahRepository.saveAndFlush(nasabah);
    }

    @Override
    public Boolean findByEmail(String email) {
        Boolean checkEmail = nasabahRepository.findByEmail(email).isPresent();
        if (checkEmail) return true;
        return false;
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


    @Override
    public Nasabah findNasabahByUserCredential(String userCredential) {
        Nasabah optionalNasabah = nasabahRepository.getNasabahByUserCredential_Id(userCredential);
        System.out.println(optionalNasabah);
        if (optionalNasabah != null) return optionalNasabah;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nasabah Not Found");
    }

    private Nasabah findByIdOrThrowNotFound(String id){
        return nasabahRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not Found"));
    }



}
