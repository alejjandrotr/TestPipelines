package com.alejjandrodev.ArcaWareHouse.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDeliveryTimeDTO {

    @Min(value = 1, message = "El tiempo de entrega debe ser al menos 1 hora")
    private int deliveryTimeHours;
}
