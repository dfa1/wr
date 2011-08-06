package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;

public class WordReference {

    private final Repository repository;
    private final Parser parser;

    public WordReference(Repository repository, Parser parser) {
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

    private Result lookupResult(String dict, String word)  {
        Reader reader = null;
        try {
            reader = repository.lookup(dict, word);
            return parser.parseDefinition(reader);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
