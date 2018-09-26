package com.humaorie.wr.dict;

import java.util.List;

public class DictEntry {

    private List<Category> categories;
    private String note;

    public static DictEntry create(List<Category> categories, String note) {
        final DictEntry newEntry = new DictEntry();
        newEntry.setCategories(categories);
        newEntry.setNote(note);
        return newEntry;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }
}
