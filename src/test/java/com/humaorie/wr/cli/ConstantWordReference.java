package com.humaorie.wr.cli;

import com.humaorie.wr.dict.Result;
import com.humaorie.wr.dict.Dict;

public class ConstantWordReference implements Dict {

    private final Result result;

    public ConstantWordReference(Result result) {
        this.result = result;
    }

    @Override
    public Result lookup(String dict, String word) {
        return result;
    }
}
