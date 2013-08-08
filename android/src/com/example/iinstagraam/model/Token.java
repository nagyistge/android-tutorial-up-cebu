package com.example.iinstagraam.model;

import java.util.Date;

public class Token {
	
	public String token;
	public Date expiry;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiry() {
		return expiry;
	}
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
}
