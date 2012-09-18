package com.humaorie.wr.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileApiKeyProvider implements ApiKeyProvider {

    private final File file;

    public FileApiKeyProvider(File file) {
        Preconditions.require(file != null, "file cannot be null");
        this.file = file;
    }

    @Override
    public String provideKey() {
        try {
            return tryLoad();
        } catch (IOException ex) {
            throw new WordReferenceException(String.format("cannot read api key from file %s", file), ex);
        }
    }

    private String tryLoad() throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            final String apiKey = reader.readLine();
            if (apiKey == null) {
                throw new WordReferenceException(String.format("please set your API key into file %s%nSee http://www.wordreference.com/docs/APIregistration.aspx", file));
            }
            return apiKey;
        } finally {
            reader.close();
        }
    }
}

