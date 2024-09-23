package com.enigmacamp.enigmacoop.service;

import com.enigmacamp.enigmacoop.constant.ERole;
import com.enigmacamp.enigmacoop.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
