package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.entity.UserCredential;
import com.gardio.kremasi.repository.UserCredentialRepository;
import com.gardio.kremasi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserCredentialRepository repository;

    @Override
    public UserCredential loadByUserId(String userId) {
        return repository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Load by user id is fail"));
    }

    @Override
    public UserCredential findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Load by user username is fail"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Load by username is fail"));
    }
}
