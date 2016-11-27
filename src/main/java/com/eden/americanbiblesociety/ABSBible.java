package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.Eden;
import com.caseyjbrooks.eden.bible.Bible;
import com.caseyjbrooks.eden.defaults.DefaultBible;
import com.caseyjbrooks.eden.utils.TextUtils;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.Base64;

public class ABSBible extends Bible<ABSBook> {
//Data Members
//--------------------------------------------------------------------------------------------------
	protected String APIKey;

	protected String nameEnglish;
	protected String copyright;

    protected String languageEnglish;

//Constructors
//--------------------------------------------------------------------------------------------------
	public ABSBible() {
		super();
		this.id = "eng-ESV";

		this.abbreviation = "ESV";
		this.name = "English Standard Version";
		this.nameEnglish = "English Standard Version";
		this.language = "English";
		this.languageEnglish = "English";
		this.copyright = "Scripture quotations marked (ESV) are from The Holy Bible, English Standard Version®, copyright © 2001 by Crossway Bibles, a publishing ministry of Good News Publishers. Used by permission. All rights reserved.";

		books = new ArrayList<>();
		for(int i = 0; i < DefaultBible.defaultBookName.length; i++) {
			ABSBook book = new ABSBook();
			book.setId(this.id + ":" + DefaultBible.defaultBookAbbr[i]);
			book.setName(DefaultBible.defaultBookName[i]);
			book.setAbbreviation(DefaultBible.defaultBookAbbr[i]);
			book.setChapters(DefaultBible.defaultBookVerseCount[i]);
			book.setLocation(i + 1);

			books.add(book);
		}
	}

//Getters and Setters
//--------------------------------------------------------------------------------------------------
	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

//Downloadable Interface Implementation
//--------------------------------------------------------------------------------------------------
//	@Override
	public void download() {
		APIKey = Eden.getInstance().getMetadata().getString("ABS_ApiKey", null);

		if(TextUtils.isEmpty(APIKey)) {
			throw new IllegalStateException(
					"API key not set in ABT metadata. Please add 'ABS_ApiKey' key to metadata."
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

            Gson gson = new Gson();
            gson.fromJson(body, ABSBible.class);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || !(o instanceof ABSBible)) {
			return false;
		}

		ABSBible bible = (ABSBible) o;

		if(getId().equalsIgnoreCase(bible.getId())) {
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
}
