package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LocalJsonRepository implements Repository {

    @Override
    public Reader lookup(String dict, String word) {
        if (dict == null || dict.equals("")) {
            throw new IllegalArgumentException("dict cannot be null or empty");
        }

        if (word == null || word.equals("")) {
            throw new IllegalArgumentException("word cannot be null or empty");
        }

        final String fileName = String.format("/data/%s-%s.json", dict, word);
        final InputStream inputStream = LocalJsonRepository.class.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new NotFoundException("dictionary '%s' not found", dict);
        }

        return new InputStreamReader(inputStream);
    }
}
