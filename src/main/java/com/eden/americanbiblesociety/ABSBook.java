package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.bible.Book;

public class ABSBook extends Book {
	protected String id;

	public ABSBook() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String s = getName() + " ";
		for(int chapter : getChapters()) {
			s += chapter + ", ";
		}
		s += getId();
		return s;
	}
}
