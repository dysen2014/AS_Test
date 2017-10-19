package com.pactera.financialmanager.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.pactera.financialmanager.ui.model.UploadInfo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by xh on 2016/6/12.
 */
public class ApkUpdater {

    private ProgressDialog pd;

    private UploadInfo uploadInfo;

    private Activity activity;

    public ApkUpdater(ProgressDialog pd, UploadInfo uploadInfo, Activity activity) {
        this.uploadInfo = uploadInfo;
        this.activity = activity;
        this.pd = pd;
    }

    public void start() {
        new Thread() {
            @Override
            public void run() {

                final String BASE_PATH = "/hbcrm_dbank/";
                final String fileName = "tmp-" + uploadInfo.getVersionNO() + ".tmp";

                File dir = new File(Environment.getExternalStorageDirectory(), BASE_PATH);
                if (!dir.exists()) {

                    try {
                        boolean mkdirs =  dir.mkdirs();
                        Toast.makeText(activity,mkdirs+"path"+dir.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(activity,e.getMessage(),Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                // delete other temp files, and old apk files
                {
                    File[] delFiles = dir.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.startsWith("tmp-") && filename.endsWith(".tmp") && !filename.equals(fileName)) {
                                return true;
                            }
                            if (filename.endsWith(".apk")) {
                                return true;
                            }
                            return false;
                        }
                    });
                    if (delFiles != null) {
                        for (File f : delFiles) {
                            f.deleteOnExit();
                        }
                    }
                }

                File tmpFile = new File(dir, fileName);
                while(true) {

                    long rangeFrom = 0;
                    if (tmpFile.exists()) {
                        rangeFrom = tmpFile.length();
                    }

                    HttpClient hc = new DefaultHttpClient();
                    hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15 * 1000);
                    hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15 * 1000);
                    HttpGet get = new HttpGet(uploadInfo.getLoadURL());
                    get.setHeader("Range", rangeFrom + "-");

                    BufferedInputStream bis = null;
                    FileOutputStream fos = null;
                    try {
                        HttpResponse response = hc.execute(get);
                        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {

                            int totalLength = getTotalLength(response);
                            pd.setMax(totalLength);
                            pd.setProgress((int)rangeFrom);

                            HttpEntity entity = response.getEntity();

                            bis = new BufferedInputStream(entity.getContent());
                            fos = new FileOutputStream(tmpFile, true);

                            byte[] buffer = new byte[1024 * 4];
                            int rd;
                            int written = (int)rangeFrom;
                            while ((rd = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, rd);
                                written += rd;
                                //获取当前下载量
                                pd.setProgress(written);
                            }

                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    } finally {
                        try {
                            bis.close();
                        } catch (Exception e) {
                        }
                        try {
                            fos.close();
                        } catch (Exception e) {
                        }
                    }
                }

                File renamed = new File(tmpFile.getParentFile(), uploadInfo.getVersionNO() + ".apk");
                tmpFile.renameTo(renamed);

                pd.dismiss(); //结束掉进度条对话框

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(renamed), "application/vnd.android.package-archive");
                activity.startActivity(intent);
                activity.finish();
            }
        }.start();
    }


    private int getTotalLength(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        int totalLength = (int) entity.getContentLength();
        if (totalLength != -1) {
            return totalLength;
        }

        Header[] headers = response.getHeaders("Content-Range");
        if (headers == null || headers.length == 0) {
            return -1;
        }

        String v = headers[0].getValue();
        String[] sp = v.split(" ");
        if (sp.length < 2) {
            return -1;
        }

        v = sp[1];
        if (v.contains("/")) {
            return Integer.valueOf(v.substring(v.indexOf("/") + 1));
        }

        sp = v.split("-");
        if (sp.length != 2) {
            return -1;
        }
        return Integer.valueOf(sp[1]) + 1;
    }


}
