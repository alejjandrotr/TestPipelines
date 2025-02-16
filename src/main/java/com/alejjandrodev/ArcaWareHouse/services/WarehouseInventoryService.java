package com.alejjandrodev.ArcaWareHouse.services;

import com.alejjandrodev.ArcaWareHouse.dtos.DispatchOrderDTO;
import com.alejjandrodev.ArcaWareHouse.dtos.DispatchProductDTO;
import com.alejjandrodev.ArcaWareHouse.entities.DispatchOrder;
import com.alejjandrodev.ArcaWareHouse.entities.DispatchOrderDetail;
import com.alejjandrodev.ArcaWareHouse.entities.Warehouse;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseInventory;
import com.alejjandrodev.ArcaWareHouse.errors.InsufficientStockException;
import com.alejjandrodev.ArcaWareHouse.errors.ProductInWarehoueNotFoundException;
import com.alejjandrodev.ArcaWareHouse.errors.WarehouseNotFoundException;
import com.alejjandrodev.ArcaWareHouse.repositories.DispatchOrderRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.DispatchOrderDetailRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.ProductRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseInventoryRepository;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseRepository;
import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import com.alejjandrodev.ArcaWareHouse.utils.loggerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseInventoryService {

    @Autowired
    private WarehouseInventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DispatchOrderRepository dispatchOrderRepository;

    @Autowired
    private DispatchOrderDetailRepository dispatchOrderDetailRepository;

    @Autowired  // Inject the logger
    private ILoggerWriter logger;

    @Transactional
    public void dispatchProducts(DispatchOrderDTO dispatchOrderDTO) {
        Long warehouseId = dispatchOrderDTO.getWarehouseId();
        List<DispatchProductDTO> productsToDispatch = dispatchOrderDTO.getProducts();

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));

        DispatchOrder dispatchOrder = new DispatchOrder();
        dispatchOrder.setWarehouse(warehouse);
        dispatchOrder.setDetails(new ArrayList<>());

        for (DispatchProductDTO productDTO : productsToDispatch) {
            String productCode = productDTO.getProductCode();
            int quantityToDispatch = productDTO.getQuantity();

            WarehouseInventory inventory = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id(productCode, warehouseId)
                    .orElseThrow(() -> new ProductInWarehoueNotFoundException(productCode, warehouseId));

            int currentQuantity = inventory.getQuantity();
            if (currentQuantity < quantityToDispatch) {
                throw new InsufficientStockException(productCode, warehouseId, quantityToDispatch, currentQuantity);
            }

            inventory.setQuantity(currentQuantity - quantityToDispatch);
            inventoryRepository.save(inventory);

            DispatchOrderDetail detail = new DispatchOrderDetail();
            detail.setDispatchOrder(dispatchOrder);
            detail.setProduct(inventory.getProduct());
            detail.setQuantity(quantityToDispatch);
            dispatchOrder.getDetails().add(detail);

            // Log successful dispatch of the product
            logger.info("Dispatched product", detail);
        }

        dispatchOrderRepository.save(dispatchOrder);

        // Log successful dispatch order
        logger.info("Dispatch order created", dispatchOrder);


    }
}
