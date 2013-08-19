package com.example.iinstagraam.data;

import com.example.iinstagraam.model.Photo;
import com.example.iinstagraam.util.DateUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyPhotoDBAdapter {


	private static final String DATABASE_NAME = "Iinstagraam_MyPhotos";
	private static final int DATABASE_VERSION = 1;
	private static final String MYPHOTOS_TABLE = "MyPhotos";
	
	
	public static final String COL_ID = "_id";
	public static final String COL_NAME = "name";
	public static final String COL_PHOTOURL = "photo_url";
	public static final String COL_DATEUPLOADED = "date_uploaded";
	public static final String COL_CAPTION = "caption";
	
	public static final String[] MYPHOTOS_COL = {
		COL_ID,
		COL_NAME,
		COL_PHOTOURL,
		COL_DATEUPLOADED,
		COL_CAPTION
	};
	
	Context mContext;
	DBHelper mDBHelper;
	SQLiteDatabase mDb;
	
	private static final String DATABASE_CREATE =
			 "CREATE TABLE if not exists " + MYPHOTOS_TABLE + " (" +
					  COL_ID + " integer PRIMARY KEY autoincrement," +
					  COL_NAME + " text," +
					  COL_PHOTOURL + " text," +
					  COL_DATEUPLOADED + " text," +
					  COL_CAPTION + " text" +
					  ");";
	
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + MYPHOTOS_TABLE);
			onCreate(db);
		}
	}
	
	public MyPhotoDBAdapter(Context context) {
		mContext = context;
	}
	
	public MyPhotoDBAdapter open() throws SQLException {
		mDBHelper = new DBHelper(mContext);
		mDb = mDBHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		if(mDBHelper != null) {
			mDBHelper.close();
		}
	}
	
	public void addPhoto(Photo photo) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, photo.getUser_name());
		values.put(COL_DATEUPLOADED, DateUtil.dateToString(photo.getDate_uploaded()));
		values.put(COL_PHOTOURL, photo.getPhoto_url());
		values.put(COL_CAPTION, photo.getCaption());
		
		mDb.insert(MYPHOTOS_TABLE, null, values);
	}
	
	public Cursor getMyPhotos() {
		Cursor mCursor = null;
		
		mCursor = mDb.query(MYPHOTOS_TABLE, MYPHOTOS_COL, null, null, null, null, null);
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;		
	}
	
}

/*
 * Usage:
 * 
 * db = new MyPhotoDBAdapter(this);
 * db.open();
 * db.close();
 * 
 */