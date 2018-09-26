package com.humaorie.wr.thesaurus;

import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.WordReferenceException;
import java.io.IOException;
import java.io.Reader;

public class DefaultThesaurus implements Thesaurus {

    private final Repository repository;
    private final ThesaurusParser thesaurusParser;

    public DefaultThesaurus(Repository repository, ThesaurusParser thesaurusParser) {
        this.repository = repository;
        this.thesaurusParser = thesaurusParser;
    }

    @Override
    public ThesaurusEntry lookup(String word) {
        try {
            return this.tryLookup(word);
        } catch (final IOException ex) {
            throw new WordReferenceException(String.format("I/O error: %s", ex.getMessage()), ex);
        }
    }

    private ThesaurusEntry tryLookup(String word) throws IOException {
        final Reader lookup = this.repository.lookup("thesaurus", word);
        // web service yields a zero-len response when the word is not found (!)
        try {
            return this.thesaurusParser.parse(lookup);
        } finally {
            lookup.close();
        }
    }
}
