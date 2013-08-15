package com.example.iinstagraam;

import com.example.iinstagraam.adapter.MyPhotoAdapter;
import com.example.iinstagraam.data.MyPhotoDBAdapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

public class MyPhotosActivity extends Activity {

	Context mContext;
	GridView gridPhotos;
	MyPhotoAdapter mAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.myphoto_grid);
	    
	    mContext = this;
	    
	    MyPhotoDBAdapter db = new MyPhotoDBAdapter(mContext);
	    db.open();
	    Cursor photos = db.getMyPhotos();
	    
	    gridPhotos = (GridView) findViewById(R.id.gridPhotos);
	    mAdapter = new MyPhotoAdapter(mContext, R.layout.myphoto_item, photos, MyPhotoDBAdapter.MYPHOTOS_COL, null, 0);
	    
	    gridPhotos.setAdapter(mAdapter);
	    db.close();
	    gridPhotos.setClickable(false);
	}

}
