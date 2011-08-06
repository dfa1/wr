package com.humaorie.wr.api;

public class RedirectException extends RuntimeException {

    private String newDict;
    private String newWord;

    public RedirectException(String newDict, String newWord) {
        this.newDict = newDict;
        this.newWord = newWord;
    }

    public String getNewWord() {
        return newWord;
    }

    public String getNewDict() {
        return newDict;
    }
}
