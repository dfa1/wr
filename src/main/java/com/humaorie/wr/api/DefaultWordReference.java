package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.UnknownHostException;

public class DefaultWordReference implements WordReference {

    private final Repository repository;
    private final Parser parser;

    public DefaultWordReference(Repository repository, Parser parser) {
        Preconditions.require(repository != null, "repository cannot be null");
        Preconditions.require(parser != null, "parser cannot be null");
        this.repository = repository;
        this.parser = parser;
    }

    @Override
    public Result lookup(String dict, String word) {
        try {
            return fetchAndParse(dict, word);
        } catch (RedirectException redirect) {
            return fetchAndParse(redirect.getNewDict(), redirect.getNewWord());
        }
    }

    private Result fetchAndParse(String dict, String word) {
        return parse(fetch(dict, word));
    }

    private Result parse(final Reader reader) {
        try {
            return parser.parse(reader);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                throw new RuntimeException("IO error", ex);
            }
        }
    }

    private Reader fetch(String dict, String word) {
        try {
            return repository.lookup(dict, word);
        } catch (FileNotFoundException ex) {
            throw new WordReferenceException(String.format("dictionary '%s' not found", dict), ex);
        } catch (UnknownHostException ex) {
            throw new WordReferenceException("cannot open connection", ex);
        } catch (IOException ex) {
            throw new RuntimeException("Generic I/O error", ex);
        }
    }
}
