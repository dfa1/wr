package com.humaorie.wr.cli;

import com.humaorie.wr.api.Category;
import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.Term;
import com.humaorie.wr.api.Translation;
import com.humaorie.wr.api.WordReference;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("java -jar wr.jar dict term");
            System.exit(1);
        }
        
        String dict = args[0];
        String term = args[1];
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.setApiKeyProvider(new EnviromentApiKeyProvider());
        WordReference wordReference = new WordReference(repository);
        List<Category> categories = wordReference.lookup(dict, term);
        
        for (Category category : categories) {
            List<Translation> translations = category.getTranslations();
            for (Translation translation : translations) {
                List<Term> translations1 = translation.getTranslations();
                for (Term term1 : translations1) {
                    System.out.printf("%s %s %s %s\n", term1.getTerm(), term1.getPos(), term1.getSense(), term1.getUsage());
                }
            }
        }
    }
}
