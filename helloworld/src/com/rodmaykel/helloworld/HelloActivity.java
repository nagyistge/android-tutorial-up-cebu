package com.rodmaykel.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends Activity {

	TextView textHello;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.hello);
	    textHello = (TextView) findViewById(R.id.textView1);
	    
	    Intent intent = getIntent();
	    
	    String name = intent.getStringExtra("name");
	    
	    textHello.setText("Hello " + name);
	    
	    // TODO Auto-generated method stub
	}

}
