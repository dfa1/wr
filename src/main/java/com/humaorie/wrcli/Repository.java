package com.humaorie.wrcli;

import java.io.Reader;

public interface Repository {

    Reader lookup(String dict, String word);
    
}
