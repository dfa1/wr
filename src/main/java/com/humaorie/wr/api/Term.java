package com.humaorie.wr.api;

public class Term {

    private String term;
    private String pos;
    private String sense;
    private String usage;

    public static Term create(String term, String pos, String sense, String usage) {
        final Term newTerm = new Term();
        newTerm.setTerm(term);
        newTerm.setPos(pos);
        newTerm.setSense(sense);
        newTerm.setUsage(usage);
        return newTerm;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getPos() {
        return pos;
    }

    public String getSense() {
        return sense;
    }

    public String getTerm() {
        return term;
    }

    public String getUsage() {
        return usage;
    }
}
