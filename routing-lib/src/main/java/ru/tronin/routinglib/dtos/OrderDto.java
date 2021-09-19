package ru.tronin.routinglib.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    Long id;

    Long userId;

    List<OrderedProductDto> products;

    String address;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;


}
