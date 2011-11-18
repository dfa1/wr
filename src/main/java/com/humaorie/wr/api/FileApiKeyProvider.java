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
        if (!file.exists()) {
            throw new WordReferenceException(String.format("cannot read your api key from %s", file));
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final String apiKey = reader.readLine();
            if (apiKey == null) {
                throw new WordReferenceException(String.format("please set your API key into file %s%nSee http://www.wordreference.com/docs/APIregistration.aspx", file));
            }
            return apiKey;
        } catch (IOException ex) {
            throw new WordReferenceException("cannot read api key from file", ex);
        }
    }
}
