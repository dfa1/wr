package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.InvalidApiKeyException;
import com.humaorie.wr.api.NotFoundException;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.WordReference;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        Main main = new Main(enviromentApiKeyProvider);
        int status = main.run(args);
        System.exit(status);
    }

    private final ApiKeyProvider apiKeyProvider;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public Main(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    public int run(String... args) {
        if (args.length != 2) {
            out.println("java -jar wr.jar dict term");
            return 1;
        }

        String dict = args[0];
        String term = args[1];
        List<Category> categories = lookup(dict, term);

        if (categories == null) { // XXX: smell
            return 1;
        }

        for (Category category : categories) {
            List<Translation> translations = category.getTranslations();
            for (Translation translation : translations) {
                List<Term> translations1 = translation.getTranslations();
                for (Term term1 : translations1) {
                    out.printf("%s %s %s %s\n", term1.getTerm(), term1.getPos(), term1.getSense(), term1.getUsage());
                }
            }
        }

        return 0;
    }

    private List<Category> lookup(String dict, String term) {
        try {
            InternetJsonRepository repository = new InternetJsonRepository();
            repository.setApiKeyProvider(apiKeyProvider);
            WordReference wordReference = new WordReference(repository);
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
}
