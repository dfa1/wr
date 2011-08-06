package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.LocalJsonRepository;
import com.humaorie.wr.api.JSONParser;
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

public class MainTest {

    private Main main;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Before
    public void setup() {
        final LocalJsonRepository repository = new LocalJsonRepository();
        final JSONParser parser = new JSONParser();
        final WordReference wordReference = new WordReference(repository, parser);
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
        main.run("enit", "run");
        Assert.assertTrue(outContent.toString().contains("correre"));
    }

    @Test
    public void returnErrorOnInvalidDictionary() {
        int status = main.run("asdasd", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidDictionary() {
        main.run("foo", "grin");
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
        final Parser parser = new JSONParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final Main main = new Main(wordReference);
        int status = main.run("enit", "foo");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnInvalidApiKey() {
        final EnviromentApiKeyProvider apiKeyProvider = new EnviromentApiKeyProvider();
        final Repository repository = new FakeRepository(apiKeyProvider);
        final Parser parser = new JSONParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final Main main = new Main(wordReference);
        main.setErr(new PrintStream(errContent));
        main.run("enit", "dog");
        Assert.assertTrue(errContent.toString().contains("See http://www.wordreference.com/docs/APIregistration.aspx"));
    }
}
