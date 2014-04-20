package com.humaorie.wr.cli;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class VersionLoaderTest {

    @Test
    public void versionIsResolved() throws IOException {
        final String version = new VersionLoader().loadVersion();
        Assert.assertFalse(version.contains("${project.version}"));
    }

}
