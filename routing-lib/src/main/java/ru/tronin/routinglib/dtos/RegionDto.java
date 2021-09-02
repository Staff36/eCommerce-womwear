package ru.tronin.routinglib.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RegionDto {

    Long id;
    String name;
    CountryDto country;
}
