package com.humaorie.wr.api;

import java.io.IOException;
import java.net.URL;
import org.junit.Test;

public class HttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new HttpRepository(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void urlProtocolMustBeHttp() throws IOException {
        final UrlFactory urlFactory = new UrlFactory() {
            @Override
            public URL createUrl(String dict, String word) throws IOException {
                return new URL("file:///test.gz");
            }
        };
        final HttpRepository repository = new HttpRepository(urlFactory);
        repository.lookup("dict", "word");
    }
}
