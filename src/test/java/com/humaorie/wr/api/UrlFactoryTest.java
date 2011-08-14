package com.humaorie.wr.api;

import java.net.MalformedURLException;
import org.junit.Ignore;
import org.junit.Test;

public class UrlFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNull() throws MalformedURLException {
        new UrlFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullDict() throws MalformedURLException {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl(null, "dog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyDict() throws MalformedURLException {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("", "dog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullWord() throws MalformedURLException {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("enit", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyWord() throws MalformedURLException {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("enit", "");
    }

    public static class StubApiKeyProvider implements ApiKeyProvider {

        @Override
        public String provideKey() {
            return "key";
        }
    }
}
