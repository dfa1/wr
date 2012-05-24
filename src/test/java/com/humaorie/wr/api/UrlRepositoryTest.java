package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

public class UrlRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new UrlRepository(null);
    }

    @Test
    public void canOpenReaders() throws IOException {
        final UrlFactory urlFactory = new UrlFactory() {

            @Override
            public URL createUrl(String dict, String word) throws IOException {
                return this.getClass().getResource("iten-drago.json");
            }
        };
        final UrlRepository repository = new UrlRepository(urlFactory);
        final Reader reader = repository.lookup("dict", "word");
        Assert.assertNotNull(reader);
    }
}
