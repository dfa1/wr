package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.CostantApiKeyProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    private ApiKeyProvider apiKeyProvider = new CostantApiKeyProvider("1");

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
        int status = main.run("enfr", "grind");
        Assert.assertEquals(0, status);
    }

    @Test
    public void canOutputAValidWordWithinAValidDictionary() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream capturing = new PrintStream(outContent);
        Main main = new Main(apiKeyProvider);
        main.setOut(capturing);
        main.run("enit", "grin");
        
        Assert.assertTrue(capturing.toString().contains("rictus"));
    }

    @Test
    public void cannotQueryAnEmptyWord() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enit", "");
        Assert.assertEquals(1, status);
    }

    @Test
    public void cannotQueryAnInvalidDictionary() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("asdasd", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void canHandleInvalidKey() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enfr", "foo");
        Assert.assertEquals(1, status);
    }
}
