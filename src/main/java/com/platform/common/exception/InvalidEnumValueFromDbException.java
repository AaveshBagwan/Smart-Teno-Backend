package com.platform.common.exception;

public class InvalidEnumValueFromDbException extends RuntimeException {

    public InvalidEnumValueFromDbException(String message) {
        super(message);
    }

    public InvalidEnumValueFromDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnumValueFromDbException(Throwable cause) {
        super(cause);
    }

}
