package com.personal.website.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE  + 99)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{

    private ResponseEntity<Object> buildResponseEntity(ApiException apiException)
    {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex)
    {
        ApiException apiError = new ApiException(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExistException(EntityAlreadyExistException ex)
    {
        ApiException apiError = new ApiException(CONFLICT);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({FileStorageException.class})
    protected ResponseEntity<Object> handleFileStorageException(FileStorageException ex)
    {
        ApiException apiError = new ApiException(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    protected ResponseEntity<Object> handleOperationNotAllowedException(OperationNotAllowedException ex)
    {
        ApiException apiError = new ApiException(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<Object> handleUnAuthorizedException(TokenExpiredException ex)
    {
        ApiException apiError = new ApiException(FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(FailedToSaveProfile.class)
    protected ResponseEntity<Object> failedToSaveProfile(FailedToSaveProfile ex)
    {
        ApiException apiError = new ApiException(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(apiError.getStatus().value());
        return buildResponseEntity(apiError);
    }

}
