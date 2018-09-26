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
		DictEntry attempt = this.tryLookup(dict, word);
		if (attempt.isRedirect()) {
			return lookup(attempt.getNewDict(), attempt.getNewWord());
		} else {
			return attempt;
		}
	}

	private DictEntry tryLookup(String dict, String word) {
		try (Reader document = this.fetch(dict, word)) {
            return this.parser.parse(document);
		} catch (final IOException ex) {
			throw new WordReferenceException(String.format("I/O error: %s", ex.getMessage()), ex);
		}
	}

	private Reader fetch(String dict, String word) throws IOException {
		// XXX: when a dict is not of length 4 the response is an HTML document :/
		// TODO: validte as soon as possible (e.g. use a value object?)
		if (dict.length() != 4) {
			throw new WordReferenceException("dict must be of length 4");
		}
		return this.repository.lookup(dict, word);
	}
}
