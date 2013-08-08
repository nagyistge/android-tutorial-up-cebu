package com.example.iinstagraam;

import org.json.JSONException;

import com.example.iinstagraam.model.APIError;
import com.example.iinstagraam.model.User;
import com.example.iinstagraam.util.APIUtil;
import com.example.iinstagraam.util.ParserUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
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
	    
	    btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setProgressBarIndeterminateVisibility(true);
				new LoginAsync().execute();				
			}
		});
	}
	
	private class LoginAsync extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			String result = APIUtil.login(txtEmail.getText().toString(), txtPassword.getText().toString());
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			setProgressBarIndeterminateVisibility(false); 
			String error_message = "";
			if (result == null) {
				error_message = "Unknown error!";
			}
			else {
				APIError error = ParserUtil.getAPIError(result);
				if (error != null) {
					error_message = error.getMessage();
				} 
				else {
					try {
						User user = ParserUtil.getUser(result); // We don't use it for now...
						Intent intent = new Intent().setClass(getApplicationContext(), MainActivity.class);
						startActivity(intent);
						
					} catch (JSONException e) {
						error_message = "Unknown error!";
						e.printStackTrace();
					}
				}
			}
			
			if (!error_message.equals("")){
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
				alertDialogBuilder.setMessage(error_message);
				alertDialogBuilder.setTitle("Login Error");
				alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				alertDialogBuilder.setCancelable(true);
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		}
		
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
