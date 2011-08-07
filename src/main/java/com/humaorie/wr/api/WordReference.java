package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;

public class WordReference {

    private final Repository repository;
    private final Parser parser;

    public WordReference(Repository repository, Parser parser) {
        Preconditions.require(repository != null, "repository cannot be null");
        Preconditions.require(parser != null, "parser cannot be null");
        this.repository = repository;
        this.parser = parser;
    }

    public Result lookup(String dict, String word) {
        try {
            return lookupResult(dict, word);
        } catch (RedirectException redirect) { // XXX: using exception for flow-control is evil
            return lookupResult(redirect.getNewDict(), redirect.getNewWord());
        }
    }

    private Result lookupResult(String dict, String word) {
        final Reader reader = repository.lookup(dict, word);

        try {
            return parser.parse(reader);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
