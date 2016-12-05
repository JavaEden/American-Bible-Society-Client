package com.eden.americanbiblesociety;

import com.eden.Eden;
import com.eden.bible.Bible;
import com.eden.utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.Base64;

public class ABSBible extends Bible<ABSBook> implements JsonDeserializer<ABSBible> {
//Data Members
//--------------------------------------------------------------------------------------------------

    protected String nameEnglish;
    protected String languageEnglish;

//Constructors
//--------------------------------------------------------------------------------------------------
    public ABSBible() {
        super();
    }

//Getters and Setters
//--------------------------------------------------------------------------------------------------
    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getLanguageEnglish() {
        return languageEnglish;
    }

    public void setLanguageEnglish(String languageEnglish) {
        this.languageEnglish = languageEnglish;
    }

    public boolean get() {
        String APIKey = Eden.getInstance().get("ABS_ApiKey");

        if (TextUtils.isEmpty(APIKey)) {
            throw new IllegalStateException(
                    "API key not set in Eden metadata. Please add 'ABS_ApiKey' key to Eden metadata."
            );
        }

        String url = "http://" + APIKey + ":x@bibles.org/v2/versions/" + id + "/books.js?include_chapters=true";

        try {
            OkHttpClient client = new OkHttpClient();
            String encodedHeader = Base64.getEncoder().encodeToString((APIKey + ":x").getBytes("UTF-8"));

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Basic " + encodedHeader)
                    .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();

            Gson gson = Eden.getInstance().getDeserializer().registerTypeAdapter(ABSBible.class, this).create();
            gson.fromJson(body, ABSBible.class);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof ABSBible)) {
            return false;
        }

        ABSBible bible = (ABSBible) o;

        if (getId().equalsIgnoreCase(bible.getId())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public ABSBible deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonArray booksJson = json.getAsJsonObject().get("response").getAsJsonObject().get("books").getAsJsonArray();

        //TODO: find and extract 'language' and 'languageEnglish'

        for(int i = 0; i < booksJson.size(); i++) {
            JsonObject bookJson = booksJson.get(i).getAsJsonObject();

            if(TextUtils.isEmpty(this.abbreviation)) {
                this.abbreviation = bookJson
                        .get("parent").getAsJsonObject()
                        .get("version").getAsJsonObject()
                        .get("id").getAsString()
                        .replaceAll(".*-", "");
            }
            if(TextUtils.isEmpty(this.name)) {
                this.name = bookJson
                        .get("parent").getAsJsonObject()
                        .get("version").getAsJsonObject()
                        .get("name").getAsString();

                this.nameEnglish = bookJson
                        .get("parent").getAsJsonObject()
                        .get("version").getAsJsonObject()
                        .get("name").getAsString();
            }
            if(TextUtils.isEmpty(this.copyright)) {
                this.copyright = bookJson
                        .get("copyright").getAsString();
            }

            ABSBook book = new ABSBook();
            Gson gson = Eden.getInstance().getDeserializer().registerTypeAdapter(ABSBook.class, book).create();
            gson.fromJson(bookJson, ABSBook.class);

            this.books.add(book);
        }

        return this;
    }

    public static class ListJsonizer implements JsonDeserializer<ABSBible> {
        @Override
        public ABSBible deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject bibleObj = json.getAsJsonObject();
            ABSBible bible = new ABSBible();
            bible.setId(              bibleObj.get("id").getAsString());
            bible.setName(            bibleObj.get("name").getAsString());
            bible.setAbbreviation(    bibleObj.get("abbreviation").getAsString());
            bible.setLanguage(        bibleObj.get("lang_name").getAsString());
            bible.setLanguageEnglish( bibleObj.get("lang_name_eng").getAsString());
            bible.setCopyright(       bibleObj.get("copyright").getAsString());
            bible.setNameEnglish(     bibleObj.get("name").getAsString());

            return bible;
        }
    }
}
