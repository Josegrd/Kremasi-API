package com.gardio.kremasi.service;

import com.gardio.kremasi.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
    UserCredential findByUsername(String username);
}