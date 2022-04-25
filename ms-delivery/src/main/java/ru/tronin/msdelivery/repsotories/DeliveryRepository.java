package ru.tronin.msdelivery.repsotories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.tronin.msdelivery.models.etntities.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findDeliveryByOrder(Long order);
    List<Delivery> findDeliveriesByOrderIn(List<Long> ids);
}
