package com.humaorie.wr.api;

import org.junit.Ignore;
import org.junit.Test;

public class EnviromentApiKeyProviderTest {

    @Test(expected = InvalidApiKeyException.class)
    public void withoutEnvironmentVariableThrows() {
        EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        enviromentApiKeyProvider.provideKey();
    }
    
    @Test
    @Ignore("how to set an environment variable here?")
    public void withEnvironmentVariableReturnsIt() {
        EnviromentApiKeyProvider provider = new EnviromentApiKeyProvider();
        provider.provideKey();
    }
}
