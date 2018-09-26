package com.humaorie.wr.thesaurus;

public class Synonym {

    private String name;
    private String context;

    public static Synonym create(String name, String context) {
        final Synonym newSynonym = new Synonym();
        newSynonym.setName(name);
        newSynonym.setContext(context);
        return newSynonym;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
