package com.enigmacamp.enigmacoop.service.impl;

import com.enigmacamp.enigmacoop.constant.ERole;
import com.enigmacamp.enigmacoop.entity.Role;
import com.enigmacamp.enigmacoop.repository.RoleRepository;
import com.enigmacamp.enigmacoop.service.RoleService;
import lombok.AllArgsConstructor;
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
