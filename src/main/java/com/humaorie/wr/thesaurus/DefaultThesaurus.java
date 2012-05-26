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
        try {
            return tryLookup(word);
        } catch (IOException ex) {
            throw new WordReferenceException("I/O error", ex);
        }
    }

    private List<Sense> tryLookup(String word) throws IOException {
        Reader lookup = repository.lookup("thesaurus", word);
        try {
            return thesaurusParser.parse(lookup);
        } finally {
            lookup.close();
        }
    }
}
