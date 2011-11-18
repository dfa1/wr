package com.humaorie.wr.cli;

import com.humaorie.wr.api.JsonParserCursedByLeChuck;
import com.humaorie.wr.api.Parser;
import com.humaorie.wr.api.DefaultWordReference;
import com.humaorie.wr.api.FileApiKeyProvider;
import com.humaorie.wr.api.GzippedJsonOverHttpRepository;
import com.humaorie.wr.api.UrlFactory;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        final File apiKeyFile = new File(System.getProperty("user.home"), ".wrcli");
        final FileApiKeyProvider apiKeyProvider = new FileApiKeyProvider(apiKeyFile);
        final UrlFactory urlFactory = new UrlFactory(apiKeyProvider);
        final GzippedJsonOverHttpRepository repository = new GzippedJsonOverHttpRepository(urlFactory);
        final Parser parser = new JsonParserCursedByLeChuck();
        final DefaultWordReference wordReference = new DefaultWordReference(repository, parser);
        final CommandLineClient cli = new CommandLineClient(wordReference);
        final int status = cli.run(args);
        System.exit(status);
    }
}
