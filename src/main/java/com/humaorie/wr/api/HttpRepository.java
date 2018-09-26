package com.humaorie.wr.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;


public class HttpRepository implements Repository {

    private final UrlFactory urlFactory;

    public HttpRepository(UrlFactory urlFactory) {
        Preconditions.require(urlFactory != null, "urlFactory cannot be null");
        this.urlFactory = urlFactory;
    }

    @Override
    public Reader lookup(String dict, String word) throws IOException {
        final URL url = this.urlFactory.createUrl(dict, word);
        final URLConnection connection = url.openConnection();
        final InputStream inputStream = connection.getInputStream();
        return new InputStreamReader(inputStream);
    }
}
