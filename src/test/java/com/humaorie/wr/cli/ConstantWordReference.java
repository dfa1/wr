package com.humaorie.wr.cli;

import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.WordReference;

public class ConstantWordReference implements WordReference {

    private final Result result;

    public ConstantWordReference(Result result) {
        this.result = result;
    }

    @Override
    public Result lookup(String dict, String word) {
        return result;
    }
}
