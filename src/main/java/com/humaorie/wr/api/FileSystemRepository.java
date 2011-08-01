package com.humaorie.wr.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileSystemRepository implements Repository {

    @Override
    public Reader lookup(String dict, String word) {
        String fileName = String.format("/data/%s-%s.json", dict, word);
        InputStream inputStream = FileSystemRepository.class.getResourceAsStream(fileName);

        if (inputStream == null) {
            inputStream = FileSystemRepository.class.getResourceAsStream("/data/notfound.json");
        }
        
        return new InputStreamReader(inputStream);
    }
}
