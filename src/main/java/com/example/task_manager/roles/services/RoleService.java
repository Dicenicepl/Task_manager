package com.example.task_manager.roles.services;

import com.example.task_manager.roles.entities.Role;
import com.example.task_manager.roles.entities.RoleList;
import com.example.task_manager.roles.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(String email){
        Role role = new Role(email, RoleList.USER);
        try {
            roleRepository.save(role);
        }catch (IllegalArgumentException ignored){
        }
    }
    public boolean deleteRole(String email){
        Role role = roleRepository.findRoleByEmail(email);
        try {
            roleRepository.delete(role);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public Collection<Role> findRolesByEmail(String email) {
        return roleRepository.findRolesByEmail(email);
    }
}
