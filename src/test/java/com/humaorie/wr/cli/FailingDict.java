package com.humaorie.wr.cli;

import com.humaorie.wr.dict.DictEntry;
import com.humaorie.wr.dict.Dict;

public class FailingDict implements Dict {

    private final RuntimeException exception;

    public FailingDict(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public DictEntry lookup(String dict, String word) {
        throw exception;
    }
}
