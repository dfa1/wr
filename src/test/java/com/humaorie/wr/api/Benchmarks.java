package com.humaorie.wr.api;

import org.junit.Test;

public class Benchmarks {

    private final String TESTING_API_KEY = "2f6ce"; // this is a real key, please don't abuse

    @Test
    public void lookupRepeatedlyWithoutGzip() {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider(TESTING_API_KEY);
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final Repository repository = new JsonOverHttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final WordReference wordReference = new DefaultWordReference(repository, parser);
        final long elapsed = benchmarkRepeatedLookup(wordReference);
        System.out.printf("without GZIP: %sms%n", elapsed);
    }

    @Test
    public void lookupRepeatedlyWithGzip() {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider(TESTING_API_KEY);
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final Repository repository = new GzippedJsonOverHttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final WordReference wordReference = new DefaultWordReference(repository, parser);
        final long elapsed = benchmarkRepeatedLookup(wordReference);
        System.out.printf("with GZIP   : %sms%n", elapsed);
    }

    private long benchmarkRepeatedLookup(WordReference sut) {
        final int repeat = 10;
        final long start = System.currentTimeMillis();
        for (int i = 0; i < repeat; i++) {
            sut.lookup("enit", "word");
        }
        return System.currentTimeMillis() - start;
    }
}
