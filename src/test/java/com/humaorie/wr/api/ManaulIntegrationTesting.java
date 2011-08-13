package com.humaorie.wr.api;

import org.junit.Test;

public class ManaulIntegrationTesting {

    final int REPEAT = 30;

    @Test
    public void lookupRepeatedlyWithoutGzip() {
        final CostantApiKeyProvider costantApiKeyProvider = new CostantApiKeyProvider("2");
        final UrlFactory urlFactory = new UrlFactory(costantApiKeyProvider);
        final Repository repository = new InternetJsonRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);

        for (int i = 0; i < REPEAT; i++) {
            wordReference.lookup("enit", "run");
        }
    }

    @Test
    public void lookipRepeatedlyWithGzip() {
        final CostantApiKeyProvider costantApiKeyProvider = new CostantApiKeyProvider("2");
        final UrlFactory urlFactory = new UrlFactory(costantApiKeyProvider);
        final Repository repository = new GzipInternetJsonRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);

        for (int i = 0; i < REPEAT; i++) {
            wordReference.lookup("enit", "run");
        }
    }
}
