package com.humaorie.wr.api;

import org.junit.Test;

public class JsonOverHttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new JsonOverHttpRepository(null);
    }

    @Test
    public void canCreateWithUrlFactory() {
        final UrlFactory urlFactory = new UrlFactory(new ConstantApiKeyProvider(null));
        new JsonOverHttpRepository(urlFactory);
    }
}
