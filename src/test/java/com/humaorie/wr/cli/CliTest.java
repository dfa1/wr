package com.humaorie.wr.cli;

import com.humaorie.wr.dict.Category;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.dict.Result;
import com.humaorie.wr.dict.Dict;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithoutArguments() {
        final CapturingAppendable err = new CapturingAppendable();
        final Cli cli = new Cli(new ConstantWordReference(null));
        cli.setOut(new CapturingAppendable());
        cli.setErr(err);
        cli.run();
        Assert.assertEquals("error: you must provide dict and word (e.g. 'enit run')\n", err.getCapture());
    }

    @Test
    public void returnErrorWhenCalledWithOneArgument() {
        final Cli cli = new Cli(new ConstantWordReference(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithOneArgument() {
        final CapturingAppendable err = new CapturingAppendable();
        final Cli cli = new Cli(new ConstantWordReference(null));
        cli.setErr(err);
        cli.setOut(new CapturingAppendable());
        cli.run("enit");
        Assert.assertEquals("error: you must provide dict and word (e.g. 'enit run')\n", err.getCapture());
    }

    @Test
    public void returnSuccessWhenShowDefinitionOfAWord() {
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enfr", "mist");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showDefinitionOfAWord() {
        final CapturingAppendable out = new CapturingAppendable();
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("enit", "run");
        Assert.assertTrue(out.getCapture().contains(result.getNote()));
    }

    @Test
    public void showCopyrightMessage() {
        final CapturingAppendable out = new CapturingAppendable();
        final Result result = Result.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli(new ConstantWordReference(result));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("enit", "run");
        Assert.assertTrue(out.getCapture().contains("(C) WordReference.com"));
    }

    @Test
    public void returnErrorOnWordReferenceException() {
        final Dict wordReference = new FailingWordReference(new WordReferenceException("a message"));
        final Cli cli = new Cli(wordReference);
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enen", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnWordReferenceException() {
        final CapturingAppendable err = new CapturingAppendable();
        final WordReferenceException exception = new WordReferenceException("a message");
        final Dict wordReference = new FailingWordReference(exception);
        final Cli cli = new Cli(wordReference);
        cli.setErr(err);
        cli.setOut(new CapturingAppendable());
        cli.run("enen", "grin");
        final String expected = String.format("error: %s%n", exception.getMessage());
        Assert.assertEquals(expected, err.getCapture());
    }

    @Test
    public void showVersion() {
        final CapturingAppendable out = new CapturingAppendable();
        final Dict wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("--version");
        Assert.assertTrue(out.getCapture().startsWith("wrcli version"));
    }

    @Test
    public void returnSuccessOnVersion() {
        final Dict wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("--version");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showHelp() {
        final CapturingAppendable out = new CapturingAppendable();
        final Dict wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("--help");
        Assert.assertTrue(out.getCapture().contains("usage:"));
    }

    @Test
    public void returnSuccessOnHelp() {
        final Dict wordReference = new ConstantWordReference(null);
        final Cli cli = new Cli(wordReference);
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("--help");
        Assert.assertEquals(0, status);
    }

    public static class CapturingAppendable implements Appendable {

        private final ByteArrayOutputStream capture = new ByteArrayOutputStream();

        private CapturingAppendable capture(CharSequence csq, int start, int end) {
            for (int i = start; i < end; i++) {
                capture.write(csq.charAt(i));
            }
            return this;
        }

        @Override
        public Appendable append(CharSequence csq) throws IOException {
            return capture(csq, 0, csq.length());
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) throws IOException {
            return capture(csq, start, end);
        }

        @Override
        public Appendable append(char c) throws IOException {
            capture.write(c);
            return this;
        }

        public String getCapture() {
            return capture.toString();
        }
    }
}
