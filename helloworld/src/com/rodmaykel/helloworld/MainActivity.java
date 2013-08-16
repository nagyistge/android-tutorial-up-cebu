package com.rodmaykel.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Context mContext;
	EditText editName;
	Button btnSubmit;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    mContext = this;
	    
	    editName = (EditText) findViewById(R.id.editName);
	    btnSubmit = (Button) findViewById(R.id.btnSubmit);
	    
	    btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloActivity.class);
				intent.putExtra("name", editName.getText().toString());
				startActivity(intent);
				
			}
		});
	    
	}

}
