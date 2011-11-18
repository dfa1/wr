package com.humaorie.wr.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class FileApiKeyProviderTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullFileLeadsToException() {
        new FileApiKeyProvider(null);
    }
    
    @Test(expected=WordReferenceException.class)
    public void nonExistentFileLeadsToException() throws IOException {
        final FileApiKeyProvider provider = new FileApiKeyProvider(new File("I_do_not_exist"));
        provider.provideKey();
    }
    
    @Test(expected=WordReferenceException.class)
    public void emptyFileLeadsToException() throws IOException {
        final File file = File.createTempFile("apikey-", "-test", new File("target"));
        final FileApiKeyProvider provider = new FileApiKeyProvider(file);
        provider.provideKey();
    }
    
    @Test
    public void yieldsFileContents() throws IOException {
        final File file = File.createTempFile("apikey-", "-test", new File("target"));
        final FileWriter writer = new FileWriter(file);
        final String expectedApiKey = "123456";
        writer.append(expectedApiKey);
        writer.close();
        
        final FileApiKeyProvider provider = new FileApiKeyProvider(file);
        final String providedApiKey = provider.provideKey();
        Assert.assertEquals(expectedApiKey, providedApiKey);
    }
}
