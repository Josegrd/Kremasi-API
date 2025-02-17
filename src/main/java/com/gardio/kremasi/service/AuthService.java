package com.gardio.kremasi.service;

import com.gardio.kremasi.model.request.AuthRequest;
import com.gardio.kremasi.model.request.NasabahRequest;
import com.gardio.kremasi.model.response.LoginResponse;
import com.gardio.kremasi.model.response.NasabahResponse;

public interface AuthService {
    NasabahResponse register(NasabahRequest nasabahRequest);
    LoginResponse login(AuthRequest authRequest);
    Boolean checkEmailUsername(String username, String email);
}
