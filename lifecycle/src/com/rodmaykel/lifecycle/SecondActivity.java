package com.rodmaykel.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {

	private static final String TAG = "SecondActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.activity_second);
	    Log.d(TAG, "onCreate()");
	}


	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause()");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d(TAG, "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume()");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop()");
		super.onStop();
	}

}
