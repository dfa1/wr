package com.humaorie.wr.cli;

import com.humaorie.wr.dict.DictEntry;
import com.humaorie.wr.dict.Dict;

public class ConstantDict implements Dict {

    private final DictEntry result;

    public ConstantDict(DictEntry result) {
        this.result = result;
    }

    @Override
    public DictEntry lookup(String dict, String word) {
        return result;
    }
}
