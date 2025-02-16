package com.alejjandrodev.ArcaWareHouse.controllers;

import com.alejjandrodev.ArcaWareHouse.dtos.DispatchOrderDTO;
import com.alejjandrodev.ArcaWareHouse.dtos.DispatchProductDTO;
import com.alejjandrodev.ArcaWareHouse.dtos.ProductQuantityDTO;
import com.alejjandrodev.ArcaWareHouse.entities.WarehouseInventory;
import com.alejjandrodev.ArcaWareHouse.repositories.WarehouseInventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WarehouseInventoryRepository inventoryRepository;

    @Test
    void getProductQuantity_Successful() throws Exception {
        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/warehouses/1/products/PROD001/quantity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(
                        "{\"productCode\":\"PROD001\",\"warehouseId\":1,\"quantity\":50}"
                ));
    }

    @Test
    void getProductQuantity_NotFound() throws Exception {
        // Perform the GET request for a non-existent product
        mockMvc.perform(MockMvcRequestBuilders.get("/api/warehouses/1/products/NONEXISTENT/quantity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void dispatchProducts_SuccessfulDispatch() throws Exception {
        // Given
        DispatchOrderDTO dispatchOrderDTO = new DispatchOrderDTO();
        dispatchOrderDTO.setWarehouseId(1L);

        DispatchProductDTO dispatchProduct1 = new DispatchProductDTO();
        dispatchProduct1.setProductCode("PROD001");
        dispatchProduct1.setQuantity(1);

        DispatchProductDTO dispatchProduct2 = new DispatchProductDTO();
        dispatchProduct2.setProductCode("PROD002");
        dispatchProduct2.setQuantity(1);

        dispatchOrderDTO.setProducts(Arrays.asList(dispatchProduct1, dispatchProduct2));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dispatchOrderDTO)))
                .andExpect(status().isCreated());

        // Then
        WarehouseInventory updatedInventory1 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD001", 1L).orElse(null);
        WarehouseInventory updatedInventory2 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD002", 1L).orElse(null);

        assertEquals(49, updatedInventory1 != null ? updatedInventory1.getQuantity() : 0);
        assertEquals(9, updatedInventory2 != null ? updatedInventory2.getQuantity() : 0);
    }

    @Test
    void dispatchProducts_BadRequest() throws Exception {
        // Given
        DispatchOrderDTO dispatchOrderDTO = new DispatchOrderDTO();
        dispatchOrderDTO.setWarehouseId(1L);

        DispatchProductDTO dispatchProduct1 = new DispatchProductDTO();
        dispatchProduct1.setProductCode("PROD001");
        dispatchProduct1.setQuantity(500); // Requesting more than available

        dispatchOrderDTO.setProducts(Arrays.asList(dispatchProduct1));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dispatchOrderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dispatchProducts_ValidationFailed() throws Exception {
        // Given
        DispatchOrderDTO dispatchOrderDTO = new DispatchOrderDTO();
        dispatchOrderDTO.setWarehouseId(null); // Invalid DTO

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dispatchOrderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dispatchProducts_TransactionalRollback() throws Exception {
        // Given
        DispatchOrderDTO dispatchOrderDTO = new DispatchOrderDTO();
        dispatchOrderDTO.setWarehouseId(1L);

        DispatchProductDTO dispatchProduct1 = new DispatchProductDTO();
        dispatchProduct1.setProductCode("PROD001");
        dispatchProduct1.setQuantity(5);

        DispatchProductDTO dispatchProduct2 = new DispatchProductDTO();
        dispatchProduct2.setProductCode("PROD002");
        dispatchProduct2.setQuantity(3);

        DispatchProductDTO dispatchProduct3 = new DispatchProductDTO();
        dispatchProduct3.setProductCode("PROD003");
        dispatchProduct3.setQuantity(100); // Intentionally cause insufficient stock

        dispatchOrderDTO.setProducts(Arrays.asList(dispatchProduct1, dispatchProduct2, dispatchProduct3));

        // Initial quantities
        WarehouseInventory initialInventory1 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD001", 1L).orElse(null);
        WarehouseInventory initialInventory2 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD002", 1L).orElse(null);

        int initialQuantity1 = initialInventory1 != null ? initialInventory1.getQuantity() : 0;
        int initialQuantity2 = initialInventory2 != null ? initialInventory2.getQuantity() : 0;

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory/dispatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dispatchOrderDTO)))
                .andExpect(status().isBadRequest());

        // Then
        WarehouseInventory updatedInventory1 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD001", 1L).orElse(null);
        WarehouseInventory updatedInventory2 = inventoryRepository.findByProduct_ProductCodeAndWarehouse_Id("PROD002", 1L).orElse(null);

        int updatedQuantity1 = updatedInventory1 != null ? updatedInventory1.getQuantity() : 0;
        int updatedQuantity2 = updatedInventory2 != null ? updatedInventory2.getQuantity() : 0;

        assertEquals(initialQuantity1, updatedQuantity1);
        assertEquals(initialQuantity2, updatedQuantity2);
    }
}
