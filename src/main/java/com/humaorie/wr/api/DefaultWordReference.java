package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;

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
        final Reader reader = repository.lookup(dict, word);
        
        try {
            return parser.parse(reader);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                throw new IllegalStateException("IO error", ex);
            }
        }
    }
}
