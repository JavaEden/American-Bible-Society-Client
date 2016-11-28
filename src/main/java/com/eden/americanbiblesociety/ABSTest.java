package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.Eden;
import com.caseyjbrooks.eden.bible.Reference;
import com.google.gson.GsonBuilder;

public class ABSTest {

    public static void main(String[] args) {
        Eden.getInstance().getMetadata().put("ABS_ApiKey", "mDaM8REZFo6itplNpcv1ls8J5PkwEz1wbhJ7p9po");

        GsonBuilder builder = Eden.getInstance().getSerializer();

//        ABSBibleList obj = new ABSBibleList();
//        obj.download();

        ABSBible bible = new ABSBible();
        bible.setId("eng-NASB");
        bible.download();

        Reference ref = new Reference.Builder()
                .setBible(bible)
                .parseReference("Galatians 2:19-21")
                .create();

        ABSPassage passage = new ABSPassage(ref);
        passage.download();

        System.out.println(builder.create().toJson(passage));
        System.out.println(passage.getReference().toString());
        System.out.println(passage.getText());
        System.out.println(passage.getFormattedText());
    }
}
