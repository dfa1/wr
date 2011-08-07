package com.humaorie.wr.api;

import org.junit.Ignore;
import org.junit.Test;

public class InternetJsonRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateInternetJsonRepositoryWithNullApiKeyProvider() {
        new InternetJsonRepository(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullDict() {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(apiKeyProvider);
        repository.lookup(null, "word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyDict() {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(apiKeyProvider);
        repository.lookup("", "word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullWord() {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(apiKeyProvider);
        repository.lookup("enit", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyWord() {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(apiKeyProvider);
        repository.lookup("enit", "");
    }

    @Ignore("how to simulate this?")
    @Test(expected = NotFoundException.class)
    public void unknownDictLeadsToException() {
        final StubApiKeyProvider apiKeyProvider = new StubApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(apiKeyProvider);
        repository.lookup("invalid_dict", "word");
    }

    public static class StubApiKeyProvider implements ApiKeyProvider {

        @Override
        public String provideKey() {
            return "stub";
        }
    }
}
