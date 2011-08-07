package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import org.junit.Assert;
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

    @Test
    public void callCloseWhenParserSucceeds() {
        final MockReader reader = new MockReader();
        final ConstantRepository repository = new ConstantRepository(reader);
        final NoopParser parser = new NoopParser();
        final WordReference wordReference = new WordReference(repository, parser);
        wordReference.lookup("iten", "drago");
        Assert.assertTrue(reader.isClosed());
    }

    public static class MockReader extends Reader {

        private boolean closed = false;

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void close() throws IOException {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }
    }

    public static class ConstantRepository implements Repository {

        private final Reader reader;

        public ConstantRepository(Reader reader) {
            this.reader = reader;
        }

        @Override
        public Reader lookup(String dict, String word) {
            return reader;
        }
    }

    public static class NoopParser implements Parser {

        @Override
        public Result parse(Reader reader) {
            return null;
        }
    }

    @Test
    public void callCloseWhenParserFail() {
        final MockReader reader = new MockReader();
        try {
            final ConstantRepository repository = new ConstantRepository(reader);
            final AlwaysFailingParser parser = new AlwaysFailingParser();
            final WordReference wordReference = new WordReference(repository, parser);
            wordReference.lookup("iten", "drago");
        } catch (IllegalStateException ise) {
            Assert.assertTrue(reader.isClosed());
        }
    }

    public static class AlwaysFailingParser implements Parser {

        @Override
        public Result parse(Reader reader) {
            throw new IllegalStateException("I'm a crappy parser");
        }
    }
}
