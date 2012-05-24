package com.humaorie.wr.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class UrlGzippedRepository implements Repository {

    private final UrlFactory urlFactory;

    public UrlGzippedRepository(UrlFactory urlFactory) {
        Preconditions.require(urlFactory != null, "urlFactory cannot be null");
        this.urlFactory = urlFactory;
    }

    @Override
    public Reader lookup(String dict, String word) throws IOException {
        final URL url = urlFactory.createUrl(dict, word);
        final URLConnection openConnection = url.openConnection();
        openConnection.addRequestProperty("Accept-Encoding", "gzip");
        final InputStream inputStream = openConnection.getInputStream();
        final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        return new InputStreamReader(gzipInputStream);
    }
}
