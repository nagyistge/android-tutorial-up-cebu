package com.example.iinstagraam;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends Activity {
	
	private static String TAG = "SplashActivity";
	private static int SPLASH_DURATION = 5000;
	
	Context mContext;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
	    
	    ActionBar ab= getActionBar();
	    ab.hide();
	    
	    mContext = this;
	    
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable(){
			@Override
			public void run() {
	    		Toast.makeText(mContext, "TODO: Launch next activity", Toast.LENGTH_LONG).show();
			}
	    }, SPLASH_DURATION);
	    
	}
}
