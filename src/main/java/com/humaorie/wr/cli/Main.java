package com.humaorie.wr.cli;

import com.humaorie.wr.api.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        final File apiKeyFile = new File(System.getProperty("user.home"), ".wrcli");
        final ApiKeyProvider apiKeyProvider = new FileApiKeyProvider(apiKeyFile);
        final UrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final Repository repository = new UrlErrorAwareRepository(new UrlGzippedRepository(urlFactory));
        final Parser parser = new JsonParserCursedByLeChuck();
        final WordReference wordReference = new DefaultWordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        final int status = cli.run(args);
        System.exit(status);
    }
}
