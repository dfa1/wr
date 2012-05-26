package com.humaorie.wr.thesaurus;

import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.WordReferenceException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class DefaultThesaurus implements Thesaurus {

    private final Repository repository;
    private final ThesaurusParser thesaurusParser;

    public DefaultThesaurus(Repository repository, ThesaurusParser thesaurusParser) {
        this.repository = repository;
        this.thesaurusParser = thesaurusParser;
    }
    
    @Override
    public List<Sense> lookup(String word) {
        Reader lookup = null;
        try {
            lookup = repository.lookup("thesaurus", word);
            return thesaurusParser.parse(lookup);
        } catch (IOException ex) {
            throw new WordReferenceException("");
        } finally {
            try {
                lookup.close();
            } catch (IOException ex) {
            }
        }
    }
    
}
