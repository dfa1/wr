package com.humaorie.wr.api;

import java.util.List;

public class Result {

    private List<Category> categories;
    private String note;

    public static Result create(List<Category> categories, String note) {
        final Result newResult = new Result();
        newResult.setNote(note);
        newResult.setCategories(categories);
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
