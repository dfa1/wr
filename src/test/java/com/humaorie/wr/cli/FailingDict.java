package com.humaorie.wr.cli;

import com.humaorie.wr.dict.Result;
import com.humaorie.wr.dict.Dict;

public class FailingDict implements Dict {

    private final RuntimeException exception;

    public FailingDict(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public Result lookup(String dict, String word) {
        throw exception;
    }
}
