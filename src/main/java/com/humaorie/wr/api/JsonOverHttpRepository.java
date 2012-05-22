package com.humaorie.wr.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class JsonOverHttpRepository implements Repository {

    private final UrlFactory urlFactory;

    public JsonOverHttpRepository(UrlFactory urlFactory) {
        Preconditions.require(urlFactory != null, "urlFactory cannot be null");
        this.urlFactory = urlFactory;
    }

    @Override
    public Reader lookup(String dict, String word) throws IOException {
        final URL url = urlFactory.createUrl(dict, word);
        return new InputStreamReader(url.openStream());
    }
}
