package ru.tronin.msorders.services;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.msorders.models.Cart;
import ru.tronin.msorders.models.Order;
import ru.tronin.msorders.repositories.OrderRepository;
import ru.tronin.routinglib.controllers.ProductsClient;
import ru.tronin.routinglib.dtos.CartDto;
import ru.tronin.routinglib.dtos.OrderDto;
import ru.tronin.routinglib.dtos.ProductDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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

    @Transactional
    public OrderDto createFromUserCart(Long userId, UUID cartUuid, String address) {
        CartDto cartDto = cartService.findById(cartUuid);
        Cart cart = modelMapper.map(cartDto, Cart.class);
        Order order = new Order(cart, userId, address);
        order = orderRepository.save(order);
        return toDto(order);
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id).get();
        List<Long> productIds = new ArrayList<>();
        order.getOrderedProducts().forEach(item -> productIds.add(item.getProductId()));
        List<ProductDto> products = productsClient.getProdictsList(productIds);
        OrderDto orderDto = toDto(order);
        orderDto.setProducts(products);
        return orderDto;
    }


    public List<OrderDto> findAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private OrderDto toDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private Order toEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}
