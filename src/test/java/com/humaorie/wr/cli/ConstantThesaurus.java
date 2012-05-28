package com.humaorie.wr.cli;

import com.humaorie.wr.thesaurus.Thesaurus;
import com.humaorie.wr.thesaurus.ThesaurusEntry;

public class ConstantThesaurus implements Thesaurus {

    private final ThesaurusEntry entry;

    public ConstantThesaurus(ThesaurusEntry entry) {
        this.entry = entry;
    }

    @Override
    public ThesaurusEntry lookup(String word) {
        return entry;
    }
}
