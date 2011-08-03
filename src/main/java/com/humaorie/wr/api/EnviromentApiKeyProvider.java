package com.humaorie.wr.api;

public class EnviromentApiKeyProvider implements ApiKeyProvider {

    private final String KEY = "WR_API_KEY";

    @Override
    public String provideKey() {
        String key = System.getenv(KEY);

        if (key == null) {
            throw new InvalidApiKeyException("please set the enviroment variable %s\nSee http://www.wordreference.com/docs/APIregistration.aspx", KEY);
        }
        
        return key;
    }
}
