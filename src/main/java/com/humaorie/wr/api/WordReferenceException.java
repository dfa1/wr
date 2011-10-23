package com.humaorie.wr.api;

public class WordReferenceException extends RuntimeException {

    public WordReferenceException(String fmt, Object... args) {
        super(String.format(fmt, args));
    }
}
