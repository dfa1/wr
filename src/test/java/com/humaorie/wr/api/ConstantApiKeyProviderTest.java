package com.humaorie.wr.api;

import org.junit.Assert;
import org.junit.Test;

public class ConstantApiKeyProviderTest {

    @Test
    public void returnsTheApiKey() {
        final ConstantApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        Assert.assertEquals("key", apiKeyProvider.provideKey());
    }
}
