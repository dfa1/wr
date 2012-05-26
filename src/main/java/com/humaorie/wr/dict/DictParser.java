package com.humaorie.wr.dict;

import java.io.Reader;

public interface DictParser {

    DictEntry parse(Reader reader);
}
