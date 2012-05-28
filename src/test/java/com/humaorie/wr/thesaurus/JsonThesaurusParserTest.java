package com.humaorie.wr.thesaurus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class JsonThesaurusParserTest {

    @Test
    public void canParseRealDocument() {
        final ThesaurusEntry entry = new JsonThesaurusParser().parse(loadFile("dog.json"));
        Assert.assertNotNull(entry);
    }

    @Test
    public void canParseNotFoundDocument() {
        final ThesaurusEntry entry = new JsonThesaurusParser().parse(loadFile("notfound.json"));
        Assert.assertTrue(entry.getNote().contains("No translation was found for"));
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = this.getClass().getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
