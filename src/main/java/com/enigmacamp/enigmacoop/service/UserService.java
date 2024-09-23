package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
    UserCredential findByUsername(String username);
}