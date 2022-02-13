package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleName(String name);
    boolean deleteRoleById(Long id);
}
