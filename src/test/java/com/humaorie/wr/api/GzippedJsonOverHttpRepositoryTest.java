package com.humaorie.wr.api;

import org.junit.Test;

public class GzippedJsonOverHttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new GzippedJsonOverHttpRepository(null);
    }
}
