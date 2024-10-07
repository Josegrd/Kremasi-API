package com.gardio.kremasi.service;

import com.gardio.kremasi.constant.ERole;
import com.gardio.kremasi.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
