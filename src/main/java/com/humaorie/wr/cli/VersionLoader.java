package com.humaorie.wr.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VersionLoader {

    public String loadVersion() throws IOException {
    	final BufferedReader reader = this.createReader();
    	try {
            return reader.readLine();
    	} finally {
    		reader.close();
    	}
    }

	private BufferedReader createReader() throws IOException {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("version");
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		return bufferedReader;
	}
}
