package ru.tronin.routinglib.dtos;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {

    UUID id;

    List<CartProductDto> items;

    Double price;

    Long userId;

}
