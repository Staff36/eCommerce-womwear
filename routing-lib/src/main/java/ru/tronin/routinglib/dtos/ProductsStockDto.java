package ru.tronin.routinglib.dtos;

import lombok.Data;

@Data
public class ProductsStockDto {
    Long id;

    Long product;

    Long quantity;

}
