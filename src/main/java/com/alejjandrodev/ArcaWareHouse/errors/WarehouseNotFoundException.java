package com.alejjandrodev.ArcaWareHouse.errors;

import org.springframework.http.HttpStatus;

public class WarehouseNotFoundException extends CommonException {
    public WarehouseNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "No se puede encotrar el warehouse con id: " + id);
    }
}
