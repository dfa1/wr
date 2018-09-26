package com.humaorie.wr.api;

import org.junit.Assert;
import org.junit.Test;

public class PreconditionsTest {

    @Test
    public void hasPublicNoArgConstructor() {
        new Preconditions();
    }

    @Test
    public void notFinal() {
        new Preconditions() {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenPreconditionIsNotVerified() {
        Preconditions.require(false, "error message");
    }

    @Test
    public void throwsProvidedErrorMessage() {
        final String expectedErrorMessage = "just an informative error message";
        try {
            Preconditions.require(false, expectedErrorMessage);
            Assert.fail("an exception was expected");
        } catch (final IllegalArgumentException ex) {
            Assert.assertEquals(expectedErrorMessage, ex.getMessage());
        }
    }

    @Test
    public void doNothingWhenPrecontionsIsSatisfied() {
        Preconditions.require(true, "error message");
    }
}
