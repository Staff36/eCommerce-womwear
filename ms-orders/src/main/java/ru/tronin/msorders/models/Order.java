package ru.tronin.msorders.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    List<OrderedProduct> orderedProducts;

    @Column(name = "total_sum")
    Double totalSum;

    @Column(name = "title")
    String productTitle;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public Order(Cart cart, Long userId, String address) {
        this.orderedProducts = new ArrayList<>();
        this.userId = userId;
        this.address = address;
        this.totalSum = cart.getPrice();
        for (CartProduct cartProduct : cart.getProducts()) {
            OrderedProduct orderedProduct = new OrderedProduct(cartProduct);
            orderedProduct.setOrder(this);
            this.orderedProducts.add(orderedProduct);
        }
    }

}
