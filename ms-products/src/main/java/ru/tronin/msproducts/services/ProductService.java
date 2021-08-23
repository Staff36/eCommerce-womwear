package ru.tronin.msproducts.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msproducts.models.entities.Product;
import ru.tronin.msproducts.repositories.ProductRepository;
import ru.tronin.msproducts.repositories.specifications.ProductSpecifications;
import ru.tronin.routinglib.dtos.ProductDto;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProductDto getEntityById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new NoEntityException("Entity with id =" + id + " not found");
        }
        return mapProductToDto(product.get());
    }

    public Page<ProductDto> findPaginatedProducts(Double min, Double max, String partName, Pageable pageable) {
        Specification<Product> specification = Specification.where(null);
        if (min != null) {
            specification = specification.and(ProductSpecifications.priceGreaterOrEqualsThan(min));
        }
        if (max != null) {
            specification = specification.and(ProductSpecifications.priceLowerOrEqualsThen(max));
        }
        if (partName != null) {
            specification = specification.and(ProductSpecifications.nameLike(partName));
        }

        return productRepository.findAll(specification, pageable).map(this::mapProductToDto);
    }

    public void updateProduct(ProductDto product, Long id) {
        ProductDto productFromDB = getEntityById(id);

    }

    private ProductDto mapProductToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

    public void create(Product product) {
        productRepository.saveAndFlush(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
}
