package com.humaorie.wr.api;

import org.junit.Test;

public class ManaulIntegrationTesting {

    private final int REPEAT = 30;

    @Test
    public void lookupRepeatedlyWithoutGzip() {
        final ConstantApiKeyProvider costantApiKeyProvider = new ConstantApiKeyProvider("2");
        final UrlFactory urlFactory = new UrlFactory(costantApiKeyProvider);
        final Repository repository = new JsonOverHttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);

        for (int i = 0; i < REPEAT; i++) {
            wordReference.lookup("enit", "run");
        }
    }

    @Test
    public void lookipRepeatedlyWithGzip() {
        final ConstantApiKeyProvider costantApiKeyProvider = new ConstantApiKeyProvider("2");
        final UrlFactory urlFactory = new UrlFactory(costantApiKeyProvider);
        final Repository repository = new GzippedJsonOverHttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);

        for (int i = 0; i < REPEAT; i++) {
            wordReference.lookup("enit", "run");
        }
    }
}
