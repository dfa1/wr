package com.humaorie.wr.api;

import org.junit.Ignore;
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
    public void refuseNullWord() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("enit", null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void refuseEmptyWord() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("enit", "");
    }
    
    @Ignore("how to simulate this?")
    @Test(expected=NotFoundException.class)
    public void unknownDictLeadsToException() {
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.lookup("invalid_dict", "word"); 
    }
}
