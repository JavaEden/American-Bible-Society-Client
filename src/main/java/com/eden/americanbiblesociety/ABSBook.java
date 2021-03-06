package com.eden.americanbiblesociety;

import com.eden.bible.Book;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ABSBook extends Book implements JsonDeserializer<ABSBook> {
	public ABSBook() {
		super();
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

    @Override
    public ABSBook deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        this.id = json.getAsJsonObject().get("id").getAsString();

        this.name = json.getAsJsonObject().get("name").getAsString();
        this.abbreviation = json.getAsJsonObject().get("abbr").getAsString();
        this.location = json.getAsJsonObject().get("ord").getAsInt();

        JsonArray chapters = json.getAsJsonObject().get("chapters").getAsJsonArray();
        ArrayList<Integer> chapterVerseCounts = new ArrayList<>();
        for(int j = 0; j < chapters.size(); j++) {
            JsonObject chapterJSON = chapters.get(j).getAsJsonObject();
            try {
                String osis_end = chapterJSON.get("osis_end").getAsString();
                Matcher m = Pattern.compile(".*\\.\\d+\\.(\\d+)").matcher(osis_end);

                if(m.find()) {
                    chapterVerseCounts.add(Integer.parseInt(m.group(1)));
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        int[] chaptersArray = new int[chapterVerseCounts.size()];

        for(int j = 0; j < chapterVerseCounts.size(); j++) {
            chaptersArray[j] = chapterVerseCounts.get(j);
        }

        this.setChapters(chaptersArray);

        return this;
    }
}
