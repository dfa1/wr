package com.humaorie.wr.cli;

import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.WordReference;
import java.io.PrintStream;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EnviromentApiKeyProvider enviromentApiKeyProvider = null;

        try {
            enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

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
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.setApiKeyProvider(apiKeyProvider);
        WordReference wordReference = new WordReference(repository);
        List<Category> categories = wordReference.lookup(dict, term);

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

    public void setErr(PrintStream err) {
        this.err = err;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
}
