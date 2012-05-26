package com.humaorie.wr.thesaurus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class JsonThesaurusParserTest {

    @Test
    public void canParseSampleFile() {
        final List<Sense> senses = new JsonThesaurusParser().parse(loadFile("dog.json"));
        Assert.assertNotNull(senses);
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = this.getClass().getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
