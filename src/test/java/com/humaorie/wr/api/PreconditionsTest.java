package com.humaorie.wr.api;

import org.junit.Test;

public class PreconditionsTest {

    @Test
    public void itHasAPublicConstructor() {
        new Preconditions();
    }

    @Test
    public void itIsNotFinal() {
        final Preconditions extended = new Preconditions() {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenConditionYieldsFalse() {
        Preconditions.require(false, "error message");
    }

    @Test
    public void dontThrowsWhenConditionYieldsTrue() {
        Preconditions.require(true, "error message");
    }
}
