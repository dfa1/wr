package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.WordReference;
import java.io.IOException;
import java.util.List;

public class Cli {

    private static final String WR = "http://www.wordreference.com";
    private final WordReference wordReference;
    private Appendable out = System.out;
    private Appendable err = System.err;

    public Cli(WordReference wordReference) {
        Preconditions.require(wordReference != null, "word reference cannot be null");
        this.wordReference = wordReference;
    }

    public int run(String... args) {
        if (args.length == 1 && args[0].equals("--version")) {
            print(out, "wrcli version %s%n", new VersionLoader().loadVersion());
            return 0;
        }
        if (args.length != 2) {
            print(err, "error: you must provide dict and word (e.g. 'enit run')%n");
            return 1;
        }
        try {
            doLookup(args);
            return 0;
        } catch (WordReferenceException ex) {
            print(err, "%s%n", ex.getMessage());
            return 1;
        }
    }

    private void doLookup(String... args) {
        final String dict = args[0];
        final String word = args[1];
        final Result result = wordReference.lookup(dict, word);
        printResult(result);
        printCopyright(dict, word);
    }

    private void printCopyright(String dict, String word) {
        print(out, "(C) WordReference.com%n");
        print(out, "Original link: %s/%s/%s%n", WR, dict, word);
    }

    private void printResult(Result result) {
        for (Category category : result.getCategories()) {
            printCategory(category);
        }
        final String note = result.getNote();
        if (!note.isEmpty()) {
            print(out, "note: %s%n", note);
        }
    }

    private void printCategory(Category category) {
        print(out, "category '%s':%n", category.getName());
        final List<Translation> translations = category.getTranslations();
        for (Translation translation : translations) {
            printTranslation(translation);
        }
    }

    private void printTranslation(Translation translation) {
        final Term originalTerm = translation.getOriginalTerm();
        print(out, " %s %s %s %s%n",
                originalTerm.getTerm(),
                originalTerm.getPos(),
                originalTerm.getSense(),
                originalTerm.getUsage());
        for (Term term : translation.getTranslations()) {
            print(out, "   %s %s %s %s%n",
                    term.getTerm(),
                    term.getPos(),
                    term.getSense(),
                    term.getUsage());
        }
        final String note = translation.getNote();
        if (!note.isEmpty()) {
            print(out, " note: %s%n", note);
        }
    }

    private static void print(Appendable app, String fmt, Object... args) {
        try {
            app.append(String.format(fmt, args));
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
