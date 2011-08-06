package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import org.junit.Assert;
import org.junit.Test;

public class FileSystemRepositoryTest {

    @Test
    public void canLoadADefinition() {
        LocalJsonRepository fsr = new LocalJsonRepository();

        Assert.assertNotNull(fsr.lookup("enit", "run"));
    }

    @Test
    public void returnNotFoundWhen() throws IOException {
        LocalJsonRepository fsr = new LocalJsonRepository();
        Reader reader = fsr.lookup("enit", "missing");
        String content = readerToString(reader);
        Assert.assertTrue(content.contains("No translation was found"));
    }

    @Test
    public void canReadTheWholeDefinition() throws IOException {
        LocalJsonRepository fsr = new LocalJsonRepository();
        Reader reader = fsr.lookup("enit", "run");
        String content = readerToString(reader);

        Assert.assertTrue(content.endsWith("}"));
    }

    private String readerToString(Reader reader) throws IOException {
        StringWriter stringWriter = new StringWriter();
        int c;
        while ((c = reader.read()) != -1) {
            stringWriter.append((char) c);
        }
        return stringWriter.toString();
    }
}
