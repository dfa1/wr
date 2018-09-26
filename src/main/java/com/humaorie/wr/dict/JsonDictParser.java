package com.humaorie.wr.dict;

import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.WordReferenceException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonDictParser implements DictParser {

    @Override
    public DictEntry parse(Reader reader) {
        Preconditions.require(reader != null, "cannot use null as Reader");
        try {
            final JSONObject rootJson = new JSONObject(new JSONTokener(reader));
            this.assertNoError(rootJson);
            this.removeUselessKeys(rootJson);
            if ("Redirect".equals(rootJson.optString("Response"))) {
                return this.parseRedirect(rootJson);
            } else {
            	return this.parseDictEntry(rootJson);
            }
        } catch (final JSONException ex) {
            throw new WordReferenceException("cannot parse JSON", ex);
        }
    }

    private DictEntry parseRedirect(JSONObject rootJson) {
        final String newUrl = rootJson.optString("URL");
        final String[] newDictAndWord = newUrl.substring(1).split("/", 2);
		return DictEntry.redirect(newDictAndWord[0], newDictAndWord[1]);
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

    private DictEntry parseDictEntry(JSONObject rootJson) {
        final String note = this.parseNote(rootJson);
        final List<Category> categories = this.parseCategories(rootJson);
        return DictEntry.of(categories, note);
    }

    private String parseNote(JSONObject rootJson) {
        final String note = rootJson.optString("Note");
        rootJson.remove("Note");
        return note;
    }

    private List<Category> parseCategories(JSONObject rootJson) {
        final List<Category> categories = new ArrayList<>();
        if (rootJson.has("Error")) {
            return categories;
        }
        final Iterator<?> meaningKeys = rootJson.keys();
        while (meaningKeys.hasNext()) {
            final String meaningKey = (String) meaningKeys.next();
            final JSONObject categoryJson = rootJson.optJSONObject(meaningKey);
            final Category category = this.parseCategory(meaningKey, categoryJson);
            categories.add(category);
        }
        return categories;
    }

    private Category parseCategory(String meaningKey, JSONObject categoryJson) {
        final Iterator<?> translationsKeys = categoryJson.keys();
        final List<Translation> translations = new ArrayList<>();
        while (translationsKeys.hasNext()) {
            final String translationKey = (String) translationsKeys.next();
            final JSONObject translationsJson = categoryJson.optJSONObject(translationKey);
            translations.addAll(this.parseTranslations(translationsJson));
        }
        return Category.create(meaningKey, translations);
    }

    private List<Translation> parseTranslations(JSONObject translationsJson) {
        final List<Translation> translations = new ArrayList<>();
        final Iterator<?> translationKeys = translationsJson.keys();
        while (translationKeys.hasNext()) {
            final String translationIndex = (String) translationKeys.next();
            final Translation translation = this.parseTranslation(translationsJson.optJSONObject(translationIndex));
            translations.add(translation);
        }
        return translations;
    }

    private Translation parseTranslation(JSONObject translationJson) {
        final List<Term> terms = new ArrayList<>();
        final String note = this.parseNote(translationJson);
        final Term originalTerm = this.parseTerm(translationJson.optJSONObject("OriginalTerm"));
        translationJson.remove("OriginalTerm");
        final Iterator<?> termKeys = translationJson.keys();
        while (termKeys.hasNext()) {
            final String termKey = (String) termKeys.next();
            final JSONObject termJson = translationJson.optJSONObject(termKey);
            final Term term = this.parseTerm(termJson);
            terms.add(term);
        }
        return Translation.create(originalTerm, terms, note);
    }

    private Term parseTerm(JSONObject termJson) {
        final String term = termJson.optString("term");
        final String pos = termJson.optString("POS");
        final String sense = termJson.optString("sense");
        final String usage = termJson.optString("usage");
        return Term.create(term, pos, sense, usage);
    }
}
