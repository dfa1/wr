package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.LocalJsonRepository;
import com.humaorie.wr.api.BoringJsonParser;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.WordReference;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandLineClientTest {

    private CommandLineClient cli;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Before
    public void setup() {
        final LocalJsonRepository repository = new LocalJsonRepository();
        final BoringJsonParser parser = new BoringJsonParser();
        final WordReference wordReference = new WordReference(repository, parser);
        cli = new CommandLineClient(wordReference);
        outContent = new ByteArrayOutputStream();
        cli.setOut(new PrintStream(outContent));
        errContent = new ByteArrayOutputStream();
        cli.setErr(new PrintStream(errContent));
    }

    @Test
    public void returnErrorWhenCalledWithoutArguments() {
        final int status = cli.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithoutArguments() {
        cli.run();
        Assert.assertEquals("java -jar wr.jar dict term\n", errContent.toString());
    }

    @Test
    public void returnErrorWhenCalledWithOneArgument() {
        final int status = cli.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithOneArgument() {
        cli.run();
        Assert.assertEquals("java -jar wr.jar dict term\n", errContent.toString());
    }

    @Test
    public void returnZeroOnValidQueries() {
        final int status = cli.run("enfr", "grin");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showDefinitionOfAWord() {
        cli.run("enit", "run");
        Assert.assertTrue(outContent.toString().contains("correre"));
    }

    @Test
    public void returnErrorOnInvalidDictionary() {
        final int status = cli.run("asdasd", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidDictionary() {
        cli.run("foo", "grin");
        Assert.assertEquals("dictionary 'foo' not found\n", errContent.toString());
    }

    private static class FakeRepository implements Repository {

        private final ApiKeyProvider apiKeyProvider;

        public FakeRepository(ApiKeyProvider apiKeyProvider) {
            this.apiKeyProvider = apiKeyProvider;
        }

        @Override
        public Reader lookup(String dict, String word) {
            final String json = String.format("{ 'api key'; '%s' }", apiKeyProvider.provideKey());
            return new StringReader(json);
        }
    }

    @Test
    public void returnErrorWhenApiKeyIsNotFound() {
        final EnviromentApiKeyProvider apiKeyProvider = new EnviromentApiKeyProvider();
        final Repository repository = new FakeRepository(apiKeyProvider);
        final Parser parser = new BoringJsonParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        final int status = cli.run("enit", "foo");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidApiKey() {
        final EnviromentApiKeyProvider apiKeyProvider = new EnviromentApiKeyProvider();
        final Repository repository = new FakeRepository(apiKeyProvider);
        final Parser parser = new BoringJsonParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        cli.setErr(new PrintStream(errContent));
        cli.run("enit", "dog");
        Assert.assertTrue(errContent.toString().contains("See http://www.wordreference.com/docs/APIregistration.aspx"));
    }
    
    @Test
    public void canShowWholeDefinitions() {
        final Repository repository = new LocalJsonRepository();
        final Parser parser = new BoringJsonParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        cli.run("enit", "run");
    }
}
