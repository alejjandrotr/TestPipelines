package com.alejjandrodev.ArcaWareHouse.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DispatchProductDTO {

    @NotNull(message = "El código del producto no puede ser nulo")
    private String productCode;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int quantity;
}
