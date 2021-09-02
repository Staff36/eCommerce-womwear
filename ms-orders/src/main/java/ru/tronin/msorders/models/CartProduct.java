package ru.tronin.msorders.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.tronin.routinglib.dtos.ProductDto;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "price_per_product")
    Double costPerProduct;

    @Column(name = "price")
    Double price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CartProduct(ProductDto product) {
        this.productId = product.getId();
        this.quantity = 1;
        this.costPerProduct = product.getCost();
        this.price = this.costPerProduct;
    }

    public void incrementQuantity() {
        quantity++;
        price = quantity * costPerProduct;

    }

    public void incrementQuantity(int amount) {
        quantity += amount;
        price = quantity * costPerProduct;
    }

    public void decrementQuantity() {
        quantity--;
        price = quantity * costPerProduct;
    }

}
