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
    public ThesaurusEntry parse(Reader reader) {
        Preconditions.require(reader != null, "cannot use null as Reader");
        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            assertNoError(rootJson);
            removeUselessKeys(rootJson);
            return parseThesaurusEntry(rootJson);
        } catch (JSONException ex) {
            throw new WordReferenceException("cannot parse JSON", ex);
        }
    }

    private void removeUselessKeys(JSONObject rootJson) {
        rootJson.remove("Lines");
        rootJson.remove("END");
    }

    private void assertNoError(JSONObject rootJson) {
        final String sorry = rootJson.optString("Sorry");
        if (!sorry.isEmpty()) {
            throw new WordReferenceException(sorry);
        }
    }

    private ThesaurusEntry parseThesaurusEntry(JSONObject rootJson) throws JSONException {
        final String error = rootJson.optString("Error", "");
        if (!error.isEmpty()) {
            final String note = rootJson.optString("Note", "");
            return ThesaurusEntry.create(new ArrayList<Sense>(), note);
        }
        final List<Sense> senses = new ArrayList<Sense>();
        final Iterator terms = rootJson.keys();
        while (terms.hasNext()) {
            String term = (String) terms.next();
            senses.addAll(parseTerm(rootJson.getJSONObject(term)));
        }
        return ThesaurusEntry.create(senses, "");
    }

    private List<Sense> parseTerm(JSONObject termJson) throws JSONException {
        final List<Sense> termSenses = new ArrayList<Sense>();
        final JSONObject sensesJson = termJson.getJSONObject("senses");
        final Iterator senses = sensesJson.keys();
        while (senses.hasNext()) {
            final String sense = (String) senses.next();
            termSenses.add(parseSense(sensesJson.getJSONObject(sense)));
        }
        return termSenses;
    }

    private Sense parseSense(JSONObject senseJson) throws JSONException {
        final String text = senseJson.getString("sensetext");
        final List<Synonym> synonyms = parseSynonyms(senseJson.getJSONObject("synonyms"));
        return Sense.create(text, synonyms);
    }

    private List<Synonym> parseSynonyms(JSONObject synonymsJson) throws JSONException {
        final List<Synonym> synonyms = new ArrayList<Synonym>();
        final Iterator keys = synonymsJson.keys();
        while (keys.hasNext()) {
            final String key = (String) keys.next();
            synonyms.add(parseSynonym(synonymsJson.getJSONObject(key)));
        }
        return synonyms;
    }

    private Synonym parseSynonym(JSONObject synonymJson) throws JSONException {
        final String name = synonymJson.getString("synonym");
        final String context = synonymJson.getString("context");
        return Synonym.create(name, context);
    }
}
