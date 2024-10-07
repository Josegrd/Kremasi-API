package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.constant.ERole;
import com.gardio.kremasi.entity.Nasabah;
import com.gardio.kremasi.entity.Role;
import com.gardio.kremasi.entity.UserCredential;
import com.gardio.kremasi.model.request.AuthRequest;
import com.gardio.kremasi.model.request.NasabahRequest;
import com.gardio.kremasi.model.response.LoginResponse;
import com.gardio.kremasi.model.response.NasabahResponse;
import com.gardio.kremasi.repository.UserCredentialRepository;
import com.gardio.kremasi.security.JwtUtils;
import com.gardio.kremasi.service.AuthService;
import com.gardio.kremasi.service.NasabahService;
import com.gardio.kremasi.service.RoleService;
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

    @Value("${app.kremasi.username-admin}")
    private String usernameAdmin;

    @Value("${app.kremasi.password-admin}")
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
    public LoginResponse login(AuthRequest authRequest) {
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
        return LoginResponse.builder()
                .token(jwtUtils.generateToken(userCredential))
                .role(userCredential.getRoles().get(0).getRole().name())
                .build();
    }
}
