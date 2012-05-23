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
            return tryLookoup(dict, word);
        } catch (RedirectException redirect) {
            return tryLookoup(redirect.getNewDict(), redirect.getNewWord());
        }
    }

    private Result tryLookoup(String dict, String word) {
        try {
            return parse(fetch(dict, word));
        } catch (IOException ex) {
            throw new WordReferenceException("I/O error", ex);
        }
    }

    private Result parse(Reader reader) throws IOException {
        try {
            return parser.parse(reader);
        } finally {
            reader.close();
        }
    }

    private Reader fetch(String dict, String word) throws IOException {
        return repository.lookup(dict, word);
    }
}
