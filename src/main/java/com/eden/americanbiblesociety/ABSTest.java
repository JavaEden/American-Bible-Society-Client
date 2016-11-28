package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.Eden;
import com.google.gson.GsonBuilder;

public class ABSTest {

    public static void main(String[] args) {
        Eden.getInstance().getMetadata().put("ABS_ApiKey", "mDaM8REZFo6itplNpcv1ls8J5PkwEz1wbhJ7p9po");

        GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().serializeNulls();

        ABSBibleList obj = new ABSBibleList();
        obj.download();

//        ABSBible obj = new ABSBible();
//        obj.setId("eng-NASB");
//        obj.download();

















        System.out.println(builder.create().toJson(obj));
    }
}
