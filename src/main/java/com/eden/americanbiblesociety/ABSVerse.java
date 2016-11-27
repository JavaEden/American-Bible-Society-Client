package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.bible.Reference;
import com.caseyjbrooks.eden.bible.Verse;

public class ABSVerse extends Verse {
	public ABSVerse(Reference reference) {
		super(reference);
		this.formatter = new ABSFormatter();

		if(reference.getBook() instanceof ABSBook) {
			ABSBook absBook = (ABSBook) reference.getBook();
			this.id = absBook.getId() + "." + reference.getChapter();
		}
		else {
			this.id = "Matt.1";
		}
	}
}
