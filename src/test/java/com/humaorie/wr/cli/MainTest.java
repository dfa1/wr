package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.CostantApiKeyProvider;
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
        int status = main.run("enit", "dog");
        Assert.assertEquals(0, status);
    }

    @Test
    public void cannotQueryAnEmptyWord() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enit", "");
        Assert.assertEquals(0, status);
    }

    @Test
    public void cannotQueryANonExistentWord() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("enit", "asdasdasdhjgasdkjahsdkash");
        Assert.assertEquals(0, status);
    }

    @Test
    public void cannotQueryAnInvalidDictionary() {
        Main main = new Main(apiKeyProvider);
        int status = main.run("asdasd", "foo");
        Assert.assertEquals(0, status);
    }
}
