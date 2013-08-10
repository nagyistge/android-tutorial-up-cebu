package com.example.iinstagraam.util;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.iinstagraam.model.APIError;
import com.example.iinstagraam.model.Photo;
import com.example.iinstagraam.model.Token;
import com.example.iinstagraam.model.User;

public class ParserUtil {

	private static final String TAG = "ParserUtil";
	/*
	{
	"error" {
    	"code": "<code>",
    	"message": "<message>"
    	}
    }
	*/
	public static APIError getAPIError(String str) {
		try {
			APIError error = new APIError();
			JSONObject json = new JSONObject(str);
			JSONObject json_error = json.getJSONObject("error");
			error.setCode(json_error.getInt("code"));
			error.setMessage(json_error.getString("message"));
			return error;
		} catch (JSONException e) {
			Log.i(TAG, "getAPIError exception");
			return null;
		}
	}
	
	/*
	{
	"token": "<token>",
	"token_expiry": "<expiry in ms>",
	"profile": {
  		"email": "<user's email>",
  		"name": "<user's name>"
  		}
	}
	*/
	public static Token getToken(String str) throws JSONException {
		Token token = new Token();
		JSONObject json = new JSONObject(str);
		token.setToken(json.getString("token"));
		token.setExpiry(new Date()); 
		return token;
	}
	
	public static User getUser(String str) throws JSONException {
		User user = new User();
		JSONObject json = new JSONObject(str);
		JSONObject json_profile = json.getJSONObject("profile");
		user.setEmail(json_profile.getString("email"));
		user.setName(json_profile.getString("name"));
		return user;
	}
	
	/*
	[
	{
		"photo_id": "<id>",
		"photo_url": "<photo_url>",
		"date_uploaded": "<date_uploaded>",
		"caption": "<caption>"
		"user_name": "<name of the owner of the photo>"
	}, 
	]
	*/
	public static ArrayList<Photo> getPhotos(String str) throws JSONException {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		JSONArray json = new JSONArray(str);
		
		for (int i=0; i<json.length(); i++) {
			Photo photo = new Photo();
			JSONObject item = json.getJSONObject(i);
			photo.setPhoto_url(item.getString("photo_url"));
			photo.setCaption(item.getString("caption"));
			photo.setUser_name(item.getString("user_name"));
			//photo.setDate_uploaded(item.getString("date_uploaded"));
			photos.add(photo);
		}
		return photos;
	}
}
