package com.platform.common.exception;

public class InvalidEnumValueFromJsonException extends RuntimeException {

    public InvalidEnumValueFromJsonException(String message) {
        super(message);
    }

    public InvalidEnumValueFromJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnumValueFromJsonException(Throwable cause) {
        super(cause);
    }

}
