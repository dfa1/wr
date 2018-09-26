package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;

// TODO: rename
public interface Repository {

    Reader lookup(String dict, String word) throws IOException;
}
