package com.gardio.kremasi.repository;

import com.gardio.kremasi.constant.ERole;
import com.gardio.kremasi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByRole(ERole role);
}
