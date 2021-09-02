package ru.tronin.msorders.services;


import com.sun.xml.bind.v2.TODO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tronin.msorders.models.Cart;
import ru.tronin.msorders.models.Order;
import ru.tronin.msorders.repositories.OrderRepository;
import ru.tronin.routinglib.controllers.ProductsClient;
import ru.tronin.routinglib.dtos.CartDto;
import ru.tronin.routinglib.dtos.OrderDto;
import ru.tronin.routinglib.dtos.UsersAddressDto;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    ProductsClient productsClient;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    public OrderDto createOrderFromUserCart(Long userId, UUID cartUuid, UsersAddressDto address){
        CartDto cartDto = cartService.findById(cartUuid);
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Order order = new Order();
//        TODO

        return null;

    }
}
