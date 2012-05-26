package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import java.net.UnknownHostException;

public class HttpErrorAwareRepository implements Repository {

    private final Repository repository;

    public HttpErrorAwareRepository(Repository repository) {
        Preconditions.require(repository != null, "repository cannot be null");
        this.repository = repository;
    }

    @Override
    public Reader lookup(String dict, String word) throws IOException {
        try {
            return repository.lookup(dict, word);
        } catch (UnknownHostException ex) {
            throw new WordReferenceException("cannot contact wordreference server", ex);
        }
    }
}
