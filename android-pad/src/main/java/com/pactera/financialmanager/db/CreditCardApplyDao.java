package com.pactera.financialmanager.db;

import android.content.Context;
import android.os.Environment;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.pactera.financialmanager.entity.CreditCardApplyEntity;
import com.pactera.financialmanager.entity.CreditCardApplyEntity.PicEntity;

import java.io.File;
import java.util.List;

/**
 * Created by xh on 2015/12/11.
 */
public class CreditCardApplyDao {

    private static final String DB_PATH = "/hbcrm_dbank/credit_db/";

    private static final String DB_FILE_NAME = "apply.db";

    private Context context;

    private DbUtils dbUtils;

    public CreditCardApplyDao(Context context){
        this.context = context;
        this.dbUtils = DbUtils.create(context, new File(Environment.getExternalStorageDirectory(), DB_PATH).getAbsolutePath(), DB_FILE_NAME);
    }

    public void insert(CreditCardApplyEntity e) {

        dbUtils.configAllowTransaction(true);
        try {
            if (!dbUtils.saveBindingId(e)) {
                return;
            }
        } catch (DbException e1) {
            e1.printStackTrace();
            return;
        }

        List<PicEntity> pics = e.getAttachedPics();
        if (pics != null) {

            for (PicEntity p : pics) {
                p.setEid(e.getId());
            }

            try {
                dbUtils.saveAll(pics);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update(CreditCardApplyEntity e) {
        delById(e.getId());
        insert(e);
    }

    public List<CreditCardApplyEntity> loadList(String userId) {
        try {
            return dbUtils.findAll(Selector.from(CreditCardApplyEntity.class).where("userId", "=", userId).orderBy("last_update_time", true));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }


    public CreditCardApplyEntity loadById(long id) {
        CreditCardApplyEntity e = null;
        try {
            e = dbUtils.findFirst(Selector.from(CreditCardApplyEntity.class).where("id", "=", id));

            List<PicEntity> picList = dbUtils.findAll(Selector.from(PicEntity.class).where("eid", "=", e.getId()));
            e.setAttachedPics(picList);

        } catch (DbException dbe) {
            dbe.printStackTrace();
        }

        return e;
    }

    // transactional
    public void delById(long id) {
        dbUtils.configAllowTransaction(false);
        try {
            dbUtils.getDatabase().beginTransaction();
            dbUtils.deleteById(CreditCardApplyEntity.class, id);
            dbUtils.delete(PicEntity.class, WhereBuilder.b("eid", "=", id));

            dbUtils.getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dbUtils.getDatabase().endTransaction();
            dbUtils.configAllowTransaction(true);
        }
    }
}
