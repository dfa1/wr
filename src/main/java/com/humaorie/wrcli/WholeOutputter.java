package com.humaorie.wrcli;

import java.util.Iterator;
import org.json.JSONObject;

public class WholeOutputter implements Outputter {

    @Override
    public void output(JSONObject object) {
        object.remove("Lines");
        object.remove("END");
        dumpCategories(object);
    }

    private void dumpCategories(JSONObject object) {
        Iterator categoryKeys = object.keys();

        while (categoryKeys.hasNext()) {
            String categoryKey = (String) categoryKeys.next();
            System.out.println(categoryKey);
            JSONObject category = object.optJSONObject(categoryKey);
            Iterator nameKeys = category.keys();
            while (nameKeys.hasNext()) {
                String nameKey = (String) nameKeys.next();
                System.out.println("\n  " + nameKey);
                JSONObject translations = category.optJSONObject(nameKey);
                Iterator translationKeys = translations.keys();
                while (translationKeys.hasNext()) {
                    String translationKey = (String) translationKeys.next();
                    dumpTranslation(translations.optJSONObject(translationKey));
                }
            }
        }
    }

    private void dumpTranslation(JSONObject translation) {
        Iterator termKeys = translation.keys();
        while (termKeys.hasNext()) {
            String termKey = (String) termKeys.next();
            dumpTerm(translation.optJSONObject(termKey));
        }

        final String note = translation.optString("Note");
        if (note == null || note.isEmpty()) {
            return;
        }

        System.out.println("      note:  " + note);
    }

    private void dumpTerm(JSONObject term) {
        if (term == null) {
            return;
        }
        System.out.printf("    %s (%s) %s: %s\n",
                term.optString("term"),
                term.optString("POS"),
                term.optString("sense"),
                term.optString("usage"));
    }
}
