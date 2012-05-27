package com.humaorie.wr.dict;

import java.util.List;

public class Category {

    private String name;
    private List<Translation> translations;

    public static Category create(String name, List<Translation> translations) {
        final Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setTranslations(translations);
        return newCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        return translations;
    }
}
