package com.humaorie.wr.thesaurus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.junit.Test;

public class JsonThesaurusParserTest {

    @Test
    public void canParseSampleFile() {
        final List<Sense> senses = new JsonThesaurusParser().parse(loadFile("dog.json"));
        for (Sense sense : senses) {
            String text = sense.getText();
            System.out.println("sense " + text);
            List<Synonym> synonyms = sense.getSynonyms();
            for (Synonym synonym : synonyms) {
                System.out.printf("context '%s' -> %s%n", synonym.getContext(), synonym.getName());
            }
        }
    }

    private Reader loadFile(String filename) {
        final InputStream inputStream = this.getClass().getResourceAsStream(filename);
        return new InputStreamReader(inputStream);
    }
}
