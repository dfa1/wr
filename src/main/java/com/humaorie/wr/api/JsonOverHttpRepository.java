package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;

public class JsonOverHttpRepository implements Repository {
    
    private final UrlFactory urlFactory;

    public JsonOverHttpRepository(UrlFactory urlFactory) {
        Preconditions.require(urlFactory != null, "urlFactory cannot be null");
        this.urlFactory = urlFactory;
    }
    
    @Override
    public Reader lookup(String dict, String word) {
        try {
            final URL url = urlFactory.createUrl(dict, word);
            return new InputStreamReader(url.openStream());
        } catch (FileNotFoundException ex) {
            throw new NotFoundException("dictionary '%s' not found", dict);
        } catch (UnknownHostException ex) {
            throw new NotFoundException("cannot open http://api.wordreference.com"); 
        } catch (IOException ex) {
            throw new RuntimeException("I/O error", ex); 
        }
    }
}
