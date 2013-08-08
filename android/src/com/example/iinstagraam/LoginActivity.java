package com.example.iinstagraam;

import com.example.iinstagraam.util.APIUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public final static int REGISTER_ACTION = 1;
	
	Context mContext = this;
	
	EditText txtEmail;
	EditText txtPassword;
	Button btnLogin;
	Button btnRegister;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login);
	    
	    txtEmail = (EditText) findViewById(R.id.email);
	    txtPassword = (EditText) findViewById(R.id.password);
	    btnLogin = (Button) findViewById(R.id.btnLogin);
	    btnRegister = (Button) findViewById(R.id.btnRegister);
	    
	    btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(getApplicationContext(), RegisterActivity.class);
				startActivityForResult(intent, REGISTER_ACTION);
			}
		});
	    
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REGISTER_ACTION) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(mContext, 
					"Registration successful! Please login to proceed.", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(mContext, 
					"Registration cancelled!", Toast.LENGTH_LONG).show();
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
