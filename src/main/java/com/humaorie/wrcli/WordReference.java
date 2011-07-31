package com.humaorie.wrcli;

import java.io.Reader;
import org.json.JSONObject;

public class WordReference {

    private final Repository repository;
    private Parser parser = new JSONParser();
    private Outputter outputter = new WholeOutputter();
    
    public WordReference(Repository repository) {
        this.repository = repository;
    }

    public void lookup(String dict, String word) {
        Reader reader = repository.lookup(dict, word);
        JSONObject result = parser.parseDefinition(reader);
        outputter.output(result);
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setOutputter(Outputter outputter) {
        this.outputter = outputter;
    }
}
