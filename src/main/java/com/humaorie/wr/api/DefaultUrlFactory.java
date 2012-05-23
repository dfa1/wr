package com.humaorie.wr.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class DefaultUrlFactory implements UrlFactory {

    public static final String DEFAULT_API_URL = "http://api.wordreference.com";
    public static final String DEFAULT_API_VERSION = "0.8";
    private final ApiKeyProvider apiKeyProvider;
    private String apiVersion = DEFAULT_API_VERSION;

    public DefaultUrlFactory(ApiKeyProvider apiKeyProvider) {
        Preconditions.require(apiKeyProvider != null, "cannot use null as ApiKeyProvider");
        this.apiKeyProvider = apiKeyProvider;
    }

    @Override
    public URL createUrl(String dict, String word) throws IOException {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(word != null, "word cannot be null");
        
        if (dict.length() != 4) {
            throw new WordReferenceException("dict must be of length 4");
        }
        
        final String url = String.format("%s/%s/%s/json/%s/%s", 
					  DEFAULT_API_URL, 
					  apiVersion, 
					  encode(apiKeyProvider.provideKey()), 
					  encode(dict), 
					  encode(word));
        return new URL(url);
    }
    
    private String encode(String plain) throws IOException {
        return URLEncoder.encode(plain, "utf8");
    }

    public void setApiVersion(String apiVersion) {
        Preconditions.require(apiVersion != null, "api version cannot be null");
        this.apiVersion = apiVersion;
    }
}
