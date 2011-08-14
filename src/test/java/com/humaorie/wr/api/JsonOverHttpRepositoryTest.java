package com.humaorie.wr.api;

import org.junit.Test;

public class JsonOverHttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new JsonOverHttpRepository(null);
    }
}
