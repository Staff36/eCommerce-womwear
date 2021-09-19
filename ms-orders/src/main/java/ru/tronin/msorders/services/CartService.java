package ru.tronin.msorders.services;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msorders.models.Cart;
import ru.tronin.msorders.models.CartProduct;
import ru.tronin.msorders.repositories.CartRepository;
import ru.tronin.routinglib.controllers.ProductsClient;
import ru.tronin.routinglib.dtos.CartDto;
import ru.tronin.routinglib.dtos.CartProductDto;
import ru.tronin.routinglib.dtos.ProductDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartService {
    @Autowired
    CartRepository repository;
    @Autowired
    ProductsClient productsClient;
    @Autowired
    ModelMapper modelMapper;
    @Transactional
    public void addProductToCart(UUID cartId, Long productId){
        Optional<Cart> cartWithProducts = repository.getCartWithProducts(cartId);
        Cart cart = cartWithProducts.get();

        CartProduct cartItem = cart.getItemByProductId(productId);
        if (cartItem != null) {
            cartItem.incrementQuantity();
            cart.recalculate();
            return;
        }
        ProductDto product = productsClient.showProduct(productId);
        cart.add(new CartProduct(product));
    }

    @Transactional
    public void clearCart(UUID cartId){
        Cart cart = repository.findById(cartId)
                .orElseThrow(() -> new NoEntityException("Cart with id " + cartId + " not found"));
        cart.clear();

    }

    public Cart save(Cart cart) {
        return repository.save(cart);
    }

    public CartDto findById(UUID id) {
        Optional<Cart> cartWithProducts = repository.getCartWithProducts(id);
        CartDto cartDto = mapCartToCartDto( cartWithProducts
                .orElseThrow(() -> new NoEntityException("Cart with id " + id + " not found")));
        return cartDto;
    }

    @Transactional
    public UUID getCartForUser(Long userId, UUID cartId){
        if (userId != null && cartId != null) {
            CartDto cartDto = findById(cartId);
            Cart cart = modelMapper.map(cartDto, Cart.class);
            Optional<Cart> oldCart = findByUserId(userId);
            if (oldCart.isPresent()) {
                cart.merge(oldCart.get());
                repository.delete(oldCart.get());
            }
            cart.setUserId(userId);
        }
        if (userId == null) {
            Cart cart = save(new Cart());
            return cart.getId();
        }
        Optional<Cart> cart = findByUserId(userId);
        if (cart.isPresent()) {
            return cart.get().getId();
        }
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        save(newCart);
        return newCart.getId();
    }

    private Optional<Cart> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    private CartDto mapCartToCartDto(Cart cart){
        CartDto dto =modelMapper.map(cart, CartDto.class);
        dto.setItems(cart.getProducts().stream()
                .map(cartProduct -> modelMapper.map(cartProduct, CartProductDto.class))
                .collect(Collectors.toList()));
        return dto;
    }


}
