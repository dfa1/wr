package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import org.junit.Test;

public class ErrorAwareRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void innerRepositoryCannotBeNull() {
        new ErrorAwareRepository(null);
    }

    @Test(expected = RuntimeException.class)
    public void ignoreRuntimeExceptions() {
        final Repository failingRepository = new Repository() {

            @Override
            public Reader lookup(String dict, String word) throws IOException {
                throw new NullPointerException("I'm a bad repository");
            }
        };
        final ErrorAwareRepository repository = new ErrorAwareRepository(failingRepository);
        repository.lookup("dict", "word");
    }

    @Test(expected = WordReferenceException.class)
    public void wrapIOExceptions() {
        final Repository failingRepository = new Repository() {

            @Override
            public Reader lookup(String dict, String word) throws IOException {
                throw new IOException("shit happenz");
            }
        };
        final ErrorAwareRepository repository = new ErrorAwareRepository(failingRepository);
        repository.lookup("dict", "word");
    }
}
