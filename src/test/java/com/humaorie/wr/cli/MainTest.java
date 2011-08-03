package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.CostantApiKeyProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    private ApiKeyProvider apiKeyProvider = new CostantApiKeyProvider("2");

    @Test
    public void cannotAcceptZeroArguments() {
        Main main = new Main(apiKeyProvider);
        int status = main.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void cannotAcceptOneArgument() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void canQueryAValidWordWithinAValidDictionary() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enfr", "grin");
        Assert.assertEquals(0, status);
    }

    @Test
    public void canOutputAValidWordWithinAValidDictionary() {
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream capturing = new PrintStream(content);
        Main main = new Main(apiKeyProvider);
        main.setOut(capturing);
        main.run("enit", "grin");

        Assert.assertTrue(content.toString().contains("ghignare"));
    }

    @Test
    public void cannotQueryAnEmptyWord() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enit", "");
        Assert.assertEquals(1, status);
    }

    @Test
    public void returnErrorOnInvalidDictionary() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("asdasd", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void returnErrorOnInvalidApiKey() {
        // "1" is a DEMO api key and should not be used
        final CostantApiKeyProvider invalidApiKey = new CostantApiKeyProvider("1");
        Main main = new Main(invalidApiKey); // 
        int status = main.run("enit", "foo");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidApiKey() {
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream capturing = new PrintStream(content);
        Main main = new Main(apiKeyProvider);
        main.setErr(capturing);
        main.run("enit", "dog");
        System.out.println("error "+ content.toString());
        Assert.assertTrue(content.toString().contains("See http://www.wordreference.com/docs/APIregistration.aspx"));
    }
}
