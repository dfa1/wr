package com.humaorie.wr.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlFactory {

    public static final String API_URL = "http://api.wordreference.com";
    public static final String API_VERSION = "0.8";
    private final ApiKeyProvider apiKeyProvider;

    public UrlFactory(ApiKeyProvider apiKeyProvider) {
        Preconditions.require(apiKeyProvider != null, "cannot use null as ApiKeyProvider");
        this.apiKeyProvider = apiKeyProvider;
    }

    public URL createUrl(String dict, String word) throws IOException {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(word != null, "word cannot be null");
        
        if (dict.length() != 4) {
            throw new WordReferenceException("dict must be of length 4");
        }
        
        final String url = String.format("%s/%s/%s/json/%s/%s", 
					  API_URL, 
					  API_VERSION, 
					  urlEncode(apiKeyProvider.provideKey()), 
					  urlEncode(dict), 
					  urlEncode(word));
        return new URL(url);
    }
    
    private String urlEncode(String plain) throws UnsupportedEncodingException {
        return URLEncoder.encode(plain, "utf8");
    }
}
