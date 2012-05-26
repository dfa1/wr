package com.humaorie.wr.cli;

import com.humaorie.wr.dict.Category;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.dict.Result;
import com.humaorie.wr.dict.Term;
import com.humaorie.wr.dict.Translation;
import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.dict.Dict;
import java.io.IOException;
import java.util.List;

public class Cli {

    private static final String WR = "http://www.wordreference.com";
    private final Dict dict;
    private Appendable out = System.out;
    private Appendable err = System.err;

    public Cli(Dict dict) {
        Preconditions.require(dict != null, "word reference cannot be null");
        this.dict = dict;
    }

    public int run(String... args) {
        if (args.length == 1 && args[0].equals("--version")) {
            println(out, "wrcli version %s", new VersionLoader().loadVersion());
            return 0;
        }
        if (args.length == 1 && args[0].equals("--help")) {
            println(out, "wrcli - word reference command line interface");
            println(out, "by Davide Angelocola <davide.angelocola@gmail.com>");
            println(out, "usage: wrcli dict word");
            return 0;
        }
        if (args.length != 2) {
            println(err, "error: you must provide dict and word (e.g. 'enit run')");
            return 1;
        }
        try {
            doLookup(args);
            return 0;
        } catch (WordReferenceException ex) {
            println(err, "error: %s", ex.getMessage());
            return 1;
        }
    }

    private void doLookup(String... args) {
        final String dict = args[0];
        final String word = args[1];
        final Result result = this.dict.lookup(dict, word);
        printResult(result);
        printCopyright(dict, word);
    }

    private void printCopyright(String dict, String word) {
        println(out, "(C) WordReference.com");
        println(out, "Original link: %s/%s/%s", WR, dict, word);
    }

    private void printResult(Result result) {
        for (Category category : result.getCategories()) {
            printCategory(category);
        }
        final String note = result.getNote();
        if (!note.isEmpty()) {
            println(out, "note: %s", note);
        }
    }

    private void printCategory(Category category) {
        println(out, "category '%s':", category.getName());
        final List<Translation> translations = category.getTranslations();
        for (Translation translation : translations) {
            printTranslation(translation);
        }
    }

    private void printTranslation(Translation translation) {
        final Term originalTerm = translation.getOriginalTerm();
        println(out, " %s %s %s %s",
                originalTerm.getTerm(),
                originalTerm.getPos(),
                originalTerm.getSense(),
                originalTerm.getUsage());
        for (Term term : translation.getTranslations()) {
            println(out, "   %s %s %s %s",
                    term.getTerm(),
                    term.getPos(),
                    term.getSense(),
                    term.getUsage());
        }
        final String note = translation.getNote();
        if (!note.isEmpty()) {
            println(out, " note: %s", note);
        }
    }

    private static void println(Appendable app, String fmt, Object... args) {
        try {
            app.append(String.format(fmt + "%n", args));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setErr(Appendable err) {
        this.err = err;
    }

    public void setOut(Appendable out) {
        this.out = out;
    }
}
