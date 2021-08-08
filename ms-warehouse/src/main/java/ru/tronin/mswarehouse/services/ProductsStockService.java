package ru.tronin.mswarehouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.mswarehouse.entities.ProductsStock;
import ru.tronin.mswarehouse.entities.ProductsStockDto;
import ru.tronin.mswarehouse.repositories.ProductsStockRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsStockService {

    @Autowired
    ProductsStockRepository productsStockRepository;

    public ProductsStockDto getStockByProductId(Long id){
        ProductsStock productFromDb = findProductStockById(id);
        return new ProductsStockDto(productFromDb);
    }

    public List<ProductsStockDto> getAllStocks() {
        List<ProductsStock> productsStocks = productsStockRepository.findAll();
        return productsStocks.stream().map(ProductsStockDto::new).collect(Collectors.toList());
    }

    public boolean decreaseStockOnQuantity(Long id, Long quantity) {
        ProductsStock productsStockFromDB = findProductStockById(id);
        Long stocksQuantity = productsStockFromDB.getQuantity();
        if (stocksQuantity < 0){
            return false;
        }
        productsStockFromDB.setQuantity(stocksQuantity - quantity);
        productsStockRepository.save(productsStockFromDB);
        return true;
    }

    public void increaseStockOnQuantity(Long id, Long quantity){
        ProductsStock productsStockFromDB = findProductStockById(id);
        productsStockFromDB.setQuantity(productsStockFromDB.getQuantity() + quantity);
        productsStockRepository.save(productsStockFromDB);
    }

    private ProductsStock findProductStockById(Long id){
        return productsStockRepository.findProductsStockByProduct(id).orElseThrow(()-> new NoEntityException("Product with id " + id + "not found"));
    }
}
