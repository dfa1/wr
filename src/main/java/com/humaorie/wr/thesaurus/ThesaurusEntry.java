package com.humaorie.wr.thesaurus;

import java.util.List;

public class ThesaurusEntry {

    private List<Sense> senses;
    private String note;

    public static ThesaurusEntry create(List<Sense> senses, String note) {
        final ThesaurusEntry newEntry = new ThesaurusEntry();
        newEntry.setSenses(senses);
        newEntry.setNote(note);
        return newEntry;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
