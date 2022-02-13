package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.RoleRepo;
import com.shoppingsuite.persistence.model.Role;
import com.shoppingsuite.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Optional<Role> getRoleName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        try{
            roleRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
