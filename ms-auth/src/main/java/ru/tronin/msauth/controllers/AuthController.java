package ru.tronin.msauth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tronin.corelib.interfaces.ITokenService;
import ru.tronin.corelib.models.UserInfo;
import ru.tronin.msauth.models.dto.AuthRequestDto;
import ru.tronin.msauth.models.dto.AuthResponseDto;
import ru.tronin.msauth.models.dto.SignUpRequestDto;
import ru.tronin.msauth.models.entities.users.Role;
import ru.tronin.msauth.models.entities.users.User;
import ru.tronin.msauth.services.RolesService;
import ru.tronin.msauth.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    ITokenService tokenService;

    @Autowired
    RolesService rolesService;


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody SignUpRequestDto signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        List<Role> roles = rolesService.findRolesByRolesNames(signUpRequest.getRoles());
        user.setRoles(roles);
        userService.saveUser(user);
    }

    @PostMapping("/login")
    public AuthResponseDto authorize(@RequestBody AuthRequestDto request){
        User user =  userService.findByEmailAndPassword(request.getEmail(), request.getPassword());
        List<String> roles =  new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .build();
        String token = tokenService.generateToken(userInfo);
        return new AuthResponseDto(token);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(name = "Authorization") String token){
        tokenService.putTokenToRedis(token);
    }

}
