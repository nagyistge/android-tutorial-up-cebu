package com.example.iinstagraam.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class APIUtil {
	
	private static String TAG = "APIUtil";
	private static String BASE_URL = "http://54.251.186.10:3000";
	private static String LOGIN_URL = BASE_URL + "/user/login";
	private static String GETPHOTOS_URL = BASE_URL + "/photos";
	
	public static String login(String email, String password) {
		
		try {
			URL url = new URL(LOGIN_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");
			conn.connect();
			
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("email", email);
			jsonParam.put("password", password);
			
			OutputStream out = conn.getOutputStream();
			out.write(jsonParam.toString().getBytes());
			out.flush();
			out.close();
			
			InputStream in = new BufferedInputStream(conn.getInputStream());
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			String responeLine;
			StringBuilder responseBuilder = new StringBuilder();
			while ((responeLine = bufferedReader.readLine()) != null) {
				responseBuilder.append(responeLine);
			}
			String result = responseBuilder.toString(); 
			Log.d(TAG, result);
			return result;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String getPhotos(int page, String token) {
		
		try {
			URL url = new URL(GETPHOTOS_URL + '/' + page);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Authorization",token);
			
			conn.connect();
			
			InputStream in = new BufferedInputStream(conn.getInputStream());
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			String responeLine;
			StringBuilder responseBuilder = new StringBuilder();
			while ((responeLine = bufferedReader.readLine()) != null) {
				responseBuilder.append(responeLine);
			}
			String result = responseBuilder.toString(); 
			Log.d(TAG, result);
			return result;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		
	}
}
