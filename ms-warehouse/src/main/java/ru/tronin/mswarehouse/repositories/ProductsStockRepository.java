package ru.tronin.mswarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tronin.mswarehouse.entities.ProductsStock;

import java.util.Optional;

public interface ProductsStockRepository extends JpaRepository<ProductsStock, Long> {

    Optional<ProductsStock> findProductsStockByProduct(Long product);
}
