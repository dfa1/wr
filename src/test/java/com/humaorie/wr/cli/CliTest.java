package com.humaorie.wr.cli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.dict.Category;
import com.humaorie.wr.dict.ConstantDict;
import com.humaorie.wr.dict.Dict;
import com.humaorie.wr.dict.DictEntry;
import com.humaorie.wr.dict.FailingDict;
import com.humaorie.wr.thesaurus.ConstantThesaurus;

public class CliTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCliWithNullVersion() {
        new Cli(null, new ConstantDict(null), new ConstantThesaurus(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateCliWithNullDict() {
        new Cli("1.0", null, new ConstantThesaurus(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateCliWithNullThesaurus() {
        new Cli("1.0", new ConstantDict(null), null);
    }

    @Test
    public void returnErrorWhenCalledWithoutArguments() throws IOException {
        final Cli cli = new Cli("1.0", new ConstantDict(null), new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run();
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithoutArguments() throws IOException {
        final CapturingAppendable err = new CapturingAppendable();
        final Cli cli = new Cli("1.0", new ConstantDict(null), new ConstantThesaurus(null));
        cli.setOut(new CapturingAppendable());
        cli.setErr(err);
        cli.run();
        Assert.assertEquals(String.format("error: you must provide dict and word (e.g. 'enit run')%n"), err.getCapture());
    }

    @Test
    public void returnErrorWhenCalledWithOneArgument() throws IOException {
        final Cli cli = new Cli("1.0", new ConstantDict(null), new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enit");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorWhenCalledWithOneArgument() throws IOException {
        final CapturingAppendable err = new CapturingAppendable();
        final Cli cli = new Cli("1.0", new ConstantDict(null), new ConstantThesaurus(null));
        cli.setErr(err);
        cli.setOut(new CapturingAppendable());
        cli.run("enit");
        Assert.assertEquals(String.format("error: you must provide dict and word (e.g. 'enit run')%n"), err.getCapture());
    }

    @Test
    public void returnSuccessWhenShowDefinitionOfAWord() throws IOException {
        final DictEntry result = DictEntry.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli("1.0", new ConstantDict(result), new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enfr", "mist");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showDefinitionOfAWord() throws IOException {
        final CapturingAppendable out = new CapturingAppendable();
        final DictEntry result = DictEntry.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli("1.0", new ConstantDict(result), new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("enit", "run");
        Assert.assertTrue(out.getCapture().contains(result.getNote()));
    }

    @Test
    public void showCopyrightMessage() throws IOException {
        final CapturingAppendable out = new CapturingAppendable();
        final DictEntry entry = DictEntry.create(new ArrayList<Category>(), "random note about mist");
        final Cli cli = new Cli("1.0", new ConstantDict(entry), new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("enit", "run");
        Assert.assertTrue(out.getCapture().contains("(C) WordReference.com"));
    }

    @Test
    public void returnErrorOnWordReferenceException() throws IOException {
        final Dict dict = new FailingDict(new WordReferenceException("a message"));
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("enen", "grin");
        Assert.assertEquals(1, status);
    }

    @Test
    public void showErrorOnWordReferenceException() throws IOException {
        final CapturingAppendable err = new CapturingAppendable();
        final WordReferenceException exception = new WordReferenceException("a message");
        final Dict dict = new FailingDict(exception);
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(err);
        cli.setOut(new CapturingAppendable());
        cli.run("enen", "grin");
        final String expected = String.format("error: %s%n", exception.getMessage());
        Assert.assertEquals(expected, err.getCapture());
    }

    @Test
    public void showVersion() throws IOException {
        final CapturingAppendable out = new CapturingAppendable();
        final Dict dict = new ConstantDict(null);
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("--version");
        Assert.assertTrue(out.getCapture().startsWith("wrcli version"));
    }

    @Test
    public void returnSuccessOnVersion() throws IOException {
        final Dict dict = new ConstantDict(null);
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("--version");
        Assert.assertEquals(0, status);
    }

    @Test
    public void showHelp() throws IOException {
        final CapturingAppendable out = new CapturingAppendable();
        final Dict dict = new ConstantDict(null);
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(out);
        cli.run("--help");
        Assert.assertTrue(out.getCapture().contains("usage:"));
    }

    @Test
    public void returnSuccessOnHelp() throws IOException {
        final Dict dict = new ConstantDict(null);
        final Cli cli = new Cli("1.0", dict, new ConstantThesaurus(null));
        cli.setErr(new CapturingAppendable());
        cli.setOut(new CapturingAppendable());
        final int status = cli.run("--help");
        Assert.assertEquals(0, status);
    }

    public static class CapturingAppendable implements Appendable {

        private final ByteArrayOutputStream capture = new ByteArrayOutputStream();

        private CapturingAppendable capture(CharSequence csq, int start, int end) {
            for (int i = start; i < end; i++) {
                this.capture.write(csq.charAt(i));
            }
            return this;
        }

        @Override
        public Appendable append(CharSequence csq) throws IOException {
            return this.capture(csq, 0, csq.length());
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            return this.capture(csq, start, end);
        }

        @Override
        public Appendable append(char c) {
            this.capture.write(c);
            return this;
        }

        public String getCapture() {
            return this.capture.toString();
        }
    }
}
