package com.alejjandrodev.ArcaWareHouse.services;

import com.alejjandrodev.ArcaWareHouse.dtos.WarehouseDeliveryTimeDTO;
import com.alejjandrodev.ArcaWareHouse.entities.Warehouse;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseDeliveryTime;
import com.alejjandrodev.ArcaWareHouse.errors.WarehouseNotFoundException;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseDeliveryTimeRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseRepository;
import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import com.alejjandrodev.ArcaWareHouse.utils.loggerWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseDeliveryTimeService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseDeliveryTimeRepository deliveryTimeRepository;

    @Autowired
    private ILoggerWriter logger; // Inject the logger

    @Transactional
    public WarehouseDeliveryTime createWarehouseDeliveryTime(
            Long warehouseOriginId, Long warehouseDestinationId, WarehouseDeliveryTimeDTO deliveryTimeDTO) {

        logger.info("Creating warehouse delivery time", deliveryTimeDTO);
        Warehouse warehouseOrigin = warehouseRepository.findById(warehouseOriginId)
                .orElseThrow(() -> {
                    WarehouseNotFoundException exception = new WarehouseNotFoundException(warehouseOriginId);
                    logger.error("Warehouse origin not found", exception);
                    return exception;
                });

        Warehouse warehouseDestination = warehouseRepository.findById(warehouseDestinationId)
                .orElseThrow(() -> {
                    WarehouseNotFoundException exception = new WarehouseNotFoundException(warehouseDestinationId);
                    logger.error("Warehouse destination not found", exception.toErrorResponseDto());
                    return exception;
                });

        WarehouseDeliveryTime deliveryTime = new WarehouseDeliveryTime();
        deliveryTime.setWarehouseOrigin(warehouseOrigin);
        deliveryTime.setWarehouseDestination(warehouseDestination);
        deliveryTime.setDeliveryTimeHours(deliveryTimeDTO.getDeliveryTimeHours());

        WarehouseDeliveryTime savedDeliveryTime = deliveryTimeRepository.save(deliveryTime);
        logger.info("Warehouse delivery time created successfully", savedDeliveryTime);
        return savedDeliveryTime;
    }
}
