package com.humaorie.wr.api;

import org.junit.Test;

public class GzippedJsonOverHttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new GzippedJsonOverHttpRepository(null);
    }

    @Test
    public void canCreateWithUrlFactory() {
        final UrlFactory urlFactory = new UrlFactory(new UrlFactoryTest.StubApiKeyProvider());
        new GzippedJsonOverHttpRepository(urlFactory);
    }
}
