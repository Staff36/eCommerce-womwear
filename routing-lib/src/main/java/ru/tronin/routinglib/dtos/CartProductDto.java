package ru.tronin.routinglib.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartProductDto {

    Long productId;

    Integer quantity;

    Double costPerProduct;

    Double price;

    String productTitle;

}
