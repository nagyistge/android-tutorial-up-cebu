package com.example.iinstagraam.adapter;

import java.lang.ref.WeakReference;

import com.example.iinstagraam.R;
import com.example.iinstagraam.data.MyPhotoDBAdapter;
import com.example.iinstagraam.util.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyPhotoAdapter extends SimpleCursorAdapter {

	Context mContext;
	Cursor mCursor;
	int mLayout;
	float mWidth;
	private DisplayMetrics mOutMetrics;
	private float mDensity;
	
	public MyPhotoAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mContext = context;
		mCursor = c; 
		mLayout = layout;
		Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		mOutMetrics = new DisplayMetrics ();
		display.getMetrics(mOutMetrics);
		
		mDensity  = mContext.getResources().getDisplayMetrics().density;
		mWidth  = mOutMetrics.widthPixels / mDensity;
	}

	// http://thinkandroid.wordpress.com/2010/01/11/custom-cursoradapters/
	@Override
	public void bindView(View v, Context context, Cursor c) {
		int photoUrlCol = c.getColumnIndex(MyPhotoDBAdapter.COL_PHOTOURL);
		String photoUrl = c.getString(photoUrlCol);
		ImageView ivPhoto = (ImageView) v.findViewById(R.id.ivThumbPhoto);
		
		Bitmap bmp = BitmapFactory.decodeFile(photoUrl);
		ivPhoto.setImageBitmap(ImageUtil.getScaledSquareBitmap(mContext, bmp, mWidth/3));
		
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivPhoto.getLayoutParams(); 
	    params.width = params.width;
	    params.height = params.width;
	    ivPhoto.setLayoutParams(params);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Cursor c = getCursor();

		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(mLayout, parent, false);

		//int nameCol = c.getColumnIndex(MyPhotoDBAdapter.COL_NAME);
		//int dateCol = c.getColumnIndex(MyPhotoDBAdapter.COL_DATEUPLOADED);
		int photoUrlCol = c.getColumnIndex(MyPhotoDBAdapter.COL_PHOTOURL);
		//int captionCol = c.getColumnIndex(MyPhotoDBAdapter.COL_CAPTION);

		//String name = c.getString(nameCol);
		//String date = c.getString(dateCol);
		String photoUrl = c.getString(photoUrlCol);
		//String caption = c.getString(captionCol);

		ImageView ivPhoto = (ImageView) v.findViewById(R.id.ivThumbPhoto);
		
		Bitmap bmp = BitmapFactory.decodeFile(photoUrl);
		ivPhoto.setImageBitmap(ImageUtil.getScaledSquareBitmap(mContext, bmp, mWidth/3));
		
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivPhoto.getLayoutParams(); 
	    params.width = params.width;
	    params.height = params.width;
	    ivPhoto.setLayoutParams(params);
		
		return v;
	}
	
}
