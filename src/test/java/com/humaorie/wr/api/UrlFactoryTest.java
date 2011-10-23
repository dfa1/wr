package com.humaorie.wr.api;

import java.io.IOException;
import java.net.URL;
import org.junit.Test;
import org.junit.Assert;

public class UrlFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNull() {
        new UrlFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullDict() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl(null, "dog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyDict() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("", "dog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullWord() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("enit", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyWord() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        urlFactory.createUrl("enit", "");
    }

    @Test
    public void acceptNonEmptyDictAndWord() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/enit/dog");
        Assert.assertEquals(expectedUrl, createdUrl);
    }

    @Test
    public void dictIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("%", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/%25/dog");
        Assert.assertEquals(expectedUrl, createdUrl);
    }
    
    @Test
    public void wordIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "%");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/enit/%25");
        Assert.assertEquals(expectedUrl, createdUrl);
    }
    
    @Test
    public void apiKeyIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("%");
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/%25/json/enit/dog");
        Assert.assertEquals(expectedUrl, createdUrl);
    }
}
