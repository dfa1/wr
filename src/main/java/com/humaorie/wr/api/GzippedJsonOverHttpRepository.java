package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

public class GzippedJsonOverHttpRepository implements Repository {

    private final UrlFactory urlFactory;

    public GzippedJsonOverHttpRepository(UrlFactory urlFactory) {
        Preconditions.require(urlFactory != null, "urlFactory cannot be null");
        this.urlFactory = urlFactory;
    }

    @Override
    public Reader lookup(String dict, String word) {
        try {
            final URL url = urlFactory.createUrl(dict, word);
            final URLConnection openConnection = url.openConnection();
            openConnection.addRequestProperty("Accept-Encoding", "gzip");
            final InputStream inputStream = openConnection.getInputStream();
            final GZIPInputStream gzipInputStream= new GZIPInputStream(inputStream);
            return new InputStreamReader(gzipInputStream);
        } catch (FileNotFoundException ex) {
            throw new WordReferenceException("dictionary '%s' not found", dict);
        } catch (UnknownHostException ex) {
            throw new WordReferenceException("cannot open %s", UrlFactory.API_URL);
        } catch (IOException ex) {
            throw new RuntimeException("I/O error", ex);
        }
    }
}
