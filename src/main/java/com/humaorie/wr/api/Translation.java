package com.humaorie.wr.api;

import java.util.Collections;
import java.util.List;

public class Translation {

    private final String note;
    private final List<Term> translations;

    public Translation(String note, List<Term> translations) {
        this.note = note;
        this.translations = translations;
    }

    public String getNote() {
        return note;
    }

    public List<Term> getTranslations() {
        return Collections.unmodifiableList(translations);
    }
}
