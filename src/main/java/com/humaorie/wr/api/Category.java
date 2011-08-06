package com.humaorie.wr.api;

import java.util.Collections;
import java.util.List;

public class Category {

    private final String name;
    private final List<Translation> translations;

    public Category(String name, List<Translation> translations) {
        this.name = name;
        this.translations = translations;
    }

    public String getName() {
        return name;
    }

    public List<Translation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }
}
