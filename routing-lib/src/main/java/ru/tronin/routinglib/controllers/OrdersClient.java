package ru.tronin.routinglib.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ru.tronin.routinglib.dtos.OrderDto;

import java.util.List;
import java.util.UUID;


@FeignClient("ms-orders")
public interface OrdersClient {

    @PostMapping
    OrderDto createOrderFromCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam UUID cartUuid, @RequestParam String address);

    @GetMapping("/{id}")
    OrderDto getOrderById(@PathVariable Long id);

    @GetMapping
    List<OrderDto> getCurrentUserOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    


}
