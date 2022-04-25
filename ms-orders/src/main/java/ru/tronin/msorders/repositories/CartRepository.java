package ru.tronin.msorders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tronin.msorders.models.Cart;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("select c from Cart c where c.userId = ?1")
    Optional<Cart> findByUserId(Long id);

    @Query("select c from Cart c left join c.products where c.id =:id")
    Optional<Cart> getCartWithProducts(@Param("id") UUID uuid);

}
