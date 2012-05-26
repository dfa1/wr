package com.humaorie.wr.api;

import org.junit.Test;

public class Benchmarks {

    private final String TESTING_API_KEY = "2f6ce"; // this is a real key, please don't abuse

    @Test
    public void lookupRepeatedly() {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider(TESTING_API_KEY);
        final DefaultUrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final Repository repository = new HttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final WordReference wordReference = new DefaultWordReference(repository, parser);
        final long elapsed = benchmarkRepeatedLookup(wordReference);
        System.out.printf("with HTTP/GZIP   : %sms%n", elapsed);
    }

    private long benchmarkRepeatedLookup(WordReference sut) {
        final int repeat = 10;
        final long start = System.currentTimeMillis();
        for (int i = 0; i < repeat; i++) {
            sut.lookup("enit", "word");
        }
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;
        return elapsed;
    }
}
