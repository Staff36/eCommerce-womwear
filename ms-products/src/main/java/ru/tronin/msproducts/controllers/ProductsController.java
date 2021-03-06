package ru.tronin.msproducts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tronin.msproducts.models.entities.Product;
import ru.tronin.msproducts.services.ProductService;
import ru.tronin.routinglib.dtos.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

//for ht

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    @Value("${jwt.secret}")
    private String MY_SECRET;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public Page<ProductDto> showPaginatedProducts(@PageableDefault(size = 12) Pageable pageable,
                                                  @RequestParam(name = "min_price", required = false) Double min,
                                                  @RequestParam(name = "max_price", required = false) Double max,
                                                  @RequestParam(name = "name_part", required = false) String partName) {
    return productService.findPaginatedProducts(min, max, partName, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto showProduct(@PathVariable Long id) {
        return productService.getEntityById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        productService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/edit")
    public ProductDto editProduct(@PathVariable Long id) {
        return productService.getEntityById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDto product, @PathVariable Long id) {
        productService.updateProduct(product, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/ids")
    public List<ProductDto> getProdictsList(@RequestParam List<Long> ids) {
        return productService.findProductByIdsList(ids);
    }
}
