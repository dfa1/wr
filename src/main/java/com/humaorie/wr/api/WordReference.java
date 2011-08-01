package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class WordReference {

    private final Repository repository;
    private Parser parser = new JSONParser();

    public WordReference(Repository repository) {
        this.repository = repository;
    }

    public List<Category> lookup(String dict, String word) {
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
        } catch (RedirectException exception) { // TODO: using exception for flow-control
            String redirectUrl = exception.getRedirectUrl();
            String[] dictAndWord = redirectUrl.split("/");
            return repository.lookup(dictAndWord[0], dictAndWord[1]);
        }
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
