package com.humaorie.wr.api;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlFactory {

    private final String baseURL = "http://api.wordreference.com";
    private final String apiVersion = "0.8";
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
        final String path = String.format("%s/%s/%s/json/%s/%s", baseURL, apiVersion, apiKeyProvider.provideKey(), dict, word);
        return new URL(path);
    }
}
