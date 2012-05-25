package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.WordReference;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class CliTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateCliWithNullWordReference() {
        new Cli(null);
    }

    @Test
    public void returnErrorWhenCalledWithoutArguments() {
        final Cli cli = new Cli(new ConstantWordReference(null));
        final int status = cli.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithoutArguments() {
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final Cli cli = new Cli(new ConstantWordReference(null));
        cli.setErr(new PrintStream(errContent));
        cli.run();
        Assert.assertEquals("error: you must provide dict and word (e.g. 'enit run')\n", errContent.toString());
    }

    @Test
    public void returnErrorWhenCalledWithOneArgument() {
        final Cli cli = new Cli(new ConstantWordReference(null));
        final int status = cli.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithOneArgument() {
        final Cli cli = new Cli(new ConstantWordReference(null));
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        cli.setErr(new PrintStream(errContent));
        cli.run("enit");
        Assert.assertEquals("error: you must provide dict and word (e.g. 'enit run')\n", errContent.toString());
    }

    @Test
    public void returnSuccessOnValidQueries() {
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        final int status = cli.run("enfr", "mist");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showDefinitionOfAWord() {
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        cli.setOut(new PrintStream(outContent));
        cli.run("enit", "run");
        Assert.assertTrue(outContent.toString().contains(result.getNote()));
    }

    @Test
    public void showCopyrightMessage() {
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        cli.setOut(new PrintStream(outContent));
        cli.run("enit", "run");
        Assert.assertTrue(outContent.toString().contains("© WordReference.com"));
    }

    @Test
    public void returnErrorOnDictionaryNotFound() {
        final WordReferenceException exception = new WordReferenceException("invalid dictonary");
        final WordReference wordReference = new FailingWordReference(exception);
        final Cli cli = new Cli(wordReference);
        final int status = cli.run("enen", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnDictionaryNotFound() {
        final WordReferenceException exception = new WordReferenceException("invalid dictonary");
        final WordReference wordReference = new FailingWordReference(exception);
        final Cli cli = new Cli(wordReference);
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        cli.setErr(new PrintStream(errContent));
        cli.run("enen", "grin");
        Assert.assertEquals(exception.getMessage() + "\n", errContent.toString());
    }

    @Test
    public void returnErrorWhenApiKeyIsNotFound() {
        final WordReferenceException exception = new WordReferenceException("invalid api key");
        final WordReference wordReference = new FailingWordReference(exception);
        final Cli cli = new Cli(wordReference);
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        cli.setErr(new PrintStream(errContent));
        final int status = cli.run("enit", "foo");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidApiKey() {
        final WordReferenceException exception = new WordReferenceException("invalid api key");
        final WordReference wordReference = new FailingWordReference(exception);
        final Cli cli = new Cli(wordReference);
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        cli.setErr(new PrintStream(errContent));
        cli.run("enit", "dog");
        Assert.assertTrue(errContent.toString().contains(exception.getMessage()));
    }

    @Test
    public void showVersion() {
        final WordReference wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        cli.setOut(new PrintStream(outContent));
        cli.run("--version");
        Assert.assertTrue(outContent.toString().startsWith("wrcli version"));
    }

    @Test
    public void returnSuccessOnVersion() {
        final WordReference wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        final int status = cli.run("--version");
        Assert.assertEquals(0, status);
    }
}
