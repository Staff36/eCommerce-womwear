package ru.tronin.msorders.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @ManyToMany
    @JoinTable(name = "orders__ordered_products",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "ordered_products_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<OrderedProduct> orderedProducts;

    @Column(name = "total_sum")
    Double totalSum;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public Order(Cart cart, Long userId, String address) {
        this.orderedProducts = cart.getProducts().stream().map(cartProduct -> {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setOrder(this);
            orderedProduct.setOrderedProductPrice(cartProduct.getCostPerProduct());
            orderedProduct.setProductId(cartProduct.getProductId());
            orderedProduct.setQuantity(cartProduct.getQuantity());
            return orderedProduct;
        }).collect(Collectors.toList());
        this.userId = userId;
        this.address = address;
        this.totalSum = cart.getPrice();
    }

}
