package com.alejjandrodev.ArcaWareHouse.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDto {
    private Object errors;
    private String message;
    private Integer statusCode;
 }
