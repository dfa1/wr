package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.InvalidApiKeyException;
import com.humaorie.wr.api.JSONParser;
import com.humaorie.wr.api.NotFoundException;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.Result;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.WordReference;
import java.io.PrintStream;
import java.util.List;

public class Main {

    private final WordReference wordReference;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public Main(WordReference wordReference) {
        this.wordReference = wordReference;
    }

    public int run(String... args) {
        if (args.length != 2) {
            err.println("java -jar wr.jar dict term");
            return 1;
        }

        String dict = args[0];
        String term = args[1];
        Result result = lookup(dict, term);
        
        if (result == null) { // XXX: smell
            return 1;
        }
        
        for (Category category : result.getCategory()) {
            List<Translation> translations = category.getTranslations();
            for (Translation translation : translations) {
                List<Term> translations1 = translation.getTranslations();
                for (Term term1 : translations1) {
                    out.printf("%s %s %s %s%n", term1.getTerm(), term1.getPos(), term1.getSense(), term1.getUsage());
                }
            }
        }

        out.printf("Note: %s%n", result.getNote());
        return 0;
    }

    private Result lookup(String dict, String term) {
        try {
            return wordReference.lookup(dict, term);
        } catch (InvalidApiKeyException ex) {
            err.println(ex.getMessage());
            return null;
        } catch (NotFoundException ex) {
            err.println(ex.getMessage());
            return null;
        }
    }

    public void setErr(PrintStream err) {
        this.err = err;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public static void main(String[] args) {
        EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.setApiKeyProvider(enviromentApiKeyProvider);
        Parser parser = new JSONParser();
        WordReference wordReference = new WordReference(repository, parser);
        Main main = new Main(wordReference);
        int status = main.run(args);
        System.exit(status);
    }
}
