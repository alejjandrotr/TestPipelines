package com.alejjandrodev.ArcaWareHouse.dtos;

import com.alejjandrodev.ArcaWareHouse.enums.WarehouseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseUpdateDTO {
    private Long cityId;
    private WarehouseType warehouseType;
    private String warehouseName;
}
