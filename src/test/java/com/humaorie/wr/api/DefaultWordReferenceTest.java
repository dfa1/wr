package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class DefaultWordReferenceTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullRepository() {
        new DefaultWordReference(null, new FailingParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullParser() {
        new DefaultWordReference(new FailiingRepository(), null);
    }

    public static class FailiingRepository implements Repository {

        @Override
        public Reader lookup(String dict, String word) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    public void callCloseWhenParserSucceeds() {
        final MockReader reader = new MockReader();
        final ConstantRepository repository = new ConstantRepository(reader);
        final NoopParser parser = new NoopParser();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);
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
            final FailingParser parser = new FailingParser();
            final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);
            wordReference.lookup("iten", "drago");
        } catch (IllegalStateException ise) {
            Assert.assertTrue(reader.isClosed());
        }
    }

    public static class FailingParser implements Parser {

        @Override
        public Result parse(Reader reader) {
            throw new IllegalStateException("I'm a crappy parser");
        }
    }

    @Test
    public void canHandleRedirects() {
        final Result expectedResult = new Result("expected", null);
        final Parser parser = new ConstantParser(expectedResult);
        final Repository repository = new RedirectOnceRepository();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);
        final Result result = wordReference.lookup("iten", "drago");
        
        Assert.assertSame(expectedResult, result);
    }

    public static class RedirectOnceRepository implements Repository {

        private boolean alreadyRedirected = false;

        @Override
        public Reader lookup(String dict, String word) {
            if (!alreadyRedirected) {
                alreadyRedirected = true;
                throw new RedirectException(dict, word);
            }

            return null;
        }
    }

    public static class ConstantParser implements Parser {

        private final Result result;

        public ConstantParser(Result result) {
            this.result = result;
        }

        @Override
        public Result parse(Reader reader) {
            return result;
        }
    }
}
