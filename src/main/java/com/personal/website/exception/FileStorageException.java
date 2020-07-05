package com.personal.website.exception;

public class FileStorageException extends RuntimeException
{
    public FileStorageException(String s)
    {
    }

    public FileStorageException() {
        super();
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }
}
