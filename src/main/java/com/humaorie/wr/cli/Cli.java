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
        try {
            return tryRun(args);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int tryRun(String... args) throws IOException {
        if (args.length == 1 && args[0].equals("--version")) {
            out.append(String.format("wrcli version %s%n", new VersionLoader().loadVersion()));
            return 0;
        }

        if (args.length != 2) {
            err.append(String.format("error: you must provide dict and word (e.g. 'enit run')%n"));
            return 1;
        }

        final String dict = args[0];
        final String word = args[1];

        try {
            final Result result = wordReference.lookup(dict, word);
            printResult(result);
            printCopyright(dict, word);
            return 0;
        } catch (WordReferenceException ex) {
            err.append(String.format("%s%n", ex.getMessage()));
            return 1;
        }
    }

    private void printCopyright(String dict, String word) throws IOException {
        out.append(String.format("(C) WordReference.com%n"));
        out.append(String.format("Original link: %s/%s/%s%n", WR, dict, word));
    }

    private void printResult(Result result) throws IOException {
        for (Category category : result.getCategories()) {
            out.append(String.format("category '%s':%n", category.getName()));
            final List<Translation> translations = category.getTranslations();
            for (Translation translation : translations) {
                final Term originalTerm = translation.getOriginalTerm();
                out.append(String.format(" %s %s %s %s%n",
                        originalTerm.getTerm(),
                        originalTerm.getPos(),
                        originalTerm.getSense(),
                        originalTerm.getUsage()));
                for (Term term : translation.getTranslations()) {
                    out.append(String.format("   %s %s %s %s%n",
                            term.getTerm(),
                            term.getPos(),
                            term.getSense(),
                            term.getUsage()));
                }
                if (!translation.getNote().isEmpty()) {
                    out.append(String.format(" note: %s%n", translation.getNote()));
                }
            }
        }

        out.append(String.format("Note: %s%n", result.getNote()));
    }

    public void setErr(Appendable err) {
        this.err = err;
    }

    public void setOut(Appendable out) {
        this.out = out;
    }
}
