package com.humaorie.wr.cli;

import com.humaorie.wr.api.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final File apiKeyFile = new File(System.getProperty("user.home"), ".wrcli");
        final ApiKeyProvider apiKeyProvider = new FileApiKeyProvider(apiKeyFile);
        final UrlFactory urlFactory = new DefaultUrlFactory(apiKeyProvider);
        final Repository repository = new HttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final WordReference wordReference = new DefaultWordReference(repository, parser);
        final Cli cli = new Cli(wordReference);
        final int status = cli.run(args);
        System.exit(status);
    }
}
