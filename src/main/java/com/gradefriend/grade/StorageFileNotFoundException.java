package com.gradefriend.grade;

/**
 * @author batman
 * @since 29/9/17
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}