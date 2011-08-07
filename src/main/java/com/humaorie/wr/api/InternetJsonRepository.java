package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;

public class InternetJsonRepository implements Repository {

    private String baseURL = "http://api.wordreference.com";
    private String apiVersion = "0.8";
    private ApiKeyProvider apiKeyProvider = new CostantApiKeyProvider("2");

    @Override
    public Reader lookup(String dict, String word) {
        if (dict == null || dict.equals("")) {
            throw new IllegalArgumentException("dict cannot be null or empty");
        }

        if (word == null || word.equals("")) {
            throw new IllegalArgumentException("word cannot be null or empty");
        }

        try {
            final String path = String.format("%s/%s/%s/json/%s/%s", baseURL, apiVersion, apiKeyProvider.provideKey(), dict, word);
            final URL url = new URL(path);
            return new InputStreamReader(url.openStream());
        } catch (FileNotFoundException ex) {
            throw new NotFoundException("dictionary '%s' not found", dict);
        } catch (UnknownHostException ex) {
            throw new NotFoundException("cannot open http://api.wordreference.com"); 
        } catch (IOException ex) {
            throw new RuntimeException("I/O error", ex); 
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
