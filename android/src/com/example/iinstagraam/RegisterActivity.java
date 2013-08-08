package com.example.iinstagraam;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class RegisterActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.register);
	    
	    WebView wv = (WebView) findViewById(R.id.wvRegister);
	    wv.getSettings().setJavaScriptEnabled(true);
	    wv.loadUrl(getString(R.string.register_url));
	}

}
