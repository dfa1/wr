package com.humaorie.wr.api;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import org.junit.Assert;
import org.junit.Test;

public class FileSystemRepositoryTest {
    
    @Test
    public void canLoadADefinition() {
        FileSystemRepository fsr = new FileSystemRepository();
        
        Assert.assertNotNull(fsr.lookup("enit", "run"));
    }
    
    @Test
    public void canReadTheWholeDefinition() throws IOException {
        FileSystemRepository fsr = new FileSystemRepository();
        Reader reader = fsr.lookup("enit", "run");
        StringWriter stringWriter = new StringWriter();

        int c ;
        while ((c = reader.read()) != -1) {
            stringWriter.append((char) c);
        } 
        
        Assert.assertTrue(stringWriter.toString().endsWith("}"));
    }
}
