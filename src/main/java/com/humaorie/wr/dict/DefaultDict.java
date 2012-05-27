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
            return tryLookoup(dict, word);
        } catch (RedirectException redirect) {
            return tryLookoup(redirect.getNewDict(), redirect.getNewWord());
        }
    }

    private DictEntry tryLookoup(String dict, String word) {
        try {
            return parse(fetch(dict, word));
        } catch (IOException ex) {
            throw new WordReferenceException("I/O error", ex);
        }
    }

    private DictEntry parse(Reader reader) throws IOException {
        try {
            return parser.parse(reader);
        } finally {
            reader.close();
        }
    }

    private Reader fetch(String dict, String word) throws IOException {
        // XXX: when a dict is not of length 4 the response is an HTML document :/
        if (dict.length() != 4) {
            throw new WordReferenceException("dict must be of length 4");
        }
        return repository.lookup(dict, word);
    }
}
