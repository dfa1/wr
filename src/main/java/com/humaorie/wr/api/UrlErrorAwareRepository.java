package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.UnknownHostException;

/**
 * Common exception wrapping logic for url-based repositories.
 * 
 * @see UrlGzippedRepository
 * @see UrlRepository
*/
public class UrlErrorAwareRepository implements Repository {

    private final Repository repository;

    public UrlErrorAwareRepository(Repository repository) {
        Preconditions.require(repository != null, "repository cannot be null");
        this.repository = repository;
    }

    @Override
    public Reader lookup(String dict, String word) {
        try {
            return repository.lookup(dict, word);
        } catch (FileNotFoundException ex) {
            throw new WordReferenceException(String.format("dictionary '%s' not found", dict), ex);
        } catch (UnknownHostException ex) {
            throw new WordReferenceException("cannot open connection", ex);
        } catch (IOException ex) {
            throw new WordReferenceException("I/O error" ,ex);
        }
    }
}