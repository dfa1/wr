package com.humaorie.wr.dict;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.ConstantApiKeyProvider;
import com.humaorie.wr.api.HttpRepository;
import com.humaorie.wr.api.JsonUrlFactory;
import com.humaorie.wr.api.Repository;
import com.humaorie.wr.dict.JsonDictParser;
import com.humaorie.wr.dict.Dict;
import com.humaorie.wr.dict.DefaultDict;
import com.humaorie.wr.dict.DictParser;
import org.junit.Test;

public class Benchmarks {

    private final String TESTING_API_KEY = "2f6ce"; // this is a real key, please don't abuse

    @Test
    public void lookupRepeatedly() {
        final ApiKeyProvider apiKeyProvider = new ConstantApiKeyProvider(TESTING_API_KEY);
        final JsonUrlFactory urlFactory = new JsonUrlFactory(apiKeyProvider);
        final Repository repository = new HttpRepository(urlFactory);
        final DictParser parser = new JsonDictParser();
        final Dict wordReference = new DefaultDict(repository, parser);
        final long elapsed = benchmarkRepeatedLookup(wordReference);
        System.out.printf("with HTTP/GZIP   : %sms%n", elapsed);
    }

    private long benchmarkRepeatedLookup(Dict sut) {
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
