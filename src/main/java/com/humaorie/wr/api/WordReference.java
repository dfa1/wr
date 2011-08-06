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
        Reader reader = null;

        try {
            reader = retrieveDocument(dict, word);
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

    private Reader retrieveDocument(String dict, String word) {
        try {
            return repository.lookup(dict, word);
        } catch (RedirectException exception) { // XXX: smess using exception for flow-control
            String redirectUrl = exception.getRedirectUrl();
            String[] dictAndWord = redirectUrl.split("/");
            return repository.lookup(dictAndWord[0], dictAndWord[1]);
        } 
    }
}
