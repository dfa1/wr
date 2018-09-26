package com.humaorie.wr.cli;

import com.humaorie.wr.api.Preconditions;
import com.humaorie.wr.api.WordReferenceException;
import com.humaorie.wr.dict.Category;
import com.humaorie.wr.dict.Dict;
import com.humaorie.wr.dict.DictEntry;
import com.humaorie.wr.dict.Term;
import com.humaorie.wr.dict.Translation;
import com.humaorie.wr.thesaurus.Sense;
import com.humaorie.wr.thesaurus.Synonym;
import com.humaorie.wr.thesaurus.Thesaurus;
import com.humaorie.wr.thesaurus.ThesaurusEntry;
import java.io.IOException;
import java.util.List;

public class Cli {

    private static final String WR = "http://www.wordreference.com";
    private final String version;
    private final Dict dict;
    private final Thesaurus thesaurus;
    private Appendable out = System.out;
    private Appendable err = System.err;

    public Cli(String version, Dict dict, Thesaurus thesaurus) {
    	Preconditions.require(version != null, "version cannot be null");
        Preconditions.require(dict != null, "dict cannot be null");
        Preconditions.require(thesaurus != null, "thesausus cannot be null");
        this.version = version;
        this.dict = dict;
        this.thesaurus = thesaurus;
    }

    public void setErr(Appendable err) {
        this.err = err;
    }

    public void setOut(Appendable out) {
        this.out = out;
    }

    public int run(String... args) throws IOException {
        final int SUCCESS = 0;
        final int FAILURE = 1;
        if (args.length == 1 && args[0].equals("--version")) {
            this.println(this.out, "wrcli version %s", this.version);
            return SUCCESS;
        }
        if (args.length == 1 && args[0].equals("--help")) {
            this.println(this.out, "wrcli - word reference command line interface");
            this.println(this.out, "by Davide Angelocola <davide.angelocola@gmail.com>");
            this.println(this.out, "usage: wrcli dict word");
            return SUCCESS;
        }
        if (args.length != 2) {
            this.println(this.err, "error: you must provide dict and word (e.g. 'enit run')");
            return FAILURE;
        }
        try {
            this.doLookup(args);
            return SUCCESS;
        } catch (final WordReferenceException ex) {
            this.println(this.err, "error: %s", ex.getMessage());
            return FAILURE;
        }
    }

    private void doLookup(String... args) throws IOException {
        final String dict = args[0];
        final String word = args[1];
        if ("thesaurus".startsWith(dict)) {
            final ThesaurusEntry thesaurusEntry = this.thesaurus.lookup(word);
            this.printThesaurus(thesaurusEntry);
            this.printCopyright("thesaurus", word);
        } else {
            final DictEntry dictEntry = this.dict.lookup(dict, word);
            this.printDictEntry(dictEntry);
            this.printCopyright(dict, word);
        }
    }

    private void printCopyright(String dict, String word) throws IOException {
        this.println(this.out, "(C) WordReference.com");
        this.println(this.out, "Original link: %s/%s/%s", WR, dict, word);
    }

    private void printDictEntry(DictEntry dictEntry) throws IOException {
        for (final Category category : dictEntry.getCategories()) {
            this.printCategory(category);
        }
        final String note = dictEntry.getNote();
        if (!note.isEmpty()) {
            this.println(this.out, "note: %s", note);
        }
    }

    private void printCategory(Category category) throws IOException {
        this.println(this.out, "category '%s':", category.getName());
        final List<Translation> translations = category.getTranslations();
        for (final Translation translation : translations) {
            this.printTranslation(translation);
        }
    }

    private void printTranslation(Translation translation) throws IOException {
        final Term originalTerm = translation.getOriginalTerm();
        this.println(this.out, " %s %s %s %s",
                originalTerm.getTerm(),
                originalTerm.getPos(),
                originalTerm.getSense(),
                originalTerm.getUsage());
        for (final Term term : translation.getTranslations()) {
            this.println(this.out, "   %s %s %s %s",
                    term.getTerm(),
                    term.getPos(),
                    term.getSense(),
                    term.getUsage());
        }
        final String note = translation.getNote();
        if (!note.isEmpty()) {
            this.println(this.out, " note: %s", note);
        }
    }

    private void println(Appendable app, String fmt, Object... args) throws IOException {
        app.append(String.format(fmt + "%n", args));
    }

    private void printThesaurus(ThesaurusEntry entry) throws IOException {
        for (final Sense sense : entry.getSenses()) {
            this.println(this.out, "as '%s'", sense.getText());
            final List<Synonym> synonyms = sense.getSynonyms();
            for (final Synonym synonym : synonyms) {
                final String context = synonym.getContext();
                this.println(this.out, "  %s %s", synonym.getName(), context.isEmpty() ? "" : "(" + context + ")");
            }
            this.println(this.out, "");
        }
        final String note = entry.getNote();
        if (!note.isEmpty()) {
            this.println(this.out, "note: %s", note);
        }
    }
}
