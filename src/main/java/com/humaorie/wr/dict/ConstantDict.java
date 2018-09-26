package com.humaorie.wr.dict;


public class ConstantDict implements Dict {

    private final DictEntry result;

    public ConstantDict(DictEntry result) {
        this.result = result;
    }

    @Override
    public DictEntry lookup(String dict, String word) {
        return this.result;
    }
}
