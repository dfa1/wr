package com.humaorie.wr.dict;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.ConstantApiKeyProvider;
import com.humaorie.wr.api.HttpRepository;
import com.humaorie.wr.api.JsonUrlFactory;
import com.humaorie.wr.api.Repository;
import org.junit.Test;

public class Benchmarks {

    private final String TESTING_API_KEY = "2f6ce";

    @Test
    public void lookupRepeatedly() {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider(TESTING_API_KEY);
        final JsonUrlFactory urlFactory = new JsonUrlFactory(apiKeyProvider);
        final Repository repository = new HttpRepository(urlFactory);
        final DictParser parser = new JsonDictParser();
        final Dict wordReference = new DefaultDict(repository, parser);
        final long elapsed = benchmarkRepeatedLookup(wordReference);
        System.out.printf("with HTTP: %sms%n", elapsed);
    }

    private long benchmarkRepeatedLookup(Dict dict) {
        final int repeat = 10;
        final long start = System.currentTimeMillis();
        for (int i = 0; i < repeat; i++) {
            dict.lookup("enit", "word");
        }
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;
        return elapsed;
    }
}
