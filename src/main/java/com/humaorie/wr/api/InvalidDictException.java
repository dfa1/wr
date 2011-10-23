package com.humaorie.wr.api;

public class InvalidDictException extends RuntimeException {

    public InvalidDictException(String fmt, Object... args) {
        super(String.format(fmt, args));
    }
}
