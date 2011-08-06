package com.humaorie.wr.api;

import java.io.Reader;
import java.util.List;

public interface Parser {

    Result parseDefinition(Reader reader);
}
