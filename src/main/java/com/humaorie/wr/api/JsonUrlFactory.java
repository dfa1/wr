package com.humaorie.wr.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class JsonUrlFactory implements UrlFactory {

    public static final String DEFAULT_API_URL = "http://api.wordreference.com";
    public static final String DEFAULT_API_VERSION = "0.8";
    private final ApiKeyProvider apiKeyProvider;

    public JsonUrlFactory(ApiKeyProvider apiKeyProvider) {
        Preconditions.require(apiKeyProvider != null, "cannot use null as ApiKeyProvider");
        this.apiKeyProvider = apiKeyProvider;
    }

    @Override
    public URL createUrl(String dict, String word) throws IOException {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(word != null, "word cannot be null");
        // FIXME: when a dict is not of length 4 the response is an HTML with status 200
        if (dict.length() != 4) {
            throw new WordReferenceException("dict must be of length 4");
        }
        final String apiKey = apiKeyProvider.provideKey();
        final String url = String.format("%s/%s/%s/json/%s/%s", DEFAULT_API_URL, DEFAULT_API_VERSION, encode(apiKey), encode(dict), encode(word));
        return new URL(url);
    }

    private String encode(String plain) throws IOException {
        return URLEncoder.encode(plain, "utf8");
    }
}
