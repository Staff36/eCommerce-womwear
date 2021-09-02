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

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @JoinColumn(name = "product_id")
    Long productId;

    Long quantity;

    @Column(name = "ordered_product_price")
    Double orderedProductPrice;

    @Column(name = "total_price")
    Double totalPrice;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public OrderedProduct(CartProduct cartItem) {
        this.productId = cartItem.getProductId();
        this.quantity = cartItem.getQuantity();
        this.orderedProductPrice = cartItem.getCostPerProduct();
        this.totalPrice = cartItem.getPrice();
    }

}
