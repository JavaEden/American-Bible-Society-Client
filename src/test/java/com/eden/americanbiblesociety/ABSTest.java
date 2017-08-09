package com.eden.americanbiblesociety;

import com.eden.Eden;
import com.eden.bible.Passage;
import com.eden.injection.annotations.EdenBible;
import com.eden.injection.annotations.EdenBibleList;
import org.junit.Assert;
import org.junit.Test;


public class ABSTest {

    @Test
    public void testBasicStuff() throws Throwable {

        // Setup Eden application and register ABSRepository as an injectable EdenRepository
        Eden eden = Eden.getInstance();
        eden.config().putString("ABS_ApiKey", "mDaM8REZFo6itplNpcv1ls8J5PkwEz1wbhJ7p9po");
        eden.config().putString("com.eden.americanbiblesociety.ABSRepository_selectedBibleId", "eng-NASB");
        eden.registerRepository(new ABSRepository());

        // Get our repository as an injected repository, and use it to query for our Bible
        ABSRepository repo = (ABSRepository) eden.getRepository(ABSRepository.class);

        Passage passage = repo.lookupVerse("Galatians 2:19-21");

        Assert.assertEquals(passage.getReference().toString(), "Galatians 2:19-21");

        System.out.println(eden.getSerializer().create().toJson(passage));
        System.out.println(passage.getReference().toString());
        System.out.println(passage.getFormattedText());
    }

    @EdenBible(repository = ABSRepository.class)
    public ABSBible injectedBible;

    @EdenBibleList(repository = ABSRepository.class)
    public ABSBibleList injectedBibleList;

    @Test
    public void testInjector() throws Throwable {
        Assert.assertNull(injectedBible);
        Assert.assertNull(injectedBibleList);

        Eden.getInstance().inject(this);

        Assert.assertNotNull(injectedBible);
        Assert.assertNotNull(injectedBibleList);
    }
}
