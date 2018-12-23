package com.pactera.financialmanager.db;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 对Sqlite数据库进行操作的业务bean
 */
public class CateDao {
	
	private CateDownLoadHelper openHelper;
	
	public CateDao(Context context){
		openHelper=new CateDownLoadHelper(context, "cate.db", null, 1);
	}
	
	/**
	 * 根据valueDesc 获取相关所有数据条目 的集合
	 * @param valueDesc 分类码描述
	 * @return
	 */
	public ArrayList<CateInfo> getListData(String valueDesc){
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select cateLabel, cateName,cateValue,createDate,lastUpdateDt,validFlag,valueDesc from CateCode where valueDesc=?", new String[]{valueDesc});
	    ArrayList<CateInfo> cateInfos=new ArrayList<CateInfo>();
	    while(cursor.moveToNext()){
	    	String cateLabelValue = cursor.getString(cursor.getColumnIndex("cateLabel"));
	    	String cateNameValue = cursor.getString(cursor.getColumnIndex("cateName"));
	    	String cateValueValue = cursor.getString(cursor.getColumnIndex("cateValue"));
	    	String createDateValue = cursor.getString(cursor.getColumnIndex("createDate"));
	    	String lastUpdateDtValue = cursor.getString(cursor.getColumnIndex("lastUpdateDt"));
	    	String validFlagValue = cursor.getString(cursor.getColumnIndex("validFlag"));
	    	String valueDescValue = cursor.getString(cursor.getColumnIndex("valueDesc"));
	    	CateInfo cateInfo=new CateInfo(cateLabelValue,cateNameValue,cateValueValue,createDateValue,lastUpdateDtValue,validFlagValue,valueDescValue);
	    	cateInfos.add(cateInfo);
	    	Log.i("cateLabel+cateInfos.size()", cateInfo.getCateLabel()+"  "+cateInfos.size());
	    }
	    cursor.close();
		db.close();
		return cateInfos;
	}
	
	/**
	 * 根据valueDesc 获取相关所有数据条目 的集合
	 * @param valueDesc 分类码描述
	 * @return
	 */
	public HashMap<String,String> getMapData(String valueDesc){
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select cateLabel, cateValue from CateCode where valueDesc=?", new String[]{valueDesc});
		HashMap<String,String> map = new HashMap<String, String>();
		while(cursor.moveToNext()){
			map.put(cursor.getString(cursor.getColumnIndex("cateValue")),cursor.getString(cursor.getColumnIndex("cateLabel")));
//			Log.i("cateLabel+cateInfos.size()",cursor.getString(cursor.getColumnIndex("cateValue"))+"==="+cursor.getString(cursor.getColumnIndex("cateLabel")) );
		}
		cursor.close();
		db.close();
		return map;
	}
	
	/**
	 * 根据valueDesc 获取相关所有数据条目 的集合
	 * @param {"cateLabel":"摄影","cateName":"interest","cateValue":"0","createDate":"2014-09-23 00:00:00","lastUpdateDt":"2014-09-23 00:00:00","validFlag":"01","valueDesc":null},
	 * @return
	 */
	public HashMap<String,String> getMapDataBaseName(String cateName){
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select cateLabel, cateValue from CateCode where cateName=?", new String[]{cateName});
		HashMap<String,String> map = new HashMap<String, String>();
		while(cursor.moveToNext()){
			map.put(cursor.getString(cursor.getColumnIndex("cateValue")),cursor.getString(cursor.getColumnIndex("cateLabel")));
			Log.i("cateLabel+cateInfos.size()",cursor.getString(cursor.getColumnIndex("cateValue"))+"==="+cursor.getString(cursor.getColumnIndex("cateLabel")) );
		}
		cursor.close();
		db.close();
		return map;
	}
	
	/**
	 * 保存每条数据
	 * @param cateInfo 一条分类码数据数据 	{"cateLabel":"不可预约","cateName":"bookingFlag","cateValue":"0","validFlag":"01","valueDesc":"是否可预约"}
	 */
	public void save(CateInfo cateInfo){//int threadid, int position
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.beginTransaction();
		try{
				db.execSQL("insert into CateCode(cateLabel, cateName,cateValue,createDate,lastUpdateDt,validFlag,valueDesc) values(?,?,?,?,?,?,?)",
						new Object[]{cateInfo.getCateLabel(), cateInfo.getCateName(),cateInfo.getCateValue(),cateInfo.getCreateDate(),cateInfo.getLastUpdateDt(), cateInfo.getValidFlag(),cateInfo.getValueDesc()});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
	}
	
	/**
	 * 保存一个集合的数据//是为了改善 按条来存数据时要不听地打开关闭数据库造成的缓慢情况。
	 * @param cateInfo 一条分类码数据数据 	{"cateLabel":"不可预约","cateName":"bookingFlag","cateValue":"0","validFlag":"01","valueDesc":"是否可预约"}
	 */
	public void saves(ArrayList<CateInfo> list){//int threadid, int position
		SQLiteDatabase db = openHelper.getWritableDatabase();
		db.beginTransaction();
		try{
			for(int i=0;i<list.size();i++){
				CateInfo cateInfo = list.get(i);
				db.execSQL("insert into CateCode(cateLabel,cateName,cateValue,createDate,lastUpdateDt,validFlag,valueDesc) values(?,?,?,?,?,?,?)",
						new Object[]{cateInfo.getCateLabel(), cateInfo.getCateName(),cateInfo.getCateValue(),cateInfo.getCreateDate(),cateInfo.getLastUpdateDt(), cateInfo.getValidFlag(),cateInfo.getValueDesc()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		db.close();
	}

	//清空表
	public void clearTable() {
		SQLiteDatabase db = openHelper.getWritableDatabase();
			try {
			db.execSQL("DELETE FROM CateCode");
			db.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'TableName' ");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			db.close();
		}
	}
}
