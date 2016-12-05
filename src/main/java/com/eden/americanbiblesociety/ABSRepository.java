package com.eden.americanbiblesociety;

import com.eden.EdenRepository;
import com.eden.bible.Bible;
import com.eden.bible.Book;

public class ABSRepository extends EdenRepository {
    public ABSRepository() {
        super();
    }

    @Override
    public Class<ABSBibleList> getBibleListClass() {
        return ABSBibleList.class;
    }

    @Override
    public Class<ABSBible> getBibleClass() {
        return ABSBible.class;
    }

    @Override
    public Class<ABSPassage> getPassageClass() {
        return ABSPassage.class;
    }

    @Override
    public ABSBible getSelectedBible() {
        return (ABSBible) super.getSelectedBible();
    }

    @Override
    public void setSelectedBible(Bible<? extends Book> selectedBible) {
        super.setSelectedBible(selectedBible);
    }

    @Override
    public ABSPassage lookupVerse(String reference) {
        return (ABSPassage) super.lookupVerse(reference);
    }
}
