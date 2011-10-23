package com.humaorie.wr.api;

import java.io.IOException;
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
        Preconditions.require(!dict.isEmpty(), "dict cannot be empty");
        Preconditions.require(word != null, "word cannot be null");
        Preconditions.require(!word.isEmpty(), "word cannot be empty");
	final String urlEncodedApiKey = URLEncoder.encode(apiKeyProvider.provideKey(), "utf8");
	final String urlEncodedDict = URLEncoder.encode(dict, "utf8");
	final String urlEncodedWord = URLEncoder.encode(word, "utf8");
        final String url = String.format("%s/%s/%s/json/%s/%s", 
					  API_URL, 
					  API_VERSION, 
					  urlEncodedApiKey, 
					  urlEncodedDict, 
					  urlEncodedWord);
        return new URL(url);
    }
}
