package ru.tronin.msauth.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msauth.models.entities.users.Role;
import ru.tronin.msauth.models.entities.users.User;
import ru.tronin.msauth.repositories.RoleRepository;
import ru.tronin.msauth.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesService rolesService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        String role = "ROLE_USER";
        List<Role> roles = List.of(rolesService.findByName(role));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User findByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoEntityException(String.format("User with email %s not found", email)));
        Hibernate.initialize(user.getRoles());
        return user;
    }

    public User findByEmailAndPassword(String email, String password) {
        User userFromDB = findByEmail(email);
        if (userFromDB != null) {
            if (passwordEncoder.matches(password, userFromDB.getPassword())) {
                return userFromDB;
            }
        }
        return null;
    }




}
