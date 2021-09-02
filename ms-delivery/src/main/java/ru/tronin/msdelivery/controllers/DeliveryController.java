package ru.tronin.msdelivery.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tronin.msdelivery.models.services.DeliveryService;
import ru.tronin.routinglib.dtos.DeliveryDto;

import java.util.List;

@RestController
@RequestMapping("api/v1/deliveries")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/{id}")
    public DeliveryDto getDeliveryById(@PathVariable Long id){
        return deliveryService.getDeliveryById(id);
    }
    @GetMapping("/ids")
    public List<DeliveryDto> getDeliveriesByOrderIdsList(List<Long> ids){
        return deliveryService.getDeliveriesByOrderIdsList(ids);
    }
}
