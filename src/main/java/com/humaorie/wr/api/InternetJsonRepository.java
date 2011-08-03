package com.humaorie.wr.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class InternetJsonRepository implements Repository {

    private String baseURL = "http://api.wordreference.com";
    private String apiVersion = "0.8";
    private ApiKeyProvider apiKeyProvider = new CostantApiKeyProvider("2");

    @Override
    public Reader lookup(String dict, String term) {
        if (dict == null || dict.equals("")) {
            throw new IllegalArgumentException("dict cannot be null or empty");
        }

        if (term == null || term.equals("")) {
            throw new IllegalArgumentException("term cannot be null or empty");
        }

        String path = String.format("%s/%s/%s/json/%s/%s", baseURL, apiVersion, apiKeyProvider.provideKey(), dict, term);
        try {
            URL url = new URL(path);
            return new InputStreamReader(url.openStream());
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setApiKeyProvider(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
