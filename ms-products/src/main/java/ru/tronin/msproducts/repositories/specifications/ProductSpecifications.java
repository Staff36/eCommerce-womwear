package ru.tronin.msproducts.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tronin.msproducts.models.entities.Product;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThan(Double minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice);
    }

    public static Specification<Product> priceLowerOrEqualsThen(Double maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxPrice);
    }

    public static Specification<Product> nameLike(String namesPart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", namesPart));
    }

}
