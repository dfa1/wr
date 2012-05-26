package com.humaorie.wr.thesaurus;

import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.WordReferenceException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonThesaurusParser implements ThesaurusParser {

    @Override
    public List<Sense> parse(Reader reader) {
        Preconditions.require(reader != null, "cannot use null as Reader");
        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            assertValidApiKey(rootJson);
            removeUselessKeys(rootJson);
            return parseTerms(rootJson);
        } catch (JSONException ex) {
            throw new WordReferenceException("cannot parse JSON", ex);
        }
    }

    private void removeUselessKeys(JSONObject rootJson) {
        rootJson.remove("Lines");
        rootJson.remove("END");
    }

    private void assertValidApiKey(JSONObject rootJson) {
        final String sorry = rootJson.optString("Sorry");

        if (!sorry.isEmpty()) {
            throw new WordReferenceException(sorry);
        }
    }

    private List<Sense> parseTerms(JSONObject rootJson) throws JSONException {
        List<Sense> senses = new ArrayList<Sense>();
        Iterator terms = rootJson.keys();
        while (terms.hasNext()) {
            String term = (String) terms.next();
            senses.addAll(parseTerm(rootJson.getJSONObject(term)));
        }
        return senses;
    }

    private List<Sense> parseTerm(JSONObject termJson) throws JSONException {
        List<Sense> termSenses = new ArrayList<Sense>();
        final JSONObject sensesJson = termJson.getJSONObject("senses");
        Iterator senses = sensesJson.keys();
        while (senses.hasNext()) { 
            String sense = (String) senses.next();
            termSenses.add(parseSense(sensesJson.getJSONObject(sense)));
        }
        return termSenses;
    }

    private Sense parseSense(JSONObject senseJson) throws JSONException {
        String text = senseJson.getString("sensetext");
        Sense sense = new Sense();
        sense.setText(text);
        sense.setSynonyms(parseSynonyms(senseJson.getJSONObject("synonyms")));
        return sense;
    }

    private List<Synonym> parseSynonyms(JSONObject synonymsJson) throws JSONException {
        List<Synonym> synonyms = new ArrayList<Synonym>();
        Iterator keys = synonymsJson.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            synonyms.add(parseSynonym(synonymsJson.getJSONObject(key)));
        }
        return synonyms;
    }

    private Synonym parseSynonym(JSONObject synonymJson) throws JSONException {
        Synonym synonym = new Synonym();
        synonym.setName(synonymJson.getString("synonym"));
        synonym.setContext(synonymJson.getString("context"));
        return synonym;
    }
}
