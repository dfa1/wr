package com.humaorie.wr.api;

import org.junit.Test;

public class InternetJsonRepositoryTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void refuseNullDict() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup(null, "word");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void refuseEmptyDict() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("", "word");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void refuseNullTerm() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("dict", null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void refuseEmptyTerm() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("dict", "");
    }
}
