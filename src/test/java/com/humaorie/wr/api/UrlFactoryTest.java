package com.humaorie.wr.api;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import org.junit.Assert;

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

    @Test
    public void acceptNonEmptyDictAndWord() throws MalformedURLException {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/enit/dog");
        Assert.assertEquals(expectedUrl, createdUrl);
    }

    public static class StubApiKeyProvider implements ApiKeyProvider {

        @Override
        public String provideKey() {
            return "key";
        }
    }
}
