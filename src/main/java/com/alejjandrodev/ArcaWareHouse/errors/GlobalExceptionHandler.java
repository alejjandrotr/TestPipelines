package com.alejjandrodev.ArcaWareHouse.errors;

import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alejjandrodev.ArcaWareHouse.utils.loggerWriter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ILoggerWriter loggerWriter;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        errorResponseDto.setMessage("Error alvalidar los datos");
        errorResponseDto.setStatusCode(400);
        errorResponseDto.setErrors(errors);
        return ResponseEntity.badRequest().body(errors); // Personaliza la respuesta aquí
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(CommonException e) {
        loggerWriter.error("Paso un error" , e.toErrorResponseDto());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toErrorResponseDto());
    }

}
