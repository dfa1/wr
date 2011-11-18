package com.humaorie.wr.api;

public class WordReferenceException extends RuntimeException {

    public WordReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordReferenceException(String message) {
        super(message);
    }
}
