package com.alejjandrodev.ArcaWareHouse.controllers;

import com.alejjandrodev.ArcaWareHouse.dtos.ProductQuantityDTO;
import com.alejjandrodev.ArcaWareHouse.services.ProductWarehouseInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
public class ProductWarehouseInventoryController {

    @Autowired
    ProductWarehouseInventoryService productWarehouseInventoryService;

    @GetMapping("/{warehouseId}/products/{productCode}/quantity")
    public ResponseEntity<ProductQuantityDTO> getProductQuantity(
            @PathVariable Long warehouseId,
            @PathVariable String productCode) {
        try {
            ProductQuantityDTO quantityDTO = productWarehouseInventoryService.getProductQuantity(productCode, warehouseId);
            return new ResponseEntity<>(quantityDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
