package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.model.request.AuthRequest;
import com.enigmacamp.enigmacoop.model.request.NasabahRequest;
import com.enigmacamp.enigmacoop.model.response.NasabahResponse;

public interface AuthService {
    NasabahResponse register(NasabahRequest nasabahRequest);
    String login(AuthRequest authRequest);
}
