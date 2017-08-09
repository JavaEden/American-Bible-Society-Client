package com.eden.americanbiblesociety;

import com.eden.bible.Bible;
import com.eden.repositories.EdenRepository;

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
    public void setSelectedBible(Bible selectedBible) {
        super.setSelectedBible(selectedBible);
    }

    @Override
    public ABSPassage lookupVerse(String reference) {
        return (ABSPassage) super.lookupVerse(reference);
    }
}
