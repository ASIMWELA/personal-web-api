package com.personal.website.payload;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse
{
    private Boolean success;
    private HttpStatus status;
    private int statusCode;
    private String message;

    public ApiResponse(Boolean success,HttpStatus status,int statusCode,String message)
    {
        this.success = success;
        this.status = status;
        this.statusCode=statusCode;
        this.message = message;
    }
}
