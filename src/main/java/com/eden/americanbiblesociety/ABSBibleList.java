package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.bible.BibleList;

public class ABSBibleList extends BibleList<ABSBible> {
	private String APIKey;

	public ABSBibleList() {

	}

//	public void download(OnResponseListener listener) {
//		APIKey = ABT.getInstance().getMetadata().getString("ABS_ApiKey", null);
//
//		if(TextUtils.isEmpty(APIKey)) {
//			throw new IllegalStateException(
//					"API key not set in ABT metadata. Please add 'ABS_ApiKey' key to metadata."
//			);
//		}
//
//		this.listener = listener;
//
//		String tag = "ABSBibleList";
//		String url = "http://" + APIKey + ":x@api-v2.bibles.org/v2/versions.js";
//
//		CachingStringRequest jsonObjReq = new CachingStringRequest(Request.Method.GET, url, this, this) {
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				HashMap<String, String> headers = new HashMap<String, String>();
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
//	public void onResponse(String response) {
//		if(TextUtils.isEmpty(response)) {
//			onErrorResponse(new VolleyError("Empty response"));
//		}
//
//		try {
//			JSONArray biblesJSON = new JSONObject(response).getJSONObject("response")
//			                                               .getJSONArray("versions");
//
//			bibles = new HashMap<>();
//
//			for(int i = 0; i < biblesJSON.length(); i++) {
//				JSONObject bibleJSON = biblesJSON.getJSONObject(i);
//				ABSBible bible = new ABSBible();
//				bible.setId(bibleJSON.getString("id"));
//				bible.setName(bibleJSON.getString("name"));
//				bible.setAbbreviation(bibleJSON.getString("abbreviation"));
//				bible.setLanguage(bibleJSON.getString("lang_name"));
//				bible.setLanguageEnglish(bibleJSON.getString("lang_name_eng"));
//				bible.setCopyright(bibleJSON.getString("copyright"));
//
//				bibles.put(bible.getId(), bible);
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
//
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		error.printStackTrace();
//		if(listener != null) {
//			listener.responseFinished(false);
//		}
//	}
}
