package com.alejjandrodev.ArcaWareHouse.errors;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

public class CommonException extends HttpStatusCodeException  {
    protected CommonException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);

    }

    public ErrorResponseDto toErrorResponseDto(){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(this.getMessage());
        errorResponseDto.setStatusCode(this.getStatusCode().value());
        return errorResponseDto;
    }
}
