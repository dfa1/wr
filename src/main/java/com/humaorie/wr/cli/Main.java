package com.humaorie.wr.cli;

import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.JSONParser;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.WordReference;

public class Main {

    public static void main(String[] args) {
        EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        InternetJsonRepository repository = new InternetJsonRepository();
        repository.setApiKeyProvider(enviromentApiKeyProvider);
        Parser parser = new JSONParser();
        WordReference wordReference = new WordReference(repository, parser);
        CommandLineClient main = new CommandLineClient(wordReference);
        int status = main.run(args);
        System.exit(status);
    }
}
