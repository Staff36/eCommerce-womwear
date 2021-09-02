package ru.tronin.mswarehouse.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.mswarehouse.entities.ProductsStock;
import ru.tronin.mswarehouse.repositories.ProductsStockRepository;
import ru.tronin.routinglib.dtos.ProductsStockDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsStockService {

    @Autowired
    ProductsStockRepository productsStockRepository;

    @Autowired
    ModelMapper modelMapper;

    public ProductsStockDto getStockByProductId(Long id) {
        return mapToDto(findProductStockById(id));
    }

    public List<ProductsStockDto> getAllStocks() {
        List<ProductsStock> productsStocks = productsStockRepository.findAll();
        return productsStocks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public boolean decreaseStockOnQuantity(Long id, Long quantity) {
        ProductsStock productsStockFromDB = findProductStockById(id);
        Long stocksQuantity = productsStockFromDB.getQuantity();
        if (stocksQuantity < 0) {
            return false;
        }
        productsStockFromDB.setQuantity(stocksQuantity - quantity);
        productsStockRepository.save(productsStockFromDB);
        return true;
    }

    public void increaseStockOnQuantity(Long id, Long quantity) {
        ProductsStock productsStockFromDB = findProductStockById(id);
        productsStockFromDB.setQuantity(productsStockFromDB.getQuantity() + quantity);
        productsStockRepository.save(productsStockFromDB);
    }

    private ProductsStock findProductStockById(Long id) {
        return productsStockRepository.findProductsStockByProduct(id).orElseThrow(() -> new NoEntityException("Product with id " + id + "not found"));
    }

    private ProductsStockDto mapToDto(ProductsStock productsStock) {
        return modelMapper.map(productsStock, ProductsStockDto.class);
    }
}
