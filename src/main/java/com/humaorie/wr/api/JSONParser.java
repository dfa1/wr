package com.humaorie.wr.api;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser implements Parser {

    @Override
    public Result parseDefinition(Reader reader) {
        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            assertValidApiKey(rootJson);
            assertNoRedirect(rootJson);
            final String note = rootJson.optString("Note");
            final List<Category> categories = parseCategories(rootJson);
            return new Result(note, categories);
        } catch (JSONException ex) {
            throw new IllegalStateException("cannot parse JSON", ex);
        }
    }

    private List<Category> parseCategories(JSONObject rootJson) {
        rootJson.remove("Lines");
        rootJson.remove("END");
        List<Category> categories = new ArrayList<Category>();
        Iterator categoryKeys = rootJson.keys();

        if (categoryKeys == null) {
            return categories;
        }
        
        while (categoryKeys.hasNext()) {
            String categoryKey = (String) categoryKeys.next();
            Category category = new Category(categoryKey);
            categories.add(category);
            JSONObject categoryJson = rootJson.optJSONObject(categoryKey);

            if (categoryJson == null) {
                break;
            }
            
            Iterator nameKeys = categoryJson.keys();
            while (nameKeys.hasNext()) {
                String nameKey = (String) nameKeys.next();
                JSONObject translationsJson = categoryJson.optJSONObject(nameKey);
                Iterator translationKeys = translationsJson.keys();
                while (translationKeys.hasNext()) {
                    String translationKey = (String) translationKeys.next();
                    category.addTranslation(parseTranslation(translationsJson.optJSONObject(translationKey)));
                }
            }
        }

        return categories;
    }

    private Translation parseTranslation(JSONObject translationJson) {
        Translation translation = new Translation(translationJson.optString("Note"));
        translationJson.remove("Note");
        Iterator termKeys = translationJson.keys();

        while (termKeys.hasNext()) {
            String termKey = (String) termKeys.next();
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

    private void assertNoRedirect(JSONObject rootJson) {
        if (!rootJson.optString("Response").isEmpty()) {
            throw new RedirectException(rootJson.optString("URL"));
        }
    }

    private void assertValidApiKey(JSONObject rootJson) {
        String sorry = rootJson.optString("Sorry");

        if (!sorry.isEmpty()) {
            throw new InvalidApiKeyException(sorry);
        }
    }
}
