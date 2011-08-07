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
    private final ApiKeyProvider apiKeyProvider;

    public InternetJsonRepository(ApiKeyProvider apiKeyProvider) {
        Preconditions.require(apiKeyProvider != null, "cannot use null as ApiKeyProvider");
        this.apiKeyProvider = apiKeyProvider;
    }
    
    @Override
    public Reader lookup(String dict, String word) {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(!dict.isEmpty(), "dict cannot be empty");
        Preconditions.require(word != null, "word cannot be null");
        Preconditions.require(!word.isEmpty(), "word cannot be empty");

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

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
