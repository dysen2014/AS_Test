package com.dysen.common_res.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dysen on 2017/8/25.
 */

public class DBManager {
	private String DB_NAME = "Industry.db";
	private Context mContext;

	public DBManager(Context mContext) {
		this.mContext = mContext;
	}
	//把assets目录下的db文件复制到dbpath下
	public SQLiteDatabase initDBManager(String packName) {
		String dbPath = "/data/data/" + packName
				+ "/databases/" + DB_NAME;
		if (!new File(dbPath).exists()) {
			try {
				FileOutputStream out = new FileOutputStream(dbPath);
				InputStream in = mContext.getAssets().open("Industry.db");
				byte[] buffer = new byte[1024];
				int readBytes = 0;
				while ((readBytes = in.read(buffer)) != -1)
					out.write(buffer, 0, readBytes);
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
	}
	//查询
	public Cursor query(SQLiteDatabase sqliteDB, String tableName, String[] columns, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		try {
			cursor = sqliteDB.query(tableName, columns, selection, selectionArgs, null, null, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

}
