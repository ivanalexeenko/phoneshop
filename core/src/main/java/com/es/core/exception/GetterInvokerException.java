package com.es.core.exception;

public class GetterInvokerException extends Exception {
    public GetterInvokerException(String message) {
        super(message);
    }

    public GetterInvokerException(Exception exception) {
        super(exception);
    }

    public GetterInvokerException(String message, Exception exception) {
        super(message, exception);
    }
}
