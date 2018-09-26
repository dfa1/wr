package com.humaorie.wr.dict;

import java.util.List;

import com.humaorie.wr.api.Preconditions;

public class DictEntry {

    private List<Category> categories;
    private String note;
    private String newDict;
    private String newWord;
    
    public static DictEntry redirect(String newDict, String newWord) {
        final DictEntry newEntry = new DictEntry();
        Preconditions.require(newDict != null, "newDict cannot be null");
        Preconditions.require(newWord != null, "newWord cannot be null");
        newEntry.newDict = newDict; 
        newEntry.newWord = newWord;
        return newEntry;
    }
    
    public static DictEntry of(List<Category> categories, String note) {
        final DictEntry newEntry = new DictEntry();
        newEntry.setCategories(categories);
        newEntry.setNote(note);
        return newEntry;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }
    
    public boolean isRedirect() {
    	return newDict != null && newWord != null;
    }
    
    public String getNewDict() {
		return newDict;
	}
    
    public String getNewWord() {
		return newWord;
	}
}
