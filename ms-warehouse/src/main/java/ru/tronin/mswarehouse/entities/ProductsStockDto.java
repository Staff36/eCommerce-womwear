package ru.tronin.mswarehouse.entities;

import lombok.Data;

@Data
public class ProductsStockDto {
    Long product;
    Long quantity;

    public ProductsStockDto(ProductsStock productStock) {
        this.product = productStock.getProduct();
        this.quantity = productStock.getQuantity();
    }
}
