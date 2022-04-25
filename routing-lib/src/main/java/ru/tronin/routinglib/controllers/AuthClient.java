package ru.tronin.routinglib.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.tronin.routinglib.dtos.AuthRequestDto;
import ru.tronin.routinglib.dtos.AuthResponseDto;
import ru.tronin.routinglib.dtos.SignUpRequestDto;

@FeignClient("ms-auth")
public interface AuthClient {

    @PostMapping("/signup")
    void registerUser(@RequestBody SignUpRequestDto signUpRequest);

    @PostMapping("/login")
    public AuthResponseDto authorize(@RequestBody AuthRequestDto request);

    @PostMapping("/logout")
    void logout(@RequestHeader(name = "Authorization") String token);

}
