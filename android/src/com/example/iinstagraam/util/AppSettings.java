package com.example.iinstagraam.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppSettings {
	
	private static String PREFS_NAME = "Iinstagraam_settings";
	private static String TAG = "AppSettings";
	
	public static String TOKEN = "token";
	public static String EMAIL = "email";
	public static String PASSWORD = "password";
	
	public static String getString(String param, Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		String value = settings.getString(param, "");
		Log.d(TAG, "getString( "+ param + ") = " + value);
		return value;
	}
	
	public static void setString(String param, String value, Context context) {
		Log.d(TAG, "setString("+ param + "," + value + ")");
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(param, value);
		editor.commit();
	}
	
	public static void delete(String param, Context context) {
		Log.d(TAG, "delete(" + param + ")");
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(param);
		editor.commit();		
	}

}
