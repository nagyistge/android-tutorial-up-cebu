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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoAdapter extends ArrayAdapter<Photo>{

	Context mContext;
	ArrayList<Photo> mPhotos;
	int mResourceId;
	
	public PhotoAdapter(Context context, ArrayList<Photo> objects) {
		super(context, R.layout.stream_item , objects);
		mContext = context;
		mPhotos = objects;
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
            new ImageDownloaderTask(holder.ivPhoto).execute(photo.getPhoto_url());
        }	
		return row;
	}
	
	static class PhotoHolder {
		ImageView ivPhoto;
		TextView txtName;
		TextView txtDate;
		TextView txtCaption;
	}
	

}

// http://javatechig.com/android/asynchronous-image-loader-in-android-listview/#2-creating-a-custom-listview-with-thumbnails
class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference imageViewReference;
 
    public ImageDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference(imageView);
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
                    imageView.setImageBitmap(bitmap);
                } else {
                	
                    //imageView.setImageDrawable(imageView.getContext().getResources()
                    //        .getDrawable(R.drawable.photo_default));
                }
            }
 
        }
    }
 
}
