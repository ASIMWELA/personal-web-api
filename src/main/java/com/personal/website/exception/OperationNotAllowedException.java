package com.personal.website.exception;

public class OperationNotAllowedException extends RuntimeException
{
    public OperationNotAllowedException()
    {
        super();
    }

    public OperationNotAllowedException(String message)
    {
        super(message);
    }

    public OperationNotAllowedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OperationNotAllowedException(Throwable cause)
    {
        super(cause);
    }

    protected OperationNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
