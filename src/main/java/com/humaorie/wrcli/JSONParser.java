package com.humaorie.wrcli;

import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser implements Parser {

    @Override
    public JSONObject parseDefinition(Reader reader) {
        try {
            return new JSONObject(new JSONTokener(reader));
        } catch (JSONException ex) {
            throw new IllegalStateException("cannot parse JSON");
        }
    }
}
