package com.humaorie.wr.api;

import java.util.List;

public class Result  {

    private final String note;
    private final List<Category> category;

    public Result(String note, List<Category> category) {
        this.note = note;
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public List<Category> getCategory() {
        return category;
    }
}
