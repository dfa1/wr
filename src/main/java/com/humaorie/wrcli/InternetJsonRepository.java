package com.humaorie.wrcli;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class InternetJsonRepository implements Repository {

    private String baseURL = "http://api.wordreference.com";
    private String apiVersion = "0.8";
    private ApiKeyProvider apiKeyProvider = new CostantApiKeyProvider("2");

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setApiKeyProvider(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public Reader lookup(String dict, String term) {
        String path = String.format("%s/%s/%s/json/%s/%s", baseURL, apiVersion, apiKeyProvider.provideKey(), dict, term);
        try {
            URL url = new URL(path);
            return new InputStreamReader(url.openStream());
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
