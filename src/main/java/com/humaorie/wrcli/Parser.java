package com.humaorie.wrcli;

import com.humaorie.wrcli.model.Category;
import java.io.Reader;
import java.util.List;

public interface Parser {

    List<Category> parseDefinition(Reader reader);
}
