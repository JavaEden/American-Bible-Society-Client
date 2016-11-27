package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.bible.Reference;
import com.caseyjbrooks.eden.bible.Verse;

public class ABSPassage extends Verse {
	String APIKey;

	public ABSPassage(Reference reference) {
		super(reference);
		this.formatter = new ABSFormatter();

		if(reference.getBook() instanceof ABSBook) {
			ABSBook absBook = (ABSBook) reference.getBook();
			this.id = absBook.getId() + "." + reference.getChapter();
		}
		else {
			this.id = "Matt.1";
		}
	}

//	@Override
//	public Class<? extends Verse> getVerseClass() {
//		return ABSVerse.class;
//	}
//
//	@Override
//	public void download(OnResponseListener listener) {
//		APIKey = ABT.getInstance().getMetadata().getString("ABS_ApiKey", null);
//
//		if(TextUtils.isEmpty(APIKey)) {
//			throw new IllegalStateException("API key not set in ABT metadata. Please add 'ABS_ApiKey' key to metadata.");
//		}
//
//		this.listener = listener;
//
//		String tag = "ABSBible";
//		String url = "http://" + APIKey + ":x@bibles.org/v2/chapters/" + id + "/verses.js?include_marginalia=false";
//
//		CachingStringRequest jsonObjReq = new CachingStringRequest(Request.Method.GET, url, this, this) {
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				HashMap<String, String> headers = new HashMap<>();
//				try {
//					String encodedHeader = Base64.encodeToString(
//							(APIKey + ":x").getBytes("UTF-8"),
//							Base64.DEFAULT
//					);
//					headers.put("Authorization", "Basic " + encodedHeader);
//				}
//				catch(UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//
//				return headers;
//			}
//		};
//
//		ABT.getInstance().addToRequestQueue(jsonObjReq, tag);
//	}
//
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		error.printStackTrace();
//		if(listener != null) {
//			listener.responseFinished(false);
//		}
//	}
//
//	@Override
//	public void onResponse(String response) {
//		if(TextUtils.isEmpty(response)) {
//			onErrorResponse(new VolleyError("Empty response"));
//		}
//
//		try {
//			JSONArray versesJSON = new JSONObject(response).getJSONObject("response").getJSONArray("verses");
//
//			//add all verses to a map from which we can pick the individual verses we want
//			HashMap<Integer, com.caseybrooks.androidbibletools.providers.abs.ABSVerse> verseMap = new HashMap<>();
//			for(int i = 0; i < versesJSON.length(); i++) {
//				Reference verseReference = new Reference.Builder()
//						.setBible(reference.getBible())
//						.setBook(reference.getBook())
//						.setChapter(reference.getChapter())
//						.setVerses(i)
//						.create();
//				com.caseybrooks.androidbibletools.providers.abs.ABSVerse verse = new ABSVerse(verseReference);
//
//				JSONObject verseJSON = versesJSON.getJSONObject(i);
//				String text = verseJSON.getString("text");
//
//				verse.setText(text);
//
//				verseMap.put((i+1), verse);
//			}
//
//			verses.clear();
//			for(int i = 0; i < reference.getVerses().size(); i++) {
//				com.caseybrooks.androidbibletools.providers.abs.ABSVerse verseFromMap = verseMap.get(reference.getVerses().get(i));
//				verses.add(verseFromMap);
//			}
//
//			if(listener != null) {
//				listener.responseFinished(true);
//			}
//		}
//		catch(JSONException e) {
//			e.printStackTrace();
//			onErrorResponse(new VolleyError("Error parsing JSON", e));
//		}
//	}

	@Override
	public String getText() {
		if(reference.getBible() instanceof ABSBible)
			return super.getText() + "<br/><i>" + ((ABSBible) reference.getBible()).getCopyright() + "</i>";
		else
			return super.getText();
	}
}
