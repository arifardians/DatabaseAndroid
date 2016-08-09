package com.example.databaseandroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OperasiDatabase extends SQLiteOpenHelper{
	private static final String NAMA_DATABASE = "dbMahasiswa";
	private static final String NAMA_TABLE = "biodata";
	
	public OperasiDatabase(Context context) {
		super(context, NAMA_DATABASE, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	//method createTable untuk membuat table biodata
	public void createTable(SQLiteDatabase db){
		db.execSQL("CREATE TABLE if not exists "+NAMA_TABLE+" (nim VARCHAR(20) PRIMARY KEY, " +
				"nama varchar(50), alamat TEXT);");
	}

	public Cursor selectBiodata(SQLiteDatabase db, String sql){
		Cursor cursor = db.rawQuery(sql,null);
		return cursor;
	}
	//method insertBiodata untuk mengisikan data ke biodata.
	public void insertBiodata(SQLiteDatabase db, String[] data){
		ContentValues cv=new ContentValues();
		cv.put("nim", data[0]);
		cv.put("nama", data[1]);
		cv.put("alamat", data[2]);
		db.insert(NAMA_TABLE,null,cv);
	}
	
	//method updateBiodata untuk mengisikan data ke biodata.
	public void updateBiodata(SQLiteDatabase db, String[] data){
		ContentValues cv=new ContentValues();
		cv.put("nama", data[1]);
		cv.put("alamat", data[2]);
		String whereClause = "nim=?";
		String[] whereArgs = new String[] {String.valueOf(data[0])};
		db.update(NAMA_TABLE, cv, whereClause, whereArgs);
	}
	//method deleteBiodata untuk mengisikan data ke biodata.
	public void deleteBiodata(SQLiteDatabase db, String nim){
		String whereClause = "nim=?";
		String[] whereArgs = new String[] {String.valueOf(nim)};
		db.delete(NAMA_TABLE, whereClause, whereArgs);
	}
	public void deleteAllBiodata(SQLiteDatabase db){
		db.delete(NAMA_TABLE, null, null);
	}
}