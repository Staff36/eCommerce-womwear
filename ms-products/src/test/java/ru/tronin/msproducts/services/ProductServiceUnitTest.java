package ru.tronin.msproducts.services;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msproducts.models.entities.Category;
import ru.tronin.msproducts.models.entities.Product;
import ru.tronin.msproducts.repositories.CategoriesRepository;
import ru.tronin.msproducts.repositories.ProductRepository;
import ru.tronin.routinglib.dtos.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {ProductService.class, ModelMapper.class})
public class ProductServiceUnitTest {

    @MockBean
    ProductRepository productRepository;



    @Autowired
    ProductService productService;

    final static Long ID = 1L;
    final static String PRODUCT_NAME = "test name";
    final static String PRODUCT_DESCRIPTION = "test description";
    final static Double PRODUCT_COST = 2.23d;
    final static String CATEGORY_NAME = "test category";
    final static Product testProduct = new Product();
    final static Category testCategory = new Category();

    @BeforeEach
    public void init(){
        testProduct.setId(1L);
        testCategory.setName(CATEGORY_NAME);
        testProduct.setName(PRODUCT_NAME);
        testProduct.setDescription(PRODUCT_DESCRIPTION);
        testProduct.setCost(PRODUCT_COST);
        testProduct.setCategory(testCategory);
    }

    @Test
    public void getEntityByIdTest() {
        doReturn(Optional.of(testProduct))
                .when(productRepository)
                .findById(ID);
        doReturn(Optional.of(null))
                .when(productRepository)
                .findById(2L);

        ProductDto entityById = productService.getEntityById(1L);
        assertEquals(ID, entityById.getId());
        assertEquals(PRODUCT_NAME, entityById.getName());
        assertEquals(PRODUCT_DESCRIPTION, entityById.getDescription());
        assertEquals(PRODUCT_COST, entityById.getCost());
        assertEquals(CATEGORY_NAME, entityById.getCategory());
        assertThrows(NoEntityException.class, ()-> productService.getEntityById(2L));
        assertThrows(IllegalArgumentException.class, ()-> productService.getEntityById(null));
        assertThrows(IllegalArgumentException.class, ()-> productService.getEntityById(-500L));
    }
//    @Test
    public void findPaginatedProductsTest(){
        PageImpl<Product> products = new PageImpl<>(List.of(testProduct));
        Specification<Product> specification = Specification.where(null);
        Pageable pageable = PageRequest.ofSize(1);
        Mockito
                .doReturn(products)
                .when(productRepository)
                .findAll(specification, pageable);
//        Page<ProductDto> paginatedProducts = productService.findPaginatedProducts(1d, 1d, "", pageable);

        assertEquals(testProduct, products.getContent().get(0));
    }
    @Test
    public void findProductByIdsList(){
        List<Long> ids =  List.of(ID);
        Mockito
                .doReturn(List.of(testProduct))
                .when(productRepository)
                .findAllByIdIn(ids);
        List<ProductDto> productByIdsList = productService.findProductByIdsList(ids);
        ProductDto productDto = productByIdsList.get(0);
        assertEquals(PRODUCT_NAME, productDto.getName());
        assertEquals(PRODUCT_DESCRIPTION, productDto.getDescription());
        assertEquals(PRODUCT_COST, productDto.getCost());
        assertEquals(CATEGORY_NAME, productDto.getCategory());
//        null
        assertThrows(IllegalArgumentException.class, ()-> productService.findProductByIdsList(null));
        assertThrows(NoEntityException.class, ()-> productService.findProductByIdsList(new ArrayList<>()));

    }

}
