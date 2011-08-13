package com.humaorie.wr.cli;

import com.humaorie.wr.api.EnviromentApiKeyProvider;
import com.humaorie.wr.api.InternetJsonRepository;
import com.humaorie.wr.api.JsonParserCursedByLeChuck;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.DefaultWordReference;

public class Main {

    public static void main(String[] args) {
        final EnviromentApiKeyProvider enviromentApiKeyProvider = new EnviromentApiKeyProvider();
        final InternetJsonRepository repository = new InternetJsonRepository(enviromentApiKeyProvider);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        final int status = cli.run(args);
        System.exit(status);
    }
}
