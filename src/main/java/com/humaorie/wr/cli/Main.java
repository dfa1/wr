package com.humaorie.wr.cli;

import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.JSONParser;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.WordReference;

public class Main {

    public static void main(String[] args) {
        final EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository();
        repository.setApiKeyProvider(enviromentApiKeyProvider);
        final Parser parser = new JSONParser();
        final WordReference wordReference = new WordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        final int status = cli.run(args);
        System.exit(status);
    }
}
