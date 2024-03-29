package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static String REST_CONSUMER_KEY;       // Change this
	public static String REST_CONSUMER_SECRET ; // Change this



	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				context.getString(R.string.API_KEY),
				context.getString(R.string.API_KEY_SECRET),
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));

		/**
		 * the super() call should be the first thing in the constructor.
		 * However the keys must remain hidden. That is why I am assigning values
		 * here.
		 */
		REST_CONSUMER_KEY = context.getString(R.string.API_KEY);
		REST_CONSUMER_SECRET = context.getString(R.string.API_KEY_SECRET);

	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		client.get(apiUrl, params, handler);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);

		if(maxId != 0) {
			//we pass the id to get tweets with an id less than the maxID(older tweets)
			params.put("max_id", maxId);
		}
		client.get(apiUrl, params, handler);
	}

	public void sendTweet(String message, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");

		//can specify query string params directly or through RequestParams
		RequestParams params = new RequestParams();
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}
//https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-show.html
	public void getCurrentUserObject(String screen, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");

		RequestParams params = new RequestParams();
		params.put("screen_name", screen );

		client.get(apiUrl, params, handler);
	}

	//https://developer.twitter.com/en/docs/ads/campaign-management/api-reference/authenticated-user-access.html
	public void getCurrentUserData(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("accounts/:account_id/authenticated_user_access.json");
		RequestParams params = new RequestParams();

		client.get(apiUrl, params, handler);

	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
