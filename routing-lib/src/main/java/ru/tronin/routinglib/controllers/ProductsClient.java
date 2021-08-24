package ru.tronin.routinglib.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tronin.routinglib.dtos.ProductDto;
import ru.tronin.routinglib.dtos.RestPageImpl;

import java.beans.ConstructorProperties;

@FeignClient("ms-products")
public interface ProductsClient {

    @GetMapping("/api/v1/products")
    RestPageImpl<ProductDto> index(@PageableDefault(size = 12) Pageable pageable,
                                   @RequestParam(name = "min_price", required = false) Double min,
                                   @RequestParam(name = "max_price", required = false) Double max,
                                   @RequestParam(name = "name_part", required = false) String partName);

    @GetMapping("/api/v1/products/test")
    String testingMethod();

    @GetMapping("/api/v1/products/{id}")
    ProductDto showProduct(@PathVariable Long id);
}
