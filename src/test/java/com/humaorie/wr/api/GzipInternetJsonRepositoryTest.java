package com.humaorie.wr.api;

import org.junit.Test;

public class GzipInternetJsonRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new GzipInternetJsonRepository(null);
    }
}
