package ru.tronin.routinglib.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.tronin.routinglib.dtos.ProductDto;

import java.beans.ConstructorProperties;

@RestController
@RequestMapping("/routing")
public class FeignController{
    @Autowired
    ProductsClient client;

    @GetMapping("/show")
    public Page<ProductDto> index(@PageableDefault(size = 12) Pageable pageable,
                                  @RequestParam(name = "min_price", required = false) Double min,
                                  @RequestParam(name = "max_price", required = false) Double max,
                                  @RequestParam(name = "name_part", required = false) String partName) {
        int i = 22;
        Page<ProductDto> index = client.index(pageable, min, max, partName);
        return index;
    }

    @GetMapping("/test")
    public String get(){
        return client.testingMethod();
    }

    @GetMapping("/{id}")
    ProductDto showProduct(@PathVariable Long id) {
    return client.showProduct(id);
    }
}
