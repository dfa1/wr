package com.humaorie.wr.api;

import java.io.Reader;
import org.junit.Test;

public class WordReferenceTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullRepository() {
        new WordReference(null, new StubParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullParser() {
        new WordReference(new StubRepository(), null);
    }

    public static class StubRepository implements Repository {

        @Override
        public Reader lookup(String dict, String word) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static class StubParser implements Parser {

        @Override
        public Result parse(Reader reader) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
