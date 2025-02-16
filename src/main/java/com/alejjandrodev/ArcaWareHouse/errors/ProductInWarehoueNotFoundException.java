package com.alejjandrodev.ArcaWareHouse.errors;

import org.springframework.http.HttpStatus;

public class ProductInWarehoueNotFoundException extends CommonException {
    public ProductInWarehoueNotFoundException(String productCode, Long warehouseId) {
        super(HttpStatus.NOT_FOUND, "No se puede encotrar el producto " +  productCode+  " en el warehouse id: " + warehouseId);
    }
}
