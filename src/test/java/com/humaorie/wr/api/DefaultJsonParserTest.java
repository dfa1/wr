package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class DefaultJsonParserTest {

    @Test
    public void canParseValidDocument() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        final Reader reader = loadFile("iten-drago.json");
        final Result result = parser.parse(reader);
        Assert.assertEquals("original", result.getCategories().get(0).getName());
    }

    @Test
    public void canParseTermNotFoundDocument() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        final Reader reader = loadFile("notfound.json");
        final Result result = parser.parse(reader);
        Assert.assertEquals("No translation was found for foo.", result.getNote());
    }

    @Test(expected = WordReferenceException.class)
    public void invalidKeyDocumentLeadsToException() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        final Reader reader = loadFile("invalidkey.json");
        parser.parse(reader);
    }

    @Test(expected = RedirectException.class)
    public void redirectResponseLeadsToException() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        final Reader reader = loadFile("redirect.json");
        parser.parse(reader);
    }

    @Test
    public void redirectResponseContainsNewDict() {
        try {
            final DefaultJsonParser parser = new DefaultJsonParser();
            final Reader reader = loadFile("redirect.json");
            parser.parse(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("iten", redirectException.getNewDict());
        }
    }

    @Test
    public void redirectResponseContainsNewWord() {
        try {
            final DefaultJsonParser parser = new DefaultJsonParser();
            final Reader reader = loadFile("redirect.json");
            parser.parse(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("drago", redirectException.getNewWord());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseToParseANullReader() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        parser.parse(null);
    }

    @Test
    public void noTranslationLeadsToEmptyCategories() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        final Reader reader = loadFile("notranslation.json");
        final Result result = parser.parse(reader);
        Assert.assertTrue("categories must be empty", result.getCategories().isEmpty());
    }

    @Test(expected = WordReferenceException.class)
    public void refuseIllegalJson() {
        final DefaultJsonParser parser = new DefaultJsonParser();
        parser.parse(new StringReader("<xml>I cannot be parsed as JSON</xml>"));
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = DefaultJsonParserTest.class.getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
