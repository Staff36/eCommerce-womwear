package ru.tronin.msproducts.repositories;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msproducts.models.entities.Category;
import ru.tronin.msproducts.models.entities.Product;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    final static String PRODUCT_NAME = "test name";
    final static String PRODUCT_DESCRIPTION = "test description";
    final static Double PRODUCT_COST = 2.23d;

    final static Product testProduct = new Product();
    final static Category testCategory = new Category();

    final static String CATEGORY_NAME = "test category";

    @BeforeAll
    public static void initEntities() {
        testProduct.setName(PRODUCT_NAME);
        testProduct.setDescription(PRODUCT_DESCRIPTION);
        testProduct.setCost(PRODUCT_COST);
        testProduct.setCategory(testCategory);
        testCategory.setName(CATEGORY_NAME);

        testCategory.setProducts(List.of(testProduct));
    }


    @Test
    public void saveTest() {
        categoriesRepository.save(testCategory);
        List<Product> allEntitiesBefore = productRepository.findAll();
        final int sizeBefore = allEntitiesBefore.size();
        productRepository.save(testProduct);
        assertEquals(sizeBefore + 1, productRepository.findAll().size());
        Product product = productRepository.findProductByName(PRODUCT_NAME).get();
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRODUCT_DESCRIPTION, product.getDescription());
        assertEquals(PRODUCT_COST, product.getCost());
        assertThrows(InvalidDataAccessApiUsageException.class, () -> productRepository.getById(null));
        productRepository.delete(testProduct);
        categoriesRepository.delete(testCategory);
    }


    }
