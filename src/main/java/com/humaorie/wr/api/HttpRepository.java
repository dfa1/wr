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
        final URL url = urlFactory.createUrl(dict, word);
        Preconditions.require("http".equals(url.getProtocol()), "url protocol must be http");
        final URLConnection connection = url.openConnection();
        final InputStream inputStream = connection.getInputStream();
        return new InputStreamReader(inputStream);
    }
}
