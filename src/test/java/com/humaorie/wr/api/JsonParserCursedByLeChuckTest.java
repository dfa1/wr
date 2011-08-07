package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserCursedByLeChuckTest {

    @Test
    public void canParseValidDocument() {
        final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
        final Reader reader = loadFile("/data/iten-drago.json");
        final Result result = parser.parse(reader);
        Assert.assertEquals("original", result.getCategories().get(0).getName());
    }

    @Test
    public void canParseTermNotFoundDocument() {
        final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
        final Reader reader = loadFile("/data/notfound.json");
        final Result result = parser.parse(reader);
        Assert.assertEquals("No translation was found for foo.", result.getNote());
    }

    @Test(expected = InvalidApiKeyException.class)
    public void invalidKeyDocumentLeadsToException() {
        final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
        final Reader reader = loadFile("/data/invalidkey.json");
        parser.parse(reader);
    }

    @Test(expected = RedirectException.class)
    public void redirectResponseLeadsToException() {
        final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
        final Reader reader = loadFile("/data/redirect.json");
        parser.parse(reader);
    }

    @Test
    public void redirectResponseContainsNewDict() {
        try {
            final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
            final Reader reader = loadFile("/data/redirect.json");
            parser.parse(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("iten", redirectException.getNewDict());
        }
    }

    @Test
    public void redirectResponseContainsNewWord() {
        try {
            final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
            final Reader reader = loadFile("/data/redirect.json");
            parser.parse(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("drago", redirectException.getNewWord());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseToParseANullReader() {
        final JsonParserCursedByLeChuck parser = new JsonParserCursedByLeChuck();
        parser.parse(null);
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = JsonParserCursedByLeChuckTest.class.getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
