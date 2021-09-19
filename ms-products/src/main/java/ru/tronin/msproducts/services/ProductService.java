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
import ru.tronin.msproducts.repositories.CategoriesRepository;
import ru.tronin.msproducts.repositories.ProductRepository;
import ru.tronin.msproducts.repositories.specifications.ProductSpecifications;
import ru.tronin.routinglib.dtos.ProductDto;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoriesRepository repository;


    public ProductDto getEntityById(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Incorrect value for Product " + id);
        }
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
        Product productFromDB = productRepository.findById(id).orElseThrow(() -> new NoEntityException("Entity, you try to update- not found"));
        productFromDB.setName(product.getName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setCost(product.getCost());
        productFromDB.setCategory(getRepository().findCategoryByName(product.getCategory()));
        productRepository.save(productFromDB);
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

    public List<ProductDto> findProductByIdsList(List<Long> ids) {
        return productRepository.findAllByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
    }

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
