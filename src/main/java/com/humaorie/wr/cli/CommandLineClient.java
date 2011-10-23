package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.WordReference;
import java.io.PrintStream;
import java.util.List;

public class CommandLineClient {

    private final String WR = "http://www.wordreference.com";
    private final WordReference wordReference;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public CommandLineClient(WordReference wordReference) {
        Preconditions.require(wordReference != null, "word reference cannot be null");
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
	    printCopyright(dict, word);
            return 0;
        } catch (WordReferenceException ex) {
            err.println(ex.getMessage());
            return 1;
        } 
    }
    
    private void printCopyright(String dict, String word) {
	out.printf("© WordReference.com%n");
	out.printf("Original link: %s/%s/%s%n", WR, dict, word);
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
