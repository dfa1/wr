package com.humaorie.wr.api;

public class RedirectException extends RuntimeException {
    
    private String redirectUrl;

    public RedirectException(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
