package ru.tronin.msproducts.models.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.tronin.msproducts.models.entities.Product;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProductDto {
    Long id;
    String name;
    String description;
    Double cost;
    String category;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.cost = product.getCost();
        this.category = product.getCategory().getName();
    }
}
