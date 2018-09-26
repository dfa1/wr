package com.humaorie.wr.thesaurus;

public class ConstantThesaurus implements Thesaurus {

    private final ThesaurusEntry entry;

    public ConstantThesaurus(ThesaurusEntry entry) {
        this.entry = entry;
    }

    @Override
    public ThesaurusEntry lookup(String word) {
        return this.entry;
    }
}
