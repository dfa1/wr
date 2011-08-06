package com.humaorie.wr.api;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BoringJsonParser implements Parser {

    @Override
    public Result parseDefinition(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("cannot use null as Reader");
        }

        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            assertValidApiKey(rootJson);
            assertNoRedirect(rootJson);
            removeUselessKeys(rootJson);
            final String note = parseNote(rootJson);
            final List<Category> categories = parseCategories(rootJson);
            return new Result(note, categories);
        } catch (JSONException ex) {
            throw new IllegalStateException("cannot parse JSON", ex);
        }
    }

    private void removeUselessKeys(JSONObject rootJson) {
        rootJson.remove("Lines");
        rootJson.remove("END");
    }
    
    private void assertNoRedirect(JSONObject rootJson) {
        if ("Redirect".equals(rootJson.optString("Response"))) {
            final String newUrl = rootJson.optString("URL");
            final String[] newDictAndWord = newUrl.substring(1).split("/", 2);
            throw new RedirectException(newDictAndWord[0], newDictAndWord[1]);
        }
    }

    private void assertValidApiKey(JSONObject rootJson) {
        String sorry = rootJson.optString("Sorry");

        if (!sorry.isEmpty()) {
            throw new InvalidApiKeyException(sorry);
        }
    }
    
    private String parseNote(final JSONObject rootJson) {
        return rootJson.optString("Note");
    }

    private List<Category> parseCategories(JSONObject rootJson) {
        final List<Category> categories = new ArrayList<Category>();
        final Iterator categoryKeys = rootJson.keys();

        if (categoryKeys == null) {
            return categories;
        }

        while (categoryKeys.hasNext()) {
            final String categoryKey = (String) categoryKeys.next();
            final Category category = new Category(categoryKey);
            categories.add(category);
            final JSONObject categoryJson = rootJson.optJSONObject(categoryKey);

            if (categoryJson == null) {
                break;
            }

            final Iterator nameKeys = categoryJson.keys();
            while (nameKeys.hasNext()) {
                final String nameKey = (String) nameKeys.next();
                final JSONObject translationsJson = categoryJson.optJSONObject(nameKey);
                final Iterator translationKeys = translationsJson.keys();
                while (translationKeys.hasNext()) {
                    final String translationKey = (String) translationKeys.next();
                    category.addTranslation(parseTranslation(translationsJson.optJSONObject(translationKey)));
                }
            }
        }

        return categories;
    }

    private Translation parseTranslation(JSONObject translationJson) {
        final Translation translation = new Translation(parseNote(translationJson));
        translationJson.remove("Note");
        final Iterator termKeys = translationJson.keys();

        while (termKeys.hasNext()) {
            final String termKey = (String) termKeys.next();
            final JSONObject termJson = translationJson.optJSONObject(termKey);
            translation.addTerm(parseTerm(termJson));
        }

        return translation;
    }

    private Term parseTerm(JSONObject termJson) {
        return new Term(
                termJson.optString("term"),
                termJson.optString("POS"),
                termJson.optString("sense"),
                termJson.optString("usage"));
    }
}
