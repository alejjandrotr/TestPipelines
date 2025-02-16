package com.alejjandrodev.ArcaWareHouse.dtos;

import com.alejjandrodev.ArcaWareHouse.enums.WarehouseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWarehouseDto {
    private Long id;

    @NotNull(message = "El ID de la ciudad no puede ser nulo.")
    private Long cityId;

    @NotNull(message = "El tipo de almacén no puede ser nulo.")
    private WarehouseType warehouseType;

    @NotBlank(message = "El nombre del almacén no puede estar en blanco.")
    private String warehouseName;
}
