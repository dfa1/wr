package com.humaorie.wr.api;

import java.io.Reader;
import junit.framework.Assert;
import org.junit.Test;

public class LocalJsonRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullDict() {
        LocalJsonRepository repository = new LocalJsonRepository();
        repository.lookup(null, "word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyDict() {
        LocalJsonRepository repository = new LocalJsonRepository();
        repository.lookup("", "word");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseNullWord() {
        LocalJsonRepository repository = new LocalJsonRepository();
        repository.lookup("enit", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseEmptyWord() {
        LocalJsonRepository repository = new LocalJsonRepository();
        repository.lookup("enit", "");
    }

    @Test(expected = WordReferenceException.class)
    public void unknownDictLeadsToException() {
        LocalJsonRepository repository = new LocalJsonRepository();
        repository.lookup("invalid_dict", "word");
    }

    @Test
    public void canLoadJsonFile() {
        final LocalJsonRepository repository = new LocalJsonRepository();
        final Reader reader = repository.lookup("iten", "drago");
        Assert.assertNotNull(reader);
    }
}
