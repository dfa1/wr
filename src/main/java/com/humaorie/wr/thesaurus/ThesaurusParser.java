package com.humaorie.wr.thesaurus;

import java.io.Reader;

public interface ThesaurusParser {

    ThesaurusEntry parse(Reader reader);
}
