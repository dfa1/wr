package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.InvalidApiKeyException;
import com.humaorie.wr.api.NotFoundException;
import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.DefaultWordReference;
import java.io.PrintStream;
import java.util.List;

public class CommandLineClient {

    private final DefaultWordReference wordReference;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public CommandLineClient(DefaultWordReference wordReference) {
        this.wordReference = wordReference;
    }

    public int run(String... args) {
        if (args.length != 2) {
            err.println("error: you must provide dict and word (e.g. 'enit run')");
            return 1;
        }

        final String dict = args[0];
        final String word = args[1];

        try {
            final Result result = wordReference.lookup(dict, word);
            printResult(result);
            return 0;
        } catch (InvalidApiKeyException ex) {
            err.println(ex.getMessage());
            return 1;
        } catch (NotFoundException ex) {
            err.println(ex.getMessage());
            return 1;
        }
    }

    private void printResult(Result result) {
        for (Category category : result.getCategories()) {
            out.printf("category '%s':%n", category.getName());
            final List<Translation> translations = category.getTranslations();

            for (Translation translation : translations) {
                final Term originalTerm = translation.getOriginalTerm();
                out.printf(" %s %s %s %s%n",
                        originalTerm.getTerm(),
                        originalTerm.getPos(),
                        originalTerm.getSense(),
                        originalTerm.getUsage());

                for (Term term : translation.getTranslations()) {
                    out.printf("   %s %s %s %s%n",
                            term.getTerm(),
                            term.getPos(),
                            term.getSense(),
                            term.getUsage());
                }

                if (!translation.getNote().isEmpty()) {
                    out.printf(" note: %s%n", translation.getNote());
                }
            }
        }

        out.printf("Note: %s%n", result.getNote());
    }

    public void setErr(PrintStream err) {
        this.err = err;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
}
