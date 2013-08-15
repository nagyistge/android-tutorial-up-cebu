package com.example.iinstagraam;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.iinstagraam.adapter.PhotoAdapter;
import com.example.iinstagraam.data.MyPhotoDBAdapter;
import com.example.iinstagraam.model.APIError;
import com.example.iinstagraam.model.Photo;
import com.example.iinstagraam.util.APIUtil;
import com.example.iinstagraam.util.AppSettings;
import com.example.iinstagraam.util.ParserUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
	
	private Uri mFileUri;
	private String mCaption;
	
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
			    Log.d(TAG, "Absolute path: " + image.getAbsolutePath());
			    Uri fileUri = Uri.fromFile(image);
			    mFileUri = fileUri;
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			    Log.d(TAG, mFileUri.toString());
			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
	    
	    btnMore = (Button) findViewById(R.id.btnMore);
	    btnMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setProgressBarIndeterminateVisibility(true);
				new PhotoStreamAsync().execute();
				btnMore.setEnabled(false);
				
			}
		});
	    
	    txtName = (TextView) findViewById(R.id.txtName);
	    txtName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(getApplicationContext(), MyPhotosActivity.class);
				startActivity(intent);
				
			}
		});
	    
	    setProgressBarIndeterminateVisibility(true);
	    new PhotoStreamAsync().execute();
	}
	
	// http://developer.android.com/guide/topics/media/camera.html
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            if (data != null) {
	            	mFileUri = data.getData(); // some phones support this, others not.
	            }
	        	
	        	// Image captured and saved to fileUri specified in the Intent
	            AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            builder.setMessage("Enter caption:");
	            builder.setCancelable(false);

	            final EditText editCaption = new EditText(mContext);
	            builder.setView(editCaption);

	            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    mCaption = editCaption.getText().toString();
	                    new UploadAsync().execute();
	    	            ((Activity) mContext).setProgressBarIndeterminateVisibility(true);
	                }
	            })
	            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });
	            AlertDialog alert = builder.create();
	            alert.show();
	              
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
			Log.d(TAG, "doInBackground: PhotoStreamAsync");
			
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
						btnMore.setEnabled(true);
						if (mPage == 1){
							// Initialize the Images
							ArrayList<Photo> photos = ParserUtil.getPhotos(result);
							mPhotos.addAll(photos);
							mAdapter = new PhotoAdapter(mContext, mPhotos);
							photoList.setAdapter(mAdapter);
						}
						else {
							ArrayList<Photo> photos = ParserUtil.getPhotos(result);
							if (photos.size() == 0) {
								Toast.makeText(mContext, 
										"No more photos to load", Toast.LENGTH_LONG).show();
								btnMore.setEnabled(false);
							}
							mAdapter.addPhotos(photos);
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
	
	private class UploadAsync extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			
			String result = APIUtil.uploadPhoto(mFileUri, mCaption, AppSettings.getString(AppSettings.TOKEN, mContext));
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			((Activity) mContext).setProgressBarIndeterminateVisibility(false);
			if (result==null) {
				// TODO: Error
			}
			else {
				APIError error = ParserUtil.getAPIError(result);
				if (error!=null){
					// TODO: Error
				}
				else {
					// TODO: Parse the result and add to the list of images being shown.
					try {
						Log.d(TAG, "Successful in adding photo");
						Photo photo = ParserUtil.getPhoto(result);
						Toast.makeText(mContext, 
								"Upload successful", Toast.LENGTH_LONG).show();
						mAdapter.addPhoto(photo);
						//photo.setPhoto_url(mFileUri.getPath());
						MyPhotoDBAdapter db = new MyPhotoDBAdapter(mContext);
						db.open();
						db.addPhoto(photo);
						db.close();
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
			}
		}
		
		
	}
	
}