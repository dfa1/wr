package com.humaorie.wr.api;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DefaultJsonParser implements Parser {

    @Override
    public Result parse(Reader reader) {
        Preconditions.require(reader != null, "cannot use null as Reader");
        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            assertValidApiKey(rootJson);
            assertNoRedirect(rootJson);
            removeUselessKeys(rootJson);
            final String note = parseNote(rootJson);
            final List<Category> categories = parseMeanings(rootJson);
            return Result.create(categories, note);
        } catch (JSONException ex) {
            throw new WordReferenceException("cannot parse JSON", ex);
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
        final String sorry = rootJson.optString("Sorry");

        if (!sorry.isEmpty()) {
            throw new WordReferenceException(sorry);
        }
    }

    private String parseNote(JSONObject rootJson) {
        final String note = rootJson.optString("Note");
        rootJson.remove("Note");
        return note;
    }

    private List<Category> parseMeanings(JSONObject rootJson) {
        final List<Category> categories = new ArrayList<Category>();

        if (rootJson.has("Error")) {
            return categories;
        }

        final Iterator meaningKeys = rootJson.keys();

        while (meaningKeys.hasNext()) {
            final String meaningKey = (String) meaningKeys.next();
            final JSONObject categoryJson = rootJson.optJSONObject(meaningKey);
            final Category category = parseCategory(meaningKey, categoryJson);
            categories.add(category);
        }

        return categories;
    }

    private Category parseCategory(String meaningKey, JSONObject categoryJson) {
        final Iterator translationsKeys = categoryJson.keys();
        final List<Translation> translations = new ArrayList<Translation>();

        while (translationsKeys.hasNext()) {
            final String translationKey = (String) translationsKeys.next();
            final JSONObject translationsJson = categoryJson.optJSONObject(translationKey);
            translations.addAll(parseTranslations(translationsJson));
        }

        return Category.create(meaningKey, translations);
    }

    private List<Translation> parseTranslations(JSONObject translationsJson) {
        final List<Translation> translations = new ArrayList<Translation>();
        final Iterator translationKeys = translationsJson.keys();

        while (translationKeys.hasNext()) {
            final String translationIndex = (String) translationKeys.next();
            final Translation translation = parseTranslation(translationsJson.optJSONObject(translationIndex));
            translations.add(translation);
        }

        return translations;
    }

    private Translation parseTranslation(JSONObject translationJson) {
        final List<Term> translations = new ArrayList<Term>();
        final String note = parseNote(translationJson);
        final Term originalTerm = parseTerm(translationJson.optJSONObject("OriginalTerm"));
        translationJson.remove("OriginalTerm");
        final Iterator termKeys = translationJson.keys();

        while (termKeys.hasNext()) {
            final String termKey = (String) termKeys.next();
            final JSONObject termJson = translationJson.optJSONObject(termKey);
            final Term term = parseTerm(termJson);
            translations.add(term);
        }

        return Translation.create(originalTerm, translations, note);
    }

    private Term parseTerm(JSONObject termJson) {
        final String term = termJson.optString("term");
        final String pos = termJson.optString("POS");
        final String sense = termJson.optString("sense");
        final String usage = termJson.optString("usage");
        return Term.create(term, pos, sense, usage);
    }
}
