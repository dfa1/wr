package com.humaorie.wr.cli;

import org.junit.Assert;
import org.junit.Test;

public class VersionLoaderTest {

    @Test
    public void versionIsResolved() {
        final String version = new VersionLoader().loadVersion();
        Assert.assertFalse(version.contains("${project.version}"));
    }
}
