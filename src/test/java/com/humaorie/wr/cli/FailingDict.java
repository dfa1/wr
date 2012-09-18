package com.humaorie.wr.cli;

import com.humaorie.wr.dict.Dict;
import com.humaorie.wr.dict.DictEntry;

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
