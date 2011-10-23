package com.humaorie.wr.api;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlFactory {

    public static final String API_URL = "http://api.wordreference.com";
    public static final String API_VERSION = "0.8";
    private final ApiKeyProvider apiKeyProvider;

    public UrlFactory(ApiKeyProvider apiKeyProvider) {
        Preconditions.require(apiKeyProvider != null, "cannot use null as ApiKeyProvider");
        this.apiKeyProvider = apiKeyProvider;
    }

    public URL createUrl(String dict, String word) throws MalformedURLException {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(!dict.isEmpty(), "dict cannot be empty");
        Preconditions.require(word != null, "word cannot be null");
        Preconditions.require(!word.isEmpty(), "word cannot be empty");
        final String path = String.format("%s/%s/%s/json/%s/%s", API_URL, API_VERSION, apiKeyProvider.provideKey(), dict, word);
        return new URL(path);
    }
}
