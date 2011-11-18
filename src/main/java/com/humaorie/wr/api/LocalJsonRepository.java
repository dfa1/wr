package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LocalJsonRepository implements Repository {

    @Override
    public Reader lookup(String dict, String word) {
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(!dict.isEmpty(), "dict cannot be empty");
        Preconditions.require(word != null, "word cannot be null");
        Preconditions.require(!word.isEmpty(), "word cannot be empty");
        final String fileName = String.format("/data/%s-%s.json", dict, word);
        final InputStream inputStream = LocalJsonRepository.class.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new WordReferenceException(String.format("dictionary '%s' not found", dict));
        }

        return new InputStreamReader(inputStream);
    }
}
