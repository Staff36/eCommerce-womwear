package ru.tronin.mswarehouse.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "products_stock")
public class ProductsStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long product;
    Long quantity;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
