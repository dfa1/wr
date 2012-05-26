package com.humaorie.wr.dict;

import java.util.List;

// FIXME: rename
public class DictEntry {

    private List<Category> categories;
    private String note;

    public static DictEntry create(List<Category> categories, String note) {
        final DictEntry newResult = new DictEntry();
        newResult.setCategories(categories);
        newResult.setNote(note);
        return newResult;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getNote() {
        return note;
    }
}
