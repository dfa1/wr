package com.humaorie.wrcli.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Translation {

    private final Term originalTerm;
    private final List<Term> translations = new ArrayList<Term>();

    public Translation(Term originalTerm) {
        this.originalTerm = originalTerm;
    }

    public Term getOriginalTerm() {
        return originalTerm;
    }

    public List<Term> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    public void addTerm(Term term) {
        translations.add(term);
    }
}
