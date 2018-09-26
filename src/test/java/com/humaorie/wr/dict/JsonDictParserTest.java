package com.humaorie.wr.dict;

import com.humaorie.wr.api.WordReferenceException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class JsonDictParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void refuseToParseANullReader() {
        final JsonDictParser parser = new JsonDictParser();
        parser.parse(null);
    }

    @Test
    public void canParseValidDocument() {
        final JsonDictParser parser = new JsonDictParser();
        final Reader reader = this.loadFile("iten-drago.json");
        final DictEntry result = parser.parse(reader);
        Assert.assertEquals("original", result.getCategories().get(0).getName());
    }

    @Test
    public void canParseTermNotFoundDocument() {
        final JsonDictParser parser = new JsonDictParser();
        final Reader reader = this.loadFile("notfound.json");
        final DictEntry result = parser.parse(reader);
        Assert.assertEquals("No translation was found for foo.", result.getNote());
    }

    @Test(expected = WordReferenceException.class)
    public void invalidKeyDocumentLeadsToException() {
        final JsonDictParser parser = new JsonDictParser();
        final Reader reader = this.loadFile("invalidkey.json");
        parser.parse(reader);
    }

    @Test
    public void redirectResponseContainsNewDict() {
        final JsonDictParser parser = new JsonDictParser();
        final Reader reader = this.loadFile("redirect.json");
        DictEntry parse = parser.parse(reader);
        Assert.assertTrue(parse.isRedirect());
        Assert.assertEquals("iten", parse.getNewDict());
        Assert.assertEquals("drago", parse.getNewWord());
    }

    @Test
    public void noTranslationLeadsToEmptyCategories() {
        final JsonDictParser parser = new JsonDictParser();
        final Reader reader = this.loadFile("notranslation.json");
        final DictEntry result = parser.parse(reader);
        Assert.assertTrue("categories must be empty", result.getCategories().isEmpty());
    }

    @Test(expected = WordReferenceException.class)
    public void refuseIllegalJson() {
        final JsonDictParser parser = new JsonDictParser();
        parser.parse(new StringReader("<xml>I cannot be parsed as JSON</xml>"));
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = JsonDictParserTest.class.getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
