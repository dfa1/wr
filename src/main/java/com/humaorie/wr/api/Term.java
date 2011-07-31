package com.humaorie.wr.api;

public class Term {

    private final String term;
    private final String pos;
    private final String sense;
    private final String usage;

    public Term(String term, String pos, String sense, String usage) {
        this.term = term;
        this.pos = pos;
        this.sense = sense;
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
