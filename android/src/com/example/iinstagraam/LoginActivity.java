package com.example.iinstagraam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	Context mContext = this;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login);
	    
	    EditText txtEmail = (EditText) findViewById(R.id.email);
	    EditText txtPassword = (EditText) findViewById(R.id.password);
	    Button btnLogin = (Button) findViewById(R.id.btnLogin);
	    Button btnRegister = (Button) findViewById(R.id.btnRegister);
	    
	    btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
				
			}
		});
	}

}
