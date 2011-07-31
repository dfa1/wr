package com.humaorie.wr.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Translation {

    private final String note;
    private final List<Term> translations = new ArrayList<Term>();

    public Translation(String note) {
        this.note = note;
    }

    public List<Term> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    public void addTerm(Term term) {
        translations.add(term);
    }
}
