package com.humaorie.wr.api;

public class WordReferenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WordReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordReferenceException(String message) {
        super(message);
    }
}
