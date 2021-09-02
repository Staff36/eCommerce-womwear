package ru.tronin.routinglib.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CityDto {
    Long id;
    String name;
    RegionDto region;
}
