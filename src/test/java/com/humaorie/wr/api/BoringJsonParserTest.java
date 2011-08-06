package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class BoringJsonParserTest {

    private Reader loadFile(String filename) {
        final InputStream inputStream = BoringJsonParserTest.class.getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }

    @Test
    public void canParseValidDocument() {
        final BoringJsonParser parser = new BoringJsonParser();
        final Reader reader = loadFile("/data/iten-drago.json");
        Result result = parser.parseDefinition(reader);
        Assert.assertEquals("original", result.getCategory().get(0).getName());
    }

    @Test
    public void canParseTermNotFoundDocument() {
        final BoringJsonParser parser = new BoringJsonParser();
        final Reader reader = loadFile("/data/notfound.json");
        final Result result = parser.parseDefinition(reader);
        Assert.assertEquals("No translation was found for foo.", result.getNote());
    }

    @Test(expected = InvalidApiKeyException.class)
    public void invalidKeyDocumentLeadsToException() {
        final Reader reader = loadFile("/data/invalidkey.json");
        final BoringJsonParser parser = new BoringJsonParser();
        parser.parseDefinition(reader);
    }

    @Test(expected = RedirectException.class)
    public void redirectResponseLeadsToException() {
        final Reader reader = loadFile("/data/redirect.json");
        final BoringJsonParser parser = new BoringJsonParser();
        parser.parseDefinition(reader);
    }

    @Test
    public void redirectResponseContainsNewDict() {
        try {
            final Reader reader = loadFile("/data/redirect.json");
            final BoringJsonParser parser = new BoringJsonParser();
            parser.parseDefinition(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("iten", redirectException.getNewDict());
        }
    }

    @Test
    public void redirectResponseContainsNewWord() {
        try {
            final Reader reader = loadFile("/data/redirect.json");
            final BoringJsonParser parser = new BoringJsonParser();
            parser.parseDefinition(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("drago", redirectException.getNewWord());
        }
    }
}
