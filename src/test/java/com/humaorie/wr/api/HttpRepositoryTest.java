package com.humaorie.wr.api;

import org.junit.Test;

public class HttpRepositoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateWithNullUrlFactory() {
        new HttpRepository(null);
    }

}
