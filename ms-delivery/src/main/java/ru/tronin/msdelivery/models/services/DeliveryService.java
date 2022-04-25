package ru.tronin.msdelivery.models.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.exceptions.NoEntityException;
import ru.tronin.msdelivery.models.etntities.Delivery;
import ru.tronin.msdelivery.repsotories.DeliveryRepository;
import ru.tronin.routinglib.dtos.DeliveryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DeliveryRepository deliveryRepository;

    private DeliveryDto mapDeliveryToDto(Delivery delivery){
        return modelMapper.map(delivery, DeliveryDto.class);
    }

    private Delivery mapDtoToDelivery(DeliveryDto deliveryDto){
        return modelMapper.map(deliveryDto, Delivery.class);
    }

    public DeliveryDto getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Delivery with id " + id + " not found"));
        return mapDeliveryToDto(delivery);
    }

    public DeliveryDto getDeliveryByOrderId(Long id){
        Delivery delivery = deliveryRepository.findDeliveryByOrder(id).orElseThrow(() -> new NoEntityException("Delivery for Order witn ID " + id + " not found"));
        return mapDeliveryToDto(delivery);
    }


    public List<DeliveryDto> getDeliveriesByOrderIdsList(List<Long> ids) {
        return deliveryRepository.findDeliveriesByOrderIn(ids)
                .stream()
                .map(this::mapDeliveryToDto)
                .collect(Collectors.toList());
    }

    public void saveDelivery(DeliveryDto deliveryDto){
        Delivery delivery = mapDtoToDelivery(deliveryDto);
        if (delivery.getId() != null && deliveryRepository.existsById(delivery.getId())){
            return;
        }
        deliveryRepository.save(delivery);
    }

    public void updateDelivery(DeliveryDto deliveryDto){
        Delivery delivery = mapDtoToDelivery(deliveryDto);
        if (delivery.getId() == null || !deliveryRepository.existsById(delivery.getId())){
            return;
        }
        deliveryRepository.save(delivery);
    }

    public void deleteDeliveryById(Long id){
        deliveryRepository.deleteById(id);
    }
}
