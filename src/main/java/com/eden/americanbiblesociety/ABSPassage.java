package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.Eden;
import com.caseyjbrooks.eden.bible.Reference;
import com.caseyjbrooks.eden.bible.Verse;
import com.caseyjbrooks.eden.utils.TextUtils;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Base64;

public class ABSPassage extends Verse {
    String APIKey;

    public ABSPassage(Reference reference) {
        super(reference);
        this.formatter = new ABSFormatter();

        if (reference.getBook() instanceof ABSBook) {
            ABSBook absBook = (ABSBook) reference.getBook();
            this.id = absBook.getId() + "." + reference.getChapter();
        } else {
            this.id = "Matt.1";
        }
    }

    public void download() {
        APIKey = Eden.getInstance().getMetadata().getString("ABS_ApiKey", null);

        if (TextUtils.isEmpty(APIKey)) {
            throw new IllegalStateException(
                    "API key not set in ABT metadata. Please add 'ABS_ApiKey' key to metadata."
            );
        }

        String url = "http://" + APIKey + ":x@bibles.org/v2/chapters/" + id + "/verses.js?include_marginalia=false";

        try {
            OkHttpClient client = new OkHttpClient();

            String encodedHeader = Base64.getEncoder().encodeToString((APIKey + ":x").getBytes("UTF-8"));

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Basic " + encodedHeader)
                    .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();

            Gson gson = new Gson();
            gson.fromJson(body, ABSPassage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getText() {
        if (reference.getBible() instanceof ABSBible)
            return super.getText() + "<br/><i>" + ((ABSBible) reference.getBible()).getCopyright() + "</i>";
        else
            return super.getText();
    }
}
