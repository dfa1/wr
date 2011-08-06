package com.humaorie.wr.api;

import java.io.Reader;

public interface Parser {

    Result parse(Reader reader);
}
