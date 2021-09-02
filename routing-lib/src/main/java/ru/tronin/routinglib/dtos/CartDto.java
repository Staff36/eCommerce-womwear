package ru.tronin.routinglib.dtos;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {

    List<CartProductDto> items;
    Double price;

}
