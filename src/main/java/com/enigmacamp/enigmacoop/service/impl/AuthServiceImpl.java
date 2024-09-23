package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.ERole;
import com.enigmacamp.enigmacoop.entity.Nasabah;
import com.enigmacamp.enigmacoop.entity.Role;
import com.enigmacamp.enigmacoop.entity.UserCredential;
import com.enigmacamp.enigmacoop.model.request.AuthRequest;
import com.enigmacamp.enigmacoop.model.request.NasabahRequest;
import com.enigmacamp.enigmacoop.model.response.NasabahResponse;
import com.enigmacamp.enigmacoop.repository.UserCredentialRepository;
import com.enigmacamp.enigmacoop.security.JwtUtils;
import com.enigmacamp.enigmacoop.service.AuthService;
import com.enigmacamp.enigmacoop.service.NasabahService;
import com.enigmacamp.enigmacoop.service.RoleService;
import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final UserCredentialRepository credentialRepository;
    private final NasabahService nasabahService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Value("${app.enigma-coop.username-admin}")
    private String usernameAdmin;

    @Value("${app.enigma-coop.password-admin}")
    private String passwordAdmin;

    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCred = credentialRepository.findByUsername(usernameAdmin);
        if (optionalUserCred.isPresent()) return;

        Role superAdminRole = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role customerRole = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(passwordAdmin);

        UserCredential userCredential = UserCredential.builder()
                .username(usernameAdmin)
                .password(hashPassword)
                .roles(List.of(superAdminRole,adminRole,customerRole))
                .build();
        credentialRepository.saveAndFlush(userCredential);
    }

    @Override
    public NasabahResponse register(NasabahRequest nasabahRequest) {
        // untuk role
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        // hash password
        String hashPassword = passwordEncoder.encode(nasabahRequest.getPassword());
        UserCredential userCred = credentialRepository.saveAndFlush(
                UserCredential.builder()
                        .username(nasabahRequest.getUsername())
                        .password(hashPassword)
                        .roles(List.of(roleCustomer))
                        .build());
        Nasabah nasabah = nasabahService.createNasabah(nasabahRequest, userCred);
        // list role
        List<String> roles = userCred.getRoles().stream().map(role -> role.getRole().name()).toList();
        return NasabahResponse.builder()
                .id(nasabah.getId())
                .fullName(nasabah.getFullName())
                .email(nasabah.getEmail())
                .phoneNumber(nasabah.getPhoneNumber())
                .address(nasabah.getAddress())
                .username(nasabahRequest.getUsername())
                .nik(nasabahRequest.getNik())
                .birthDate(nasabahRequest.getBirthDate())
                .roles(roles)
                .build();
    }

    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        // call method untuk kebutuhan validasi credential
        Authentication authenticated = authenticationManager.authenticate(authentication);
        // jika valid username dan password, maka selanjutnya simpan sesi untuk akses resource tertentu
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        // berikan token
        UserCredential userCredential = (UserCredential) authenticated.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }
}
