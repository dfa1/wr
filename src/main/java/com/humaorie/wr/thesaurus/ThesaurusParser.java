package com.humaorie.wr.thesaurus;

import java.io.Reader;
import java.util.List;

public interface ThesaurusParser {

    List<Sense> parse(Reader reader);
}
