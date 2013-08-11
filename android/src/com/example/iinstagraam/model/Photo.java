package com.example.iinstagraam.model;

import java.util.Date;

import android.graphics.Bitmap;

public class Photo {
	public String photo_url;
	public Date date_uploaded;
	public String caption;
	public String user_name;
	public Bitmap bitmap;
	
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public Date getDate_uploaded() {
		return date_uploaded;
	}
	public void setDate_uploaded(Date date_uploaded) {
		this.date_uploaded = date_uploaded;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
}
