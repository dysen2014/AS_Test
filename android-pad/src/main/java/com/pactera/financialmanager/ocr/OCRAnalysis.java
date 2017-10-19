package com.pactera.financialmanager.ocr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ym.idcard.reg.IDCard;
import com.ym.idcard.reg.OcrEngine;
import com.ym.idcard.util.BizFileHelper;
import com.ym.idcard.util.CommonHelper;
import com.ym.idcard.util.ConfigConstant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DengGuang on 2017/1/19.
 */
public class OCRAnalysis {

    public static byte[] photoPic = null;
    public static String pic_path = "";

    public static IDCard idCard = null;
    private int carmela_tpye;

    private Context context;

    public OCRAnalysis(Context context) {
        this.context = context;
    }

    /**
     * 获取全部路径
     * @param parameter
     */
    public void takePhoto(String parameter) {
        String fullFileName = CommonHelper.Path.getElePicFull() + getFileName(null, "IDCard.jpg");
        File localFile = new File(fullFileName);
        if (localFile.exists()) {
            localFile.delete();
        }
        pic_path = fullFileName;
        photoPic = null;

        Intent intent = new Intent(context, IdCardCameraNew.class);
        intent.putExtra("Parameter", parameter);
        context.startActivity(intent);
    }

    /**
     * 获取文件名
     *
     * @param before
     * @param after
     * @return
     */
    private String getFileName(String before, String after) {
        Date localDate = new Date(System.currentTimeMillis());
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(localDate);
        if (before == null) {
            before = "";
        }
        if (after == null) {
            after = "";
        }

        StringBuilder str = new StringBuilder(String.valueOf(before)).append(fileName).append(after);
        return str.toString();
    }


    /**
     * 读取身份证信息
     *
     * @param handler
     */
    public void readIdCardInfo(final Handler handler) {
        if (photoPic == null)
            return;

//        Toast.makeText(context, "正在识别，请稍等...", Toast.LENGTH_SHORT).show();
        InputStream inputStream = new ByteArrayInputStream(photoPic);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = new byte[102400];
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        final Bitmap mImagebitmap = BitmapFactory.decodeStream(inputStream, null, opts);

        new Thread(new Runnable() {
            public void run() {
                String idcard_path = pic_path;
                if (carmela_tpye == ConfigConstant.CUSTOM_CAMERA) {
                    BizFileHelper.saveBitmapForJPG(mImagebitmap, idcard_path);
                }
                if (!new File(idcard_path).exists())
                    return;

                Message msg = new Message();
                OcrEngine orc = new OcrEngine();
                IDCard idCard = null;
                try {
                    idCard = orc.recognize(context, idcard_path);
                    int regCode = idCard.getRecogStatus();
                    msg.what = regCode;
                } catch (Exception localException) {
                    localException.printStackTrace();
                    msg.what = OcrEngine.RECOG_FAIL;
                }
                msg.obj = idCard;
                handler.sendMessage(msg);
            }
        }).start();
    }

    /**
     *
     * @param msg
     * @return
     */
    public void toastInfo(Message msg) {
//        Toast.makeText(context, "识别结束...", Toast.LENGTH_SHORT).show();
        String toastStr = "";
        switch (msg.what) {
            case OcrEngine.RECOG_CANCEL:
                toastStr = "识别取消~";
                break;
            case OcrEngine.RECOG_OK:
                toastStr = "识别成功";//去掉识别成功的吐司，因为有时拍照识别文字不全及错误都弹出吐司，用户体验不好。
                break;
            case OcrEngine.RECOG_FAIL:
                toastStr = "识别失败~";
                break;
            case OcrEngine.RECOG_SMALL:
                toastStr = "图像太小~";
                break;
            case OcrEngine.RECOG_BLUR:
                toastStr = "图形模糊~";
                break;
            case OcrEngine.RECOG_LANGUAGE:
                toastStr = "识别语言错误~";
                break;
            default:
                break;
        }
        if(!toastStr.equals("识别成功")){
        Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
        }
    }
}
