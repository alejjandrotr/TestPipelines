package com.alejjandrodev.ArcaWareHouse.errors;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class CityNoFoundException extends CommonException {
    public CityNoFoundException(Long cityId) {
        super(HttpStatus.NOT_FOUND, "No se puede encotrar la ciudad con id: " + cityId);
    }
}
