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
@Table(name = "cart_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @Column(name = "name")
    String name;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "quantity")
    Long quantity;

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

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", cart=" + cart.getId() +
                ", productTitle='" + name + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", costPerProduct=" + costPerProduct +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public CartProduct(ProductDto product) {
        this.name = product.getName();
        this.productId = product.getId();
        this.quantity = 1L;
        this.costPerProduct = product.getCost();
        this.price = this.costPerProduct;
    }

    public void incrementQuantity() {
        quantity++;
        price = quantity * costPerProduct;

    }

    public void incrementQuantity(Long amount) {
        quantity += amount;
        price = quantity * costPerProduct;
    }

    public void decrementQuantity() {
        quantity--;
        price = quantity * costPerProduct;
    }

}
