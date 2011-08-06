package com.humaorie.wr.api;

import java.util.Collections;
import java.util.List;

public class Translation {

    private final Term originalTerm;
    private final List<Term> translations;
    private final String note;

    public Translation(Term originalTerm, List<Term> translations, String note) {
        this.originalTerm = originalTerm;
        this.translations = translations;
        this.note = note;
    }

    public Term getOriginalTerm() {
        return originalTerm;
    }

    public List<Term> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    public String getNote() {
        return note;
    }
}
