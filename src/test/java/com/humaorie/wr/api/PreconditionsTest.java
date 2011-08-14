package com.humaorie.wr.api;

import org.junit.Test;

public class PreconditionsTest {

    @Test
    public void havePublicConstructor() {
        new Preconditions();
    }

    @Test
    public void isNotFinal() {
        final Preconditions extended = new Preconditions() {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenConditionIfFalse() {
        Preconditions.require(false, "error message");
    }

    @Test
    public void dontThrowsWhenConditionIfTrue() {
        Preconditions.require(true, "error message");
    }
}
