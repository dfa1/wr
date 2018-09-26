package com.humaorie.wr.api;

public class ConstantApiKeyProvider implements ApiKeyProvider {

    private final String key;

    public ConstantApiKeyProvider(String key) {
        this.key = key;
    }

    @Override
    public String provideKey() {
        return this.key;
    }
}
