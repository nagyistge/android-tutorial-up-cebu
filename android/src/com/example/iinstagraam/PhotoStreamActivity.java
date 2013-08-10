package com.example.iinstagraam;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.iinstagraam.adapter.PhotoAdapter;
import com.example.iinstagraam.model.APIError;
import com.example.iinstagraam.model.Photo;
import com.example.iinstagraam.util.APIUtil;
import com.example.iinstagraam.util.AppSettings;
import com.example.iinstagraam.util.ParserUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoStreamActivity extends Activity {

	protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	private static String TAG = "PhotoStreamActivity";
	private int mPage;
	
	private ArrayList<Photo> mPhotos;
	
	private ListView photoList;
	private TextView txtName;
	private ImageView ivAddPhoto;
	private Button btnMore;
	private PhotoAdapter mAdapter;
	
	private Context mContext;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.stream_list);
	    mPage = 1;
	    mPhotos = new ArrayList<Photo>();
	    mContext = this;
	    photoList = (ListView) findViewById(R.id.listPhotos);
	    
	    ivAddPhoto = (ImageView) findViewById(R.id.ivAddPhoto);
	    ivAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				File imagesFolder = new File(Environment.getExternalStorageDirectory(), "IinstagraamImages");
			    imagesFolder.mkdirs(); // <----
			    long unixTime = System.currentTimeMillis() / 1000L;
			    File image = new File(imagesFolder, unixTime + ".jpg");
			    Uri fileUri = Uri.fromFile(image);
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
	    
	    new PhotoStreamAsync().execute();
	}
	
	// http://developer.android.com/guide/topics/media/camera.html
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Image saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	            
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}

	private class PhotoStreamAsync extends AsyncTask<Integer, Void, String> {

		@Override
		protected String doInBackground(Integer... params) {
			setProgressBarIndeterminateVisibility(true);
			String result = APIUtil.getPhotos(mPage, AppSettings.getString(AppSettings.TOKEN, mContext));
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			setProgressBarIndeterminateVisibility(false);
			if (result==null) {
				// TODO: Error
			}
			else {
				APIError error = ParserUtil.getAPIError(result);
				if (error!=null){
					// TODO: Error
				}
				else {
					try {
						ArrayList<Photo> photos = ParserUtil.getPhotos(result);
						mPhotos.addAll(photos);
						if (mPage == 1){
							// Initialize the Images
							mAdapter = new PhotoAdapter(mContext, mPhotos);
							photoList.setAdapter(mAdapter);
						}
						else {
							
						}
						mPage++;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}
