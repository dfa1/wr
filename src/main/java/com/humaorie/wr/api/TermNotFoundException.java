package com.humaorie.wr.api;

public class TermNotFoundException extends RuntimeException {
    
    public TermNotFoundException(String note) {
        super(note);
    }
}
