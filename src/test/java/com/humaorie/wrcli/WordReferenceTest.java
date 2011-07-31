package com.humaorie.wrcli;

import org.junit.Test;

public class WordReferenceTest {
    
    @Test
    public void integrationTest() {
        Repository repository = new FileSystemRepository();
        WordReference wordReference = new WordReference(repository);
        wordReference.lookup("enit", "run");
    }
}
