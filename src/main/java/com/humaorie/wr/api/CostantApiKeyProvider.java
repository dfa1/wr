package com.humaorie.wr.api;

public class CostantApiKeyProvider implements ApiKeyProvider {

    private final String key;

    public CostantApiKeyProvider(String key) {
        this.key = key;
    }

    @Override
    public String provideKey() {
        return key;
    }
}
