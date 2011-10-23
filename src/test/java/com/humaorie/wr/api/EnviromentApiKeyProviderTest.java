package com.humaorie.wr.api;

import org.junit.Ignore;
import org.junit.Test;

public class EnviromentApiKeyProviderTest {

    @Test(expected = WordReferenceException.class)
    public void withoutEnvironmentVariableThrows() {
        final EnviromentApiKeyProvider apiKeyProvider = new EnviromentApiKeyProvider();
        apiKeyProvider.provideKey();
    }
    
    @Test
    @Ignore("how to set an environment variable here?")
    public void withEnvironmentVariableReturnsIt() {
        final EnviromentApiKeyProvider apiKeyProvider = new EnviromentApiKeyProvider();
        apiKeyProvider.provideKey();
    }
}
