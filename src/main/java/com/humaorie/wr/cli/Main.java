package com.humaorie.wr.cli;

import com.humaorie.wr.api.FileApiKeyProvider;
import com.humaorie.wr.api.Repository;
import com.humaorie.wr.api.UrlFactory;
import com.humaorie.wr.api.JsonUrlFactory;
import com.humaorie.wr.api.HttpRepository;
import com.humaorie.wr.api.ApiKeyProvider;
import com.humaorie.wr.dict.DefaultDict;
import com.humaorie.wr.dict.JsonDictParser;
import com.humaorie.wr.dict.Dict;
import com.humaorie.wr.thesaurus.DefaultThesaurus;
import com.humaorie.wr.thesaurus.JsonThesaurusParser;
import com.humaorie.wr.thesaurus.Thesaurus;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final File apiKeyFile = new File(System.getProperty("user.home"), ".wrcli");
        final ApiKeyProvider apiKeyProvider = new FileApiKeyProvider(apiKeyFile);
        final UrlFactory urlFactory = new JsonUrlFactory(apiKeyProvider);
        final Repository repository = new HttpRepository(urlFactory);
        final Dict dict = new DefaultDict(repository, new JsonDictParser());
        final Thesaurus thesaurus = new DefaultThesaurus(repository, new JsonThesaurusParser());
        final Cli cli = new Cli(dict, thesaurus);
        final int status = cli.run(args);
        System.exit(status);
    }
}
