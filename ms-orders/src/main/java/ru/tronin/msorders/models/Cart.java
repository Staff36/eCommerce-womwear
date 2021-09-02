package ru.tronin.msorders.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    UUID id;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    List<CartProduct> products;

    @Column(name = "price")
    Double price;

    @Column(name = "user_id")
    Long userId;



    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public void add(CartProduct cartProduct) {
        for (CartProduct product : this.products) {
            if (product.getProductId().equals(cartProduct.getProductId())) {
                product.incrementQuantity(cartProduct.getQuantity());
                recalculate();
                return;
            }
        }

        this.products.add(cartProduct);
        cartProduct.setCart(this);
        recalculate();
    }

    public void recalculate() {
        price = 0D;
        for (CartProduct cartProduct : products) {
            price += cartProduct.getPrice();
        }
    }

    public void clear() {
        for (CartProduct cartProduct : products) {
            cartProduct.setCart(null);
        }
        products.clear();
        recalculate();
    }

    public CartProduct getItemByProductId(Long productId) {
        for (CartProduct cp : products) {
            if (cp.getProductId().equals(productId)) {
                return cp;
            }
        }
        return null;
    }

    public void merge(Cart another) {
        for (CartProduct cp : another.products) {
            add(cp);
        }
    }

}
