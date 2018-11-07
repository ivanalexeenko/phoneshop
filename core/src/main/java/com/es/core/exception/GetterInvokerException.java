package com.es.core.exception;

public class GetterInvokerException extends Exception {
    public GetterInvokerException(String s) {
        super(s);
    }
    public GetterInvokerException(Exception e) {
        super(e);
    }
    public GetterInvokerException(String s,Exception e) {
        super(s,e);
    }
}
