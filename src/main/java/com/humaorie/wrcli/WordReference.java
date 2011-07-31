package com.humaorie.wrcli;

import com.humaorie.wrcli.model.Category;
import java.io.Reader;
import java.util.List;

public class WordReference {

    private final Repository repository;
    private Parser parser = new JSONParser();
    
    public WordReference(Repository repository) {
        this.repository = repository;
    }

    public List<Category> lookup(String dict, String word) {
        Reader reader = repository.lookup(dict, word);
        List<Category> categories = parser.parseDefinition(reader);
        return categories;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
