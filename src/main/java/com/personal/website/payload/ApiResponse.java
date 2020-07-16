package com.personal.website.payload;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse
{
    private int code;
    private HttpStatus status;
    private String message;

    public ApiResponse(HttpStatus status,int code,String message)
    {
        this.status = status;
        this.code=code;
        this.message = message;
    }
}
