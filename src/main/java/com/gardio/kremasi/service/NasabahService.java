package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.Nasabah;
import com.gardio.kremasi.entity.UserCredential;
import com.gardio.kremasi.model.request.NasabahRequest;
import org.springframework.data.domain.Page;

public interface NasabahService {
    Nasabah createNasabah(NasabahRequest nasabahRequest, UserCredential userCredential);
    Page<Nasabah> getAllNasabah(Integer page, Integer size);
    Nasabah getById(String id);
    Nasabah update(NasabahRequest payload);
    void deleteById(String id);
    Nasabah findByUsername(String name);
    Boolean findByEmail(String email);

    Nasabah findNasabahByUserCredential(String userCredential);
}
