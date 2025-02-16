package com.alejjandrodev.ArcaWareHouse.controllers;

import com.alejjandrodev.ArcaWareHouse.dtos.CreateWarehouseDto;
import com.alejjandrodev.ArcaWareHouse.dtos.ProductQuantityDTO;
import com.alejjandrodev.ArcaWareHouse.dtos.WarehouseDeliveryTimeDTO;
import com.alejjandrodev.ArcaWareHouse.dtos.WarehouseUpdateDTO;
import com.alejjandrodev.ArcaWareHouse.entities.Warehouse;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseDeliveryTime;
import com.alejjandrodev.ArcaWareHouse.errors.WarehouseNotFoundException;
import com.alejjandrodev.ArcaWareHouse.services.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.findAll();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        return warehouseService.findById(id)
                .map(warehouse -> new ResponseEntity<>(warehouse, HttpStatus.OK))
                .orElseThrow(() -> new WarehouseNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody CreateWarehouseDto warehouseDTO) {
        Warehouse createdWarehouse = warehouseService.save(warehouseDTO);
        return new ResponseEntity<>(createdWarehouse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @Valid @RequestBody WarehouseUpdateDTO warehouseDTO) {
        try {
            Warehouse updatedWarehouse = warehouseService.update(id, warehouseDTO);
            return new ResponseEntity<>(updatedWarehouse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        try {
            warehouseService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
