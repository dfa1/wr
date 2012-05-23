package com.humaorie.wr.api;

import java.io.IOException;
import java.net.URL;

public interface UrlFactory {

    URL createUrl(String dict, String word) throws IOException;
}
