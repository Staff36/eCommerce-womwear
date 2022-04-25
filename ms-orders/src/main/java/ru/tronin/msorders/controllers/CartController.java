package ru.tronin.msorders.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tronin.corelib.interfaces.ITokenService;
import ru.tronin.corelib.models.UserInfo;
import ru.tronin.msorders.services.CartService;
import ru.tronin.routinglib.dtos.CartDto;

import java.security.Principal;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    @Autowired
    CartService cartService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createNewCart() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || principal instanceof String) {
            return cartService.getCartForUser(null, null);
        }
        UserInfo userInfo = (UserInfo) principal;
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