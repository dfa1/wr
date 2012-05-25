package com.humaorie.wr.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VersionLoader {

    public String loadVersion() {
        try {
            final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("version");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            return bufferedReader.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
