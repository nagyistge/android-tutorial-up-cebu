package com.example.iinstagraam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegisterActivity extends Activity {
	
	final static String TAG = "RegisterActivity";
	Context mContext = this;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.register);
	    
	    WebView wv = (WebView) findViewById(R.id.wvRegister);
	    wv.getSettings().setJavaScriptEnabled(true);
	    wv.loadUrl(getString(R.string.register_url));
	    
	    RegisterWebViewClient registerWebViewClient = new RegisterWebViewClient();
	    wv.setWebViewClient(registerWebViewClient);
	}

	final class RegisterWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			Log.d(TAG, url);
			if (url.equals(mContext.getString(R.string.register_url_success))) {
				Log.d(TAG, "closing RegisterActivity");
				finish();
				return true;
			} else {
				return super.shouldOverrideUrlLoading(view, url);
			}
		}
		
	}
}

