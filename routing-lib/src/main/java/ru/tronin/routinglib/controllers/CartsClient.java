package ru.tronin.routinglib.controllers;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.tronin.routinglib.dtos.CartDto;

import java.util.UUID;

@FeignClient("ms-orders")
public interface CartsClient {

    @PostMapping
    UUID createNewCart(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token);

    @GetMapping("/{uuid}")
    CartDto getCurrentCart(@PathVariable UUID uuid);

    @PostMapping("/add")
    void addProductToCart(@RequestParam UUID uuid, @RequestParam(name = "product_id") Long productId);

    @PostMapping("/clear")
    void clearCart(@RequestParam UUID uuid);
}
