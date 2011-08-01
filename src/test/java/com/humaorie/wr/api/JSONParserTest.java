package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class JSONParserTest {

    // TODO: this is an integration test
    @Test
    public void canParseDrago() {
        FileSystemRepository fileSystemRepository = new FileSystemRepository();
        Reader reader = fileSystemRepository.lookup("iten", "drago");
        JSONParser parser = new JSONParser();
        List<Category> categories = parser.parseDefinition(reader);
        Assert.assertEquals("original", categories.get(0).getName());
    }

    @Test
    public void termNotFoundLeadsToException() {
        try {
            InputStream inputStream = FileSystemRepository.class.getResourceAsStream("/data/notfound.json");
            Reader reader = new InputStreamReader(inputStream);
            JSONParser parser = new JSONParser();
            parser.parseDefinition(reader);
            Assert.fail("expected a TermNotFoundException");
        } catch (TermNotFoundException ex) {
            Assert.assertEquals("No translation was found for foo.", ex.getMessage());
        }
    }
}
