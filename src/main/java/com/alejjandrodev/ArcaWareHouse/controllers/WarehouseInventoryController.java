package com.alejjandrodev.ArcaWareHouse.controllers;

import com.alejjandrodev.ArcaWareHouse.dtos.DispatchOrderDTO;
import com.alejjandrodev.ArcaWareHouse.services.WarehouseInventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class WarehouseInventoryController {

    @Autowired
    private WarehouseInventoryService inventoryService;

    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchProducts(@Valid @RequestBody DispatchOrderDTO dispatchOrderDTO) {
        try {
            inventoryService.dispatchProducts(dispatchOrderDTO);
            return new ResponseEntity<>("Orden de despacho creada y productos despachados correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
