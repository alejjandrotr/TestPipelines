package com.alejjandrodev.ArcaWareHouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDTO {
    private String productCode;
    private Long warehouseId;
    private int quantity;
}
