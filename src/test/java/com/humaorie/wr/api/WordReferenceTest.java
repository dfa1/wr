package com.humaorie.wr.api;

import java.util.List;
import org.junit.Test;

public class WordReferenceTest {
    
    @Test
    public void integrationTest() {
        Repository repository = new FileSystemRepository();
        WordReference wordReference = new WordReference(repository);
        List<Category> categories = wordReference.lookup("enit", "run");

        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }
}
