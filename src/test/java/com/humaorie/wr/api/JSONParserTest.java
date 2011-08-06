package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class JSONParserTest {

    @Test
    public void canParseValidDocument() {
        InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/iten-drago.json");
        Reader reader = new InputStreamReader(inputStream);
        JSONParser parser = new JSONParser();
        Result result = parser.parseDefinition(reader);
        Assert.assertEquals("original", result.getCategory().get(0).getName());
    }

    @Test
    public void canParseTermNotFoundDocument() {
        InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/notfound.json");
        Reader reader = new InputStreamReader(inputStream);
        JSONParser parser = new JSONParser();
        Result result = parser.parseDefinition(reader);
        Assert.assertEquals("No translation was found for foo.", result.getNote());
    }

    @Test(expected = InvalidApiKeyException.class)
    public void canParseInvalidKeyDocument() { // TODO: don't throw exception here
        InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/invalidkey.json");
        Reader reader = new InputStreamReader(inputStream);
        JSONParser parser = new JSONParser();
        parser.parseDefinition(reader);
    }

    @Test(expected = RedirectException.class)
    public void redirectResponseLeadsToException() {
        InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/redirect.json");
        Reader reader = new InputStreamReader(inputStream);
        JSONParser parser = new JSONParser();
        parser.parseDefinition(reader);
    }

    @Test
    public void redirectResponseContainsNewDict() {
        try {
            InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/redirect.json");
            Reader reader = new InputStreamReader(inputStream);
            JSONParser parser = new JSONParser();
            parser.parseDefinition(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("iten", redirectException.getNewDict());
        }
    }

    @Test
    public void redirectResponseContainsNewWord() {
        try {
            InputStream inputStream = JSONParserTest.class.getResourceAsStream("/data/redirect.json");
            Reader reader = new InputStreamReader(inputStream);
            JSONParser parser = new JSONParser();
            parser.parseDefinition(reader);
            Assert.fail("expected a RedirectException");
        } catch (RedirectException redirectException) {
            Assert.assertEquals("drago", redirectException.getNewWord());
        }
    }
}
