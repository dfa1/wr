package com.humaorie.wrcli;

import java.io.Reader;
import org.json.JSONObject;

public interface Parser {
    
    JSONObject parseDefinition(Reader reader);
}
