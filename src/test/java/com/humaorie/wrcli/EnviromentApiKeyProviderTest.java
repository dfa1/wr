package com.humaorie.wrcli;

import org.junit.Test;

public class EnviromentApiKeyProviderTest {

    @Test(expected = IllegalStateException.class)
    public void withoutKeyThrows() {
        EnviromentApiKeyProvider provider = new EnviromentApiKeyProvider();
        provider.provideKey();
    }
}
