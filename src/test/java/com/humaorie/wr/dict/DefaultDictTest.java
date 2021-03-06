package com.humaorie.wr.dict;

import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.WordReferenceException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class DefaultDictTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullRepository() {
        new DefaultDict(null, new FailingParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWordReferenceWithNullParser() {
        new DefaultDict(new FailingRepository(), null);
    }

    @Test(expected = WordReferenceException.class)
    public void refuseDictNotOfLengthFour() throws IOException {
        final Dict dict = new DefaultDict(new ConstantRepository(null), new ConstantParser(null));
        dict.lookup("en", "word");
    }

    public static class FailingRepository implements Repository {

        @Override
        public Reader lookup(String dict, String word) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    public void callCloseWhenParserSucceeds() {
        final SpyOnClosingReader reader = new SpyOnClosingReader();
        final ConstantRepository repository = new ConstantRepository(reader);
        final NoopParser parser = new NoopParser();
        final DefaultDict wordReference = new DefaultDict(repository, parser);
        wordReference.lookup("iten", "drago");
        Assert.assertTrue(reader.isClosed());
    }

    public static class SpyOnClosingReader extends Reader {

        private boolean closed = false;

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void close() throws IOException {
            this.closed = true;
        }

        public boolean isClosed() {
            return this.closed;
        }
    }

    public static class ConstantRepository implements Repository {

        private final Reader reader;

        public ConstantRepository(Reader reader) {
            this.reader = reader;
        }

        @Override
        public Reader lookup(String dict, String word) {
            return this.reader;
        }
    }

    public static class NoopParser implements DictParser {

        @Override
        public DictEntry parse(Reader reader) {
            return new DictEntry();
        }
    }

    @Test
    public void callCloseWhenParserFail() {
        final SpyOnClosingReader reader = new SpyOnClosingReader();
        try {
            final ConstantRepository repository = new ConstantRepository(reader);
            final FailingParser parser = new FailingParser();
            final DefaultDict wordReference = new DefaultDict(repository, parser);
            wordReference.lookup("iten", "drago");
        } catch (final IllegalStateException ex) {
            // ignoring exception from failing parser
        }

        Assert.assertTrue(reader.isClosed());
    }

    public static class FailingParser implements DictParser {

        @Override
        public DictEntry parse(Reader reader) {
            throw new IllegalStateException("simulated error");
        }
    }

    @Test
    public void canHandleRedirects() {
        final DictEntry expectedResult = DictEntry.of(null, "expected");
        final DictParser parser = new ConstantParser(expectedResult);
        final Repository repository = new RedirectOnceRepository();
        final DefaultDict wordReference = new DefaultDict(repository, parser);
        final DictEntry result = wordReference.lookup("iten", "drago");
        Assert.assertSame(expectedResult, result); // TODO: weak test
    }

    public static class RedirectOnceRepository implements Repository {

        private boolean alreadyRedirected = false;

        @Override
        public Reader lookup(String dict, String word) {
            if (!this.alreadyRedirected) {
                this.alreadyRedirected = true;
                return new StringReader("");
            }

            return new StringReader("");
        }
    }

    public static class ConstantParser implements DictParser {

        private final DictEntry result;

        public ConstantParser(DictEntry result) {
            this.result = result;
        }

        @Override
        public DictEntry parse(Reader reader) {
            return this.result;
        }
    }

    @Test(expected = WordReferenceException.class)
    public void wrapIOExceptions() {
        final Reader failOnCloseReader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("baaaaaad close!");
            }
        };
        final Repository repository = new ConstantRepository(failOnCloseReader);
        final DefaultDict wr = new DefaultDict(repository, new ConstantParser(new DictEntry()));
        wr.lookup("dict", "word");
    }
}
