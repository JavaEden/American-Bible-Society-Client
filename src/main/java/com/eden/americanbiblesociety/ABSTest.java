package com.eden.americanbiblesociety;

import com.caseyjbrooks.clog.Clog;
import com.eden.Eden;
import com.eden.bible.Passage;
import com.eden.injection.annotations.EdenBible;
import com.eden.injection.annotations.EdenBibleList;

public class ABSTest {

    public static void main(String[] args) {

        // Setup Eden application and register ABSRepository as an injectable EdenRepository
        Eden eden = Eden.getInstance();
        eden.put("ABS_ApiKey", "mDaM8REZFo6itplNpcv1ls8J5PkwEz1wbhJ7p9po");
        eden.put("com.eden.americanbiblesociety.ABSRepository_selectedBibleId", "eng-NASB");
        eden.registerRepository(new ABSRepository());

        // Get our repository as an injected repository, and use it to query for our Bible
        ABSRepository repo = (ABSRepository) eden.getRepository(ABSRepository.class);

        Passage passage = repo.lookupVerse("Galatians 2:19-21");

        System.out.println(passage.getReference().toString());
        System.out.println(passage.getFormattedText());

        ABSTest test = new ABSTest();
        test.testInjector();
    }

    @EdenBible(repository = ABSRepository.class)
    public ABSBible injectedBible;

    @EdenBibleList(repository = ABSRepository.class)
    public ABSBibleList injectedBibleList;

    public void testInjector() {
        Clog.i("\n\n");
        Clog.i("Is injectedBible currently null?: #{$1}", (injectedBible == null));
        Clog.i("Is injectedBibleList currently null?: #{$1}", (injectedBibleList == null));

        Eden.getInstance().inject(this);

        Clog.i("Is injectedBible currently null?: #{$1}", (injectedBible == null));
        Clog.i("Is injectedBibleList currently null?: #{$1}", (injectedBibleList == null));
    }
}
