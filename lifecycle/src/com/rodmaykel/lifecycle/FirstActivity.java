package com.rodmaykel.lifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstActivity extends Activity {

	static final String TAG = "FirstActivity";
	Button btnStart;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_first);
		mContext = this;
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SecondActivity.class);
				startActivity(intent);
				
			}
		});
		Log.d(TAG, "onCreate()");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
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
