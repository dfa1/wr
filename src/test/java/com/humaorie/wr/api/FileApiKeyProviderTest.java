package com.humaorie.wr.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileApiKeyProviderTest {

    @Rule
    public final TemporaryFolder tempDir = new TemporaryFolder();

    @Test(expected = IllegalArgumentException.class)
    public void nullFileLeadsToException() {
        new FileApiKeyProvider(null);
    }

    @Test(expected = WordReferenceException.class)
    public void nonExistentFileLeadsToException() throws IOException {
        final FileApiKeyProvider provider = new FileApiKeyProvider(new File("I_do_not_exist"));
        provider.provideKey();
    }

    @Test(expected = WordReferenceException.class)
    public void emptyFileLeadsToException() throws IOException {
        final File file = this.tempDir.newFile("apiKey");
        final FileApiKeyProvider provider = new FileApiKeyProvider(file);
        provider.provideKey();
    }

    @Test
    public void yieldsFileContents() throws IOException {
        final String expectedApiKey = "123456";
        final File file = this.tempDir.newFile("apiKey");
        try (FileWriter writer = new FileWriter(file)) {
            writer.append(expectedApiKey);
        }

        final FileApiKeyProvider provider = new FileApiKeyProvider(file);
        final String providedApiKey = provider.provideKey();
        Assert.assertEquals(expectedApiKey, providedApiKey);
    }
}
