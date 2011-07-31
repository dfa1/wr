package com.humaorie.wr.api;

import java.io.Reader;

public interface Repository {

    Reader lookup(String dict, String word);
    
}
