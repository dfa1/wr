package com.humaorie.wr.api;

import java.util.List;

public class Result  {

    private final String note;
    private final List<Category> categories;

    public Result(String note, List<Category> categories) {
        this.note = note;
        this.categories = categories;
    }

    public String getNote() {
        return note;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
