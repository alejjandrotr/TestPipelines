package com.alejjandrodev.ArcaWareHouse.errors;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends CommonException {

    private static final String DEFAULT_MESSAGE = "No hay suficiente stock del producto %s en el almacén con ID %d. Cantidad requerida: %d, cantidad disponible: %d";

    public InsufficientStockException(String productCode, Long warehouseId, int requestedQuantity, int availableQuantity) {
        super(HttpStatus.BAD_REQUEST, buildMessage(productCode, warehouseId, requestedQuantity, availableQuantity));
    }

    private static String buildMessage(String productCode, Long warehouseId, int requestedQuantity, int availableQuantity) {
        return String.format(DEFAULT_MESSAGE, productCode, warehouseId, requestedQuantity, availableQuantity);
    }
}
