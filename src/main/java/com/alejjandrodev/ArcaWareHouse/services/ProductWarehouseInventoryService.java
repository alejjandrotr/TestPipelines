package com.alejjandrodev.ArcaWareHouse.services;

import com.alejjandrodev.ArcaWareHouse.dtos.ProductQuantityDTO;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseInventory;
import com.alejjandrodev.ArcaWareHouse.errors.ProductInWarehoueNotFoundException;
import com.alejjandrodev.ArcaWareHouse.errors.WarehouseNotFoundException;
import com.alejjandrodev.ArcaWareHouse.repositories.CityRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseInventoryRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseRepository;
import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import com.alejjandrodev.ArcaWareHouse.utils.loggerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductWarehouseInventoryService {

    @Autowired
    private WarehouseRepository warehouseRepository;


    @Autowired
    private WarehouseInventoryRepository inventoryRepository;

    @Autowired
    private ILoggerWriter logger; // Inject the logger


    public ProductQuantityDTO getProductQuantity(String productCode, Long warehouseId) {
        logger.info("Getting product quantity", String.format("productCode: %s, warehouseId: %d", productCode, warehouseId));
        // Verificar si el almacén existe
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> {
                    WarehouseNotFoundException exception = new WarehouseNotFoundException(warehouseId);
                    logger.error("Warehouse not found", exception);
                    return exception;
                });

        // Buscar el inventario del producto en el almacén
        WarehouseInventory inventory = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id(productCode, warehouseId)
                .orElseThrow(() -> {
                    ProductInWarehoueNotFoundException exception = new ProductInWarehoueNotFoundException(productCode, warehouseId);
                    logger.error("Product not found in warehouse", exception);
                    return exception;
                });

        // Crear y devolver el DTO con la información
        ProductQuantityDTO quantityDTO = new ProductQuantityDTO(productCode, warehouseId, inventory.getQuantity());
        logger.info("Product quantity retrieved successfully", quantityDTO);
        return quantityDTO;
    }
}
