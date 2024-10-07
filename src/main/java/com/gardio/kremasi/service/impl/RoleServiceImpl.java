package com.gardio.kremasi.service.impl;

import com.gardio.kremasi.constant.ERole;
import com.gardio.kremasi.entity.Role;
import com.gardio.kremasi.repository.RoleRepository;
import com.gardio.kremasi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        // kalau role ada maka akan kita ambil
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if (optionalRole.isPresent()) return optionalRole.get();
        // jika tidak ada, maka akan disimpan
        Role newRole =  Role.builder()
                .role(role)
                .build();
        return roleRepository.saveAndFlush(newRole);
    }
}
