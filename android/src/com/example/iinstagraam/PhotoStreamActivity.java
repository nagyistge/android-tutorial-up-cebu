package com.example.iinstagraam;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PhotoStreamActivity extends Activity {

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
	    
	    new PhotoStreamAsync().execute();
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
