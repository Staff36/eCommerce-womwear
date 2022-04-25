package ru.tronin.routinglib.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderedProductDto {

    Long productId;

    Long quantity;

    Double orderedProductPrice;

    Double totalPrice;

}
