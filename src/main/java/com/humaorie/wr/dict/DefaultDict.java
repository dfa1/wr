package com.humaorie.wr.dict;

import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.WordReferenceException;
import java.io.IOException;
import java.io.Reader;

public class DefaultDict implements Dict {

    private final Repository repository;
    private final DictParser parser;

    public DefaultDict(Repository repository, DictParser parser) {
        Preconditions.require(repository != null, "repository cannot be null");
        Preconditions.require(parser != null, "parser cannot be null");
        this.repository = repository;
        this.parser = parser;
    }

    @Override
    public DictEntry lookup(String dict, String word) {
        try {
            return this.tryLookoup(dict, word);
        } catch (final RedirectException redirect) {
            return this.tryLookoup(redirect.getNewDict(), redirect.getNewWord());
        }
    }

    private DictEntry tryLookoup(String dict, String word) {
        try {
            return this.parse(this.fetch(dict, word));
        } catch (final IOException ex) {
            throw new WordReferenceException(String.format("I/O error: %s", ex.getMessage()), ex);
        }
    }

    private DictEntry parse(Reader reader) throws IOException {
        try {
            return this.parser.parse(reader);
        } finally {
            reader.close();
        }
    }

    private Reader fetch(String dict, String word) throws IOException {
        // XXX: when a dict is not of length 4 the response is an HTML document :/
        if (dict.length() != 4) {
            throw new WordReferenceException("dict must be of length 4");
        }
        return this.repository.lookup(dict, word);
    }
}
