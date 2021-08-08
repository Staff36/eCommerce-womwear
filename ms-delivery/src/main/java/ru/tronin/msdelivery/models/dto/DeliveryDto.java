package ru.tronin.msdelivery.models.dto;

import lombok.Data;
import ru.tronin.msdelivery.models.etntities.Delivery;

@Data
public class DeliveryDto {
    Long order;
    Long address;
    Double coast;

    public DeliveryDto(Delivery delivery) {
        this.order = delivery.getOrder();
        this.address = delivery.getAddress();
        this.coast = delivery.getCoast();
    }
}
