package ru.tronin.mswarehouse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tronin.mswarehouse.entities.ProductsStockDto;
import ru.tronin.mswarehouse.services.ProductsStockService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-stocks")
public class ProductStocksController {

    @Autowired
    ProductsStockService productsStockService;


    @GetMapping
    public List<ProductsStockDto> getAllStocks(){
        return productsStockService.getAllStocks();
    }

    @PostMapping("/{id}/decrease")
    public boolean decreaseStockOnQuantity(@PathVariable Long id, Long quantity){
        return productsStockService.decreaseStockOnQuantity(id, quantity);
    }

    @PostMapping("/{id}/increase")
    public boolean increaseStockOnQuantity(@PathVariable Long id, Long quantity){
        productsStockService.increaseStockOnQuantity(id, quantity);
        return true;
    }
    @GetMapping("/{id}")
    public ProductsStockDto getProductsStockById(@PathVariable Long id){
        return productsStockService.getStockByProductId(id);
    }


}
