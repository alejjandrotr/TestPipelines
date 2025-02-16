package com.alejjandrodev.ArcaWareHouse.controllers;


import com.alejjandrodev.ArcaWareHouse.dtos.WarehouseDeliveryTimeDTO;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseDeliveryTime;
import com.alejjandrodev.ArcaWareHouse.services.WarehouseDeliveryTimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class DeliveryTimeWarehouseController {

    @Autowired
    WarehouseDeliveryTimeService warehouseDeliveryTimeService;

    @PostMapping("/{warehouseOriginId}/{warehouseDestinationId}")
    public ResponseEntity<WarehouseDeliveryTime> createDeliveryTime(
            @PathVariable Long warehouseOriginId,
            @PathVariable Long warehouseDestinationId,
            @Valid @RequestBody WarehouseDeliveryTimeDTO deliveryTimeDTO) {
        WarehouseDeliveryTime createdDeliveryTime = warehouseDeliveryTimeService.createWarehouseDeliveryTime(
                warehouseOriginId, warehouseDestinationId, deliveryTimeDTO);
        return new ResponseEntity<>(createdDeliveryTime, HttpStatus.CREATED);
    }
}
