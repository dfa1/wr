package com.humaorie.wrcli;

import com.humaorie.wrcli.model.Category;
import com.humaorie.wrcli.model.Term;
import com.humaorie.wrcli.model.Translation;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser implements Parser {

    @Override
    public List<Category> parseDefinition(Reader reader) {
        try {
            JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            return parseCategories(rootJson);
        } catch (JSONException ex) {
            throw new IllegalStateException("cannot parse JSON");
        }
    }

    private List<Category> parseCategories(JSONObject rootJson) {
        rootJson.remove("Lines");
        rootJson.remove("END");
        List<Category> categories = new ArrayList<Category>();
        Iterator categoryKeys = rootJson.keys();

        while (categoryKeys.hasNext()) {
            String categoryKey = (String) categoryKeys.next();
            Category category = new Category(categoryKey);
            categories.add(category);
            JSONObject categoryJson = rootJson.optJSONObject(categoryKey);
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
}
