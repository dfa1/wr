package com.humaorie.wr.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.UnknownHostException;
import org.junit.Test;

public class UrlErrorAwareRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void innerRepositoryCannotBeNull() {
        new UrlErrorAwareRepository(null);
    }

    @Test(expected = RuntimeException.class)
    public void ignoreRuntimeExceptions() {
        final Repository failingRepository = new Repository() {

            @Override
            public Reader lookup(String dict, String word) throws IOException {
                throw new NullPointerException("I'm a bad repository");
            }
        };
        final UrlErrorAwareRepository repository = new UrlErrorAwareRepository(failingRepository);
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
        final UrlErrorAwareRepository repository = new UrlErrorAwareRepository(failingRepository);
        repository.lookup("dict", "word");
    }
    
    @Test(expected = WordReferenceException.class)
    public void wrapFileNotExceptions() {
        final Repository failingRepository = new Repository() {

            @Override
            public Reader lookup(String dict, String word) throws IOException {
                throw new FileNotFoundException("dict not found");
            }
        };
        final UrlErrorAwareRepository repository = new UrlErrorAwareRepository(failingRepository);
        repository.lookup("dict", "word");
    }
    
    @Test(expected = WordReferenceException.class)
    public void wrapUnknownHostExceptions() {
        final Repository failingRepository = new Repository() {

            @Override
            public Reader lookup(String dict, String word) throws IOException {
                throw new UnknownHostException("dns down?");
            }
        };
        final UrlErrorAwareRepository repository = new UrlErrorAwareRepository(failingRepository);
        repository.lookup("dict", "word");
    }
}
