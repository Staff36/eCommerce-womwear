package ru.tronin.routinglib.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tronin.routinglib.dtos.ProductDto;

@RestController
@RequestMapping("/routing")
public class FeignController {
    @Autowired
    ProductsClient client;

    @GetMapping("/{id}")
    ProductDto showProduct(@PathVariable Long id) {
        return client.showProduct(id);
    }
}
