package ru.tronin.msorders.services;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msorders.models.Cart;
import ru.tronin.msorders.models.CartProduct;
import ru.tronin.msorders.repositories.CartRepository;
import ru.tronin.routinglib.controllers.ProductsClient;
import ru.tronin.routinglib.dtos.CartDto;
import ru.tronin.routinglib.dtos.ProductDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

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
        Cart cart = repository.findById(cartId).orElseThrow(() -> new NoEntityException("Cart with id " + cartId + " not found"));
        CartDto cartDto = mapCartToCartDto(cart);
        CartProduct cartProduct = cart.getItemByProductId(productId);
        if (cart != null){
            cartProduct.incrementQuantity();
            cart.recalculate();
            return;
        }
        ProductDto productDto = productsClient.showProduct(productId);
        cart.add(new CartProduct(productDto));
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
        return mapCartToCartDto(repository.findById(id)
                .orElseThrow(() -> new NoEntityException("Cart with id " + id + " not found")));
    }

    @Transactional
    public UUID getCartForUser(Long userId, UUID cartId){
        if (userId != null && cartId !=null){
            Cart cart = repository.findById(cartId)
                    .orElseThrow(() -> new NoEntityException("Cart with id " + cartId + " not found"));
            Optional<Cart> oldCart = repository.findByUserId(userId);
            if (oldCart.isPresent()){
                cart.merge(oldCart.get());
                repository.delete(oldCart.get());
            }
            cart.setUserId(userId);
        }
        if (userId == null) {
            Cart cart = save(new Cart());
            return cart.getId();
        }
        Optional<Cart> cart = repository.findByUserId(userId);
        if (cart.isPresent()) {
            return cart.get().getId();
        }
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        save(newCart);
        return newCart.getId();
    }

    private CartDto mapCartToCartDto(Cart cart){
        return modelMapper.map(cart, CartDto.class);
    }


}
