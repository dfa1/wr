package com.humaorie.wr.cli;

import com.humaorie.wr.api.FileSystemRepository;
import com.humaorie.wr.api.JSONParser;
import com.humaorie.wr.api.WordReference;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private Main main;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    
    @Before
    public void setup() {
        FileSystemRepository repository = new FileSystemRepository();
        JSONParser parser = new JSONParser();
        WordReference wordReference = new WordReference(repository, parser);
        main = new Main(wordReference);
        outContent = new ByteArrayOutputStream();
        main.setOut(new PrintStream(outContent));
        errContent = new ByteArrayOutputStream();
        main.setErr(new PrintStream(errContent));
    }

    @Test
    public void returnErrorWhenCalledWithoutArguments() {
        int status = main.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithoutArguments() {
        main.run();
        Assert.assertEquals("java -jar wr.jar dict term\n", errContent.toString());
    }

    @Test
    public void returnErrorWhenCalledWithOneArgument() {
        int status = main.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithOneArgument() {
        main.run();
        Assert.assertEquals("java -jar wr.jar dict term\n", errContent.toString());
    }

    @Test
    public void returnZeroOnValidQueries() {
        int status = main.run("enfr", "grin");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showDefinitionOfAWord() {
        main.run("enit", "grin");

        Assert.assertTrue(outContent.toString().contains("ghignare"));
    }

    @Test
    public void returnErrorOnInvalidDictionary() {
        int status = main.run("asdasd", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void returnErrorOnInvalidApiKey() {
        int status = main.run("enit", "foo");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidApiKey() {
        main.run("enit", "dog");
        Assert.assertTrue(errContent.toString().contains("See http://www.wordreference.com/docs/APIregistration.aspx"));
    }
}
