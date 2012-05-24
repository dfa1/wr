package com.humaorie.wr.api;

@Deprecated
public class EnviromentApiKeyProvider implements ApiKeyProvider {

    public static final String KEY = "WR_API_KEY";

    @Override
    public String provideKey() {
        final String key = System.getenv(KEY);

        if (key == null) {
            throw new WordReferenceException(String.format("please set the environment variable %s%nSee http://www.wordreference.com/docs/APIregistration.aspx", KEY));
        }
        
        return key;
    }
}
