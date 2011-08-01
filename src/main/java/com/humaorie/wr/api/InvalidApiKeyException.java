package com.humaorie.wr.api;

public class InvalidApiKeyException extends RuntimeException {

    public InvalidApiKeyException(String fmt, Object... args) {
        super(String.format(fmt, args));
    }
}
