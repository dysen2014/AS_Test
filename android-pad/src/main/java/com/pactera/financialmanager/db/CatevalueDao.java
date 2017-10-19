package com.pactera.financialmanager.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.pactera.financialmanager.entity.CatevalueEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 码值查询DAO
 *
 * @author JAY
 */
public class CatevalueDao {
    private Context context;

    // 数据库存储路径
    String filePath = "/hbcrm_dbank/db/Catevalue.db";
    // 数据库存放的文件夹
    String pathStr = "/hbcrm_dbank/db";

    SQLiteDatabase database;

    public CatevalueDao(Context context) {
        this.context = context;
        database = openDatabase(context);
    }


    public SQLiteDatabase openDatabase(Context context) {
        File pathDb = Environment.getExternalStorageDirectory();
//		System.out.println("filePath:" + filePath);
        File jhPath = new File(pathDb, filePath);
        // 查看数据库文件是否存在
        if (jhPath.exists()) {
            // 存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            // 不存在先创建文件夹
            File path = new File(pathDb, pathStr);
            if (path.mkdirs()) {
                System.out.println("创建成功");
            } else {
                System.out.println("创建失败");
            }
            ;
            try {
                // 得到资源
                AssetManager am = context.getAssets();
                // 得到数据库的输入流
                InputStream is = am.open("Catevalue.db");
                // 用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                // 创建byte数组 用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                // 最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            // 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }

    // 查询对应码值
    public List<CatevalueEntity> getCatevalue(String CatevalueCode) {
        List<CatevalueEntity> list = null;

        String[] selectionArgs = {CatevalueCode};
        String[] columns = {"VALUE", "LABEL"};
        String selection = "CATENAME=?";

        if(database == null){
            database = openDatabase(context);
        }
        Cursor cursor = database.query("IFS_CATEVALUE", columns, selection,
                selectionArgs, null, null, "VALUE ASC");
        list = new ArrayList<CatevalueEntity>();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                CatevalueEntity m = new CatevalueEntity();
                m.setValue(cursor.getString(cursor.getColumnIndex("VALUE")));
                m.setLabel(cursor.getString(cursor.getColumnIndex("LABEL")));

                list.add(m);
            }
            cursor.close();
        }

        return list;
    }

    // 查询对应码值信息
    public String getLabel(String CatevalueCode, String index) {
        String returnInfo = "";

        String[] selectionArgs = {CatevalueCode, index};
        String[] columns = {"LABEL"};
        String selection = "CATENAME=? and VALUE=?";
//12.26添加
        if(database == null){
            database = openDatabase(context);
        }
        Cursor cursor = database.query("IFS_CATEVALUE", columns, selection, selectionArgs, null, null, null);
//		if (cursor != null) {
//        if (cursor != null && cursor.moveToFirst() && cursor.moveToNext()) {
//            while (cursor.moveToNext()) {
//                CatevalueEntity m = new CatevalueEntity();
//                returnInfo = cursor.getString(cursor.getColumnIndex("LABEL"));
//
//            }
//        }
        if (null != cursor) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                returnInfo = cursor.getString(cursor.getColumnIndex("LABEL"));
            }
        }

        cursor.close();
        return returnInfo;
    }

    public void closeDataBase() {
        database.close();
    }
}
