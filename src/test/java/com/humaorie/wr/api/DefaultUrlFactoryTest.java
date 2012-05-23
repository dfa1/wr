package com.humaorie.wr.api;

import java.io.IOException;
import java.net.URL;
import org.junit.Test;
import org.junit.Assert;

public class DefaultUrlFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNull() {
        new DefaultUrlFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullDict() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        urlFactory.createUrl(null, "dog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullWord() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        urlFactory.createUrl("enit", null);
    }

    @Test(expected = WordReferenceException.class)
    public void refuseDictNotOfLengthFour() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        urlFactory.createUrl("12345", "dog");
    }

    @Test
    public void acceptNonNullDictAndWord() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/enit/dog");
        Assert.assertEquals(expectedUrl.toExternalForm(), createdUrl.toExternalForm());
    }

    @Test
    public void dictIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("%%%%", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/%25%25%25%25/dog");
        Assert.assertEquals(expectedUrl.toExternalForm(), createdUrl.toExternalForm());
    }

    @Test
    public void wordIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("key");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "%");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/key/json/enit/%25");
        Assert.assertEquals(expectedUrl.toExternalForm(), createdUrl.toExternalForm());
    }

    @Test
    public void apiKeyIsUrlEncoded() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("%");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.8/%25/json/enit/dog");
        Assert.assertEquals(expectedUrl.toExternalForm(), createdUrl.toExternalForm());
    }

    @Test
    public void apiVersionCanBeOverrided() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("1");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        urlFactory.setApiVersion("0.9");
        final URL createdUrl = urlFactory.createUrl("enit", "dog");
        final URL expectedUrl = new URL("http://api.wordreference.com/0.9/1/json/enit/dog");
        Assert.assertEquals(expectedUrl.toExternalForm(), createdUrl.toExternalForm());
    }

    @Test(expected = IllegalArgumentException.class)
    public void apiVersionCannotBeNull() throws IOException {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider("1");
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        urlFactory.setApiVersion(null);
    }
}
