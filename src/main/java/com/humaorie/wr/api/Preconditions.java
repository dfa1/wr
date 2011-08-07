package com.humaorie.wr.api;

public class Preconditions {

    public static void require(boolean precondition, String fmt, Object ... args) {
        if (!precondition) {
            throw new IllegalArgumentException(String.format(fmt, args));
        }
    }
}
