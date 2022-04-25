package ru.tronin.msauth.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msauth.models.entities.users.Role;
import ru.tronin.msauth.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolesService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name)
                .orElseThrow(()-> new NoEntityException(String.format("Role with name %s not found", name)));
    }

    public List<Role> findRolesByRolesNames(List<String> rolesNames){
        List<Role> usersRoles = new ArrayList<>();
        if (rolesNames == null || rolesNames.isEmpty()){
            usersRoles.add(findByName("ROLE_USER"));
        } else {
            rolesNames.forEach(roleByRequest -> usersRoles.add(findByName(roleByRequest)));
        }
        return usersRoles;
    }
}
