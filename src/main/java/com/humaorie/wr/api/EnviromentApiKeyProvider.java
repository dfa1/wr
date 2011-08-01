package com.humaorie.wr.api;

public class EnviromentApiKeyProvider implements ApiKeyProvider {

    private final String ENVVAR = "WR_API_KEY";

    @Override
    public String provideKey() {
        String key = System.getenv(ENVVAR);
    
        if (key == null) {
            throw new IllegalStateException(String.format("please set the enviroment variable %s\nSee http://www.wordreference.com/docs/APIregistration.aspx", ENVVAR));
        }

        return key;
    }
}
