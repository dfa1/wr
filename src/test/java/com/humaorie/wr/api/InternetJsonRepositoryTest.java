package com.humaorie.wr.api;

import org.junit.Test;

public class InternetJsonRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new InternetJsonRepository(null);
    }
}
