package com.humaorie.wr.api;

import java.util.List;

public class Translation {

    private Term originalTerm;
    private List<Term> translations;
    private String note;

    public static Translation create(Term originalTerm, List<Term> translations, String note) {
        final Translation newTranslation = new Translation();
        newTranslation.setOriginalTerm(originalTerm);
        newTranslation.setTranslations(translations);
        newTranslation.setNote(note);
        return newTranslation;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setOriginalTerm(Term originalTerm) {
        this.originalTerm = originalTerm;
    }

    public void setTranslations(List<Term> translations) {
        this.translations = translations;
    }

    public Term getOriginalTerm() {
        return originalTerm;
    }

    public List<Term> getTranslations() {
        return translations;
    }

    public String getNote() {
        return note;
    }
}
