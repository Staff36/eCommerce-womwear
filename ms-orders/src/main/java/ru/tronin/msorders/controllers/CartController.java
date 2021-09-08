package ru.tronin.msorders.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tronin.corelib.interfaces.ITokenService;
import ru.tronin.corelib.models.UserInfo;
import ru.tronin.msorders.services.CartService;
import ru.tronin.routinglib.dtos.CartDto;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ITokenService tokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createNewCart(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        if (token == null) {
            return cartService.getCartForUser(null, null);
        }
        UserInfo userInfo = tokenService.parseToken(token);
        return cartService.getCartForUser(userInfo.getId(), null);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@PathVariable UUID uuid) {
        return cartService.findById(uuid);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam UUID uuid, @RequestParam(name = "product_id") Long productId) {
        cartService.addProductToCart(uuid, productId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam UUID uuid) {
        cartService.clearCart(uuid);
    }
}