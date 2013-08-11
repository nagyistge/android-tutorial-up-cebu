package com.example.iinstagraam.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.iinstagraam.R;
import com.example.iinstagraam.model.Photo;
import com.example.iinstagraam.util.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhotoAdapter extends ArrayAdapter<Photo>{

	private static final String TAG = "PhotoAdapter";
	Context mContext;
	ArrayList<Photo> mPhotos;
	int mResourceId;
	
	float mDensity;
	float mHeight;
	float mWidth;
	DisplayMetrics mOutMetrics; 
	
	public void addPhoto(Photo photo) {
		mPhotos.add(0, photo);
		Log.d(TAG, "Adding photo to Adapter...");
		notifyDataSetChanged();
	}
	
	public PhotoAdapter(Context context, ArrayList<Photo> objects) {
		super(context, R.layout.stream_item , objects);
		mContext = context;
		mPhotos = objects;
		
		Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		mOutMetrics = new DisplayMetrics ();
		display.getMetrics(mOutMetrics);
		
		mDensity  = mContext.getResources().getDisplayMetrics().density;
		mHeight = mOutMetrics.heightPixels / mDensity;
		mWidth  = mOutMetrics.widthPixels / mDensity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PhotoHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.stream_item , null);
			
			holder = new PhotoHolder();
			holder.ivPhoto = (ImageView)row.findViewById(R.id.ivPhoto);
			holder.txtCaption = (TextView)row.findViewById(R.id.txtCaption);
			holder.txtDate = (TextView)row.findViewById(R.id.txtDate);
			holder.txtName = (TextView)row.findViewById(R.id.txtName);
			row.setTag(holder);
		}
		else {
			row = convertView;
		}
		holder = (PhotoHolder) row.getTag();
		Photo photo = mPhotos.get(position);
		holder.txtCaption.setText(photo.getCaption());
		holder.txtName.setText(photo.getUser_name());
		//holder.txtDate.setText(photo.getDate_uploaded());
		
		if (holder.ivPhoto != null) {
			if (mPhotos.get(position).getBitmap() == null){
				new ImageDownloaderTask(holder.ivPhoto, position).execute(photo.getPhoto_url());
				// set to default image while still downloading the images
				holder.ivPhoto.setImageDrawable(
						holder.ivPhoto.getContext().getResources()
	                    .getDrawable(R.drawable.default_photo));
			}
			else {
				holder.ivPhoto.setImageBitmap(
						mPhotos.get(position).getBitmap());
			}
        }
		
		//RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ivPhoto.getLayoutParams(); 
	    //params.width = mOutMetrics.widthPixels;
	    //params.height = mOutMetrics.widthPixels;
	    //holder.ivPhoto.setLayoutParams(params);
		
		return row;
	}
	
	static class PhotoHolder {
		ImageView ivPhoto;
		TextView txtName;
		TextView txtDate;
		TextView txtCaption;
	}
	

	// http://javatechig.com/android/asynchronous-image-loader-in-android-listview/#2-creating-a-custom-listview-with-thumbnails
	class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	    private final WeakReference imageViewReference;
	    private final int position;
	    public ImageDownloaderTask(ImageView imageView, int position) {
	        imageViewReference = new WeakReference(imageView);
	        this.position = position;
	    }
	 
	    @Override
	    // Actual download method, run in the task thread
	    protected Bitmap doInBackground(String... params) {
	        // params comes from the execute() call: params[0] is the url.
	        return ImageUtil.downloadBitmap(params[0]);
	    }
	 
	    @Override
	    // Once the image is downloaded, associates it to the imageView
	    protected void onPostExecute(Bitmap bitmap) {
	        if (isCancelled()) {
	            bitmap = null;
	        }
	 
	        if (imageViewReference != null) {
	            ImageView imageView = (ImageView) imageViewReference.get();
	            if (imageView != null) {
	 
	                if (bitmap != null) {
	                	imageView.setImageBitmap(ImageUtil.getScaledBitmap(mContext, bitmap, mWidth));
	                	bitmap = ImageUtil.getScaledBitmap(mContext, bitmap, mWidth);
	                    mPhotos.get(position).setBitmap(bitmap);
	                } else {
	                	
	                    //imageView.setImageDrawable(imageView.getContext().getResources()
	                    //        .getDrawable(R.drawable.photo_default));
	                }
	            }
	 
	        }
	    }
	 
	}

}

