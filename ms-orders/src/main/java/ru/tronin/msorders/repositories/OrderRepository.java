package ru.tronin.msorders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tronin.msorders.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);
}
