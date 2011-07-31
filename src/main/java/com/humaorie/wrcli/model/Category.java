package com.humaorie.wrcli.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {

    private final String name;
    private final List<Translation> translations = new ArrayList<Translation>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Translation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

    public Category addTranslation(Translation translation) {
        translations.add(translation);
        return this;
    }
}
