package com.humaorie.wr.api;

import java.io.Reader;
import java.util.List;

public interface Parser {

    List<Category> parseDefinition(Reader reader);
}
