package ru.tronin.routinglib.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tronin.routinglib.dtos.ProductDto;

import java.util.List;

@Component
@FeignClient("ms-products")
public interface ProductsClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDto showProduct(@PathVariable Long id);

    @GetMapping("/ids")
    List<ProductDto> getProdictsList(@RequestParam List<Long> ids);
}
