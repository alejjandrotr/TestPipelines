package com.alejjandrodev.ArcaWareHouse.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DispatchOrderDTO {

    @NotNull(message = "El ID del almacén no puede ser nulo")
    private Long warehouseId;

    @NotEmpty(message = "La lista de productos no puede estar vacía")
    private List<@Valid DispatchProductDTO> products;
}
