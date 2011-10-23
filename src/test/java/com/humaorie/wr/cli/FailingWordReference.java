package com.humaorie.wr.cli;

import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.WordReference;

public class FailingWordReference implements WordReference {

    private final RuntimeException exception;

    public FailingWordReference(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public Result lookup(String dict, String word) {
        throw exception;
    }
}
