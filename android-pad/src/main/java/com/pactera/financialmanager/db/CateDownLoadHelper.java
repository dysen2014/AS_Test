package com.pactera.financialmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 下载分类码表保存到本地Sqlite数据库中
 */
public class CateDownLoadHelper extends SQLiteOpenHelper {

	public CateDownLoadHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS CateCode (id integer primary key autoincrement, "
				+ "cateLabel varchar(300),"
				+ "cateName varchar(300),"
				+ "cateValue varchar(300),"
				+ "createDate varchar(300),"
				+ "lastUpdateDt varchar(300),"
				+ "validFlag varchar(300),"
				+ "valueDesc varchar(300))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS CateCode");
		onCreate(db);
	}

}
