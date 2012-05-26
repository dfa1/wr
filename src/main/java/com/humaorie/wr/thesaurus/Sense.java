package com.humaorie.wr.thesaurus;

import java.util.List;

public class Sense {

    private String text;
    private List<Synonym> synonyms;

    public static Sense create(String text, List<Synonym> synonyms) {
        final Sense sense = new Sense();
        sense.setText(text);
        sense.setSynonyms(synonyms);
        return sense;   
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public List<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
    }
}
