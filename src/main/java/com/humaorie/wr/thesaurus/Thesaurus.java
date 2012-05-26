package com.humaorie.wr.thesaurus;

import java.util.List;

public interface Thesaurus {
    
    List<Sense> lookup(String word);
}
