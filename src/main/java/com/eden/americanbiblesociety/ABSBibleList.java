package com.eden.americanbiblesociety;

import com.eden.Eden;
import com.eden.bible.BibleList;
import com.eden.utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ABSBibleList extends BibleList<ABSBible> implements JsonDeserializer<ABSBibleList> {
    public ABSBibleList() {

    }

    public boolean get() {
        String APIKey = Eden.getInstance().config().getString("ABS_ApiKey");

        if (TextUtils.isEmpty(APIKey)) {
            throw new IllegalStateException(
                    "API key not set in Eden metadata. Please add 'ABS_ApiKey' key to metadata."
            );
        }

        String url = "http://bibles.org/v2/versions.js";

        try {
            OkHttpClient client = new OkHttpClient();
            String encodedHeader = Base64.getEncoder().encodeToString((APIKey + ":x").getBytes("UTF-8"));

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Basic " + encodedHeader)
                    .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();

            Type listType = new TypeToken<Map<String, ABSBible>>() {
            }.getType();
            Gson gson = new GsonBuilder().registerTypeAdapter(ABSBibleList.class, this).create();
            gson.fromJson(body, ABSBibleList.class);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ABSBibleList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonArray biblesJson = json.getAsJsonObject().get("response").getAsJsonObject().get("versions").getAsJsonArray();

        this.bibles = new HashMap<>();

        Gson gson = Eden.getInstance().getDeserializer().registerTypeAdapter(ABSBible.class, new ABSBible.ListJsonizer()).create();

        for (int i = 0; i < biblesJson.size(); i++) {
            ABSBible bible = gson.fromJson(biblesJson.get(i), ABSBible.class);
            bibles.put(bible.getId(), bible);
        }

        return this;
    }
}
