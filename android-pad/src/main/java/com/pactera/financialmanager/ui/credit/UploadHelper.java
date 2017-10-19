package com.pactera.financialmanager.ui.credit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pactera.financialmanager.db.CreditCardApplyDao;
import com.pactera.financialmanager.entity.CreditCardApplyEntity;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.util.LogUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by xh on 2016-01-07.
 */
public class UploadHelper {

    private List<CreditCardApplyEntity> forUploadList;

    private ProgressDialog pd;

    private CreditCardApplyDao dao;

    private Context context;

    private Handler handler;

    private static Thread thread = null;

    public UploadHelper(Context context, Handler handler, List<CreditCardApplyEntity> forUploadList, ProgressDialog pd) {
        this.forUploadList = forUploadList;
        this.pd = pd;
        this.context = context;
        this.handler = handler;
        this.dao = new CreditCardApplyDao(context);
    }

    public void start() {

        if (thread != null) {
            Toast.makeText(context, "上一次上传未结束", Toast.LENGTH_SHORT).show();
            return;
        }

        thread = new Thread() {
            @Override
            public void run() {
                pd.setProgress(0); //进度条设置为0
                try {
                    int nSuc = 0, nTotal = forUploadList.size(); //文件个数
                    for (CreditCardApplyEntity e : forUploadList) {

                        CreditCardApplyEntity ce = dao.loadById(e.getId()); // 信用卡id
                        List<CreditCardApplyEntity.PicEntity> attachedPics = ce.getAttachedPics();
                        if (attachedPics != null) {
                            for (CreditCardApplyEntity.PicEntity p : attachedPics) {
                                if (p.getContent() != null && p.getBase64Content() == null) {
                                    p.setBase64Content(Base64.encodeToString(p.getContent(), Base64.DEFAULT));
                                }
                            }
                        }

                        Message msg;

                        msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = "正在上传第" + (nSuc+1) + "条数据...";
                        handler.sendMessage(msg);


                        String resp = httpPost(HRequest.BMC_URL_DOMAIN + "credit/upload", JSON.toJSONString(ce));
                        JSONObject respObject = JSON.parseObject(resp);

                        int errCode = -1;
                        if (respObject != null) {
                            errCode = respObject.getInteger("errCode");
                        }


                        if (errCode == 0) { // success
                            msg = Message.obtain();
                            msg.what = 9;
                            Bundle b = new Bundle();
                            b.putLong("id", e.getId());
                            msg.setData(b);
                            msg.obj = "第" + (nSuc+1) + "条数据上传成功";
                            handler.sendMessage(msg);

                        } else {
                            // 上传失败就停止
                            msg = Message.obtain();
                            msg.what = 2;

                            if (errCode == -1) {  // 上传失败或超时
                                msg.obj = "第" + (nSuc + 1) + "条数据上传超时，停止上传";
                            } else {
                                msg.obj = "第" + (nSuc + 1) + "条数据上传出错，错误信息：" + respObject.getString("errMsg");
                            }
                            handler.sendMessage(msg);
                            break;
                        }
                        // update progress
                        pd.setProgress(++nSuc * 100 / nTotal);
                    }
                } finally {
                    pd.cancel();
                    thread = null;
                }
            }
        };

        pd.show();
        thread.start();
    }

    private String httpPost(String url, String json) {

        LogUtils.i("upload url:"+url);
        HttpClient hc = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
        hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
        try {
            post.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            post.setEntity(new StringEntity(json, "utf-8"));

            HttpResponse response = hc.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

