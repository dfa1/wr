package com.humaorie.wr.api;

import org.junit.Test;

public class WordReferenceTest {
    
    @Test
    public void integrationTest() {
        Repository repository = new FileSystemRepository();
        WordReference wordReference = new WordReference(repository);
        Result result = wordReference.lookup("enit", "run");

        for (Category category : result.getCategory()) {
            System.out.println(category.getName());
        }
    }
}
