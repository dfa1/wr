package com.humaorie.wr.api;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String fmt, Object ... args) {
        super(String.format(fmt, args));
    }
}
