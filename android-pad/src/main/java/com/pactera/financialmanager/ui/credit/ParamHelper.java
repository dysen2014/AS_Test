package com.pactera.financialmanager.ui.credit;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pactera.financialmanager.db.Settings;
import com.pactera.financialmanager.ui.service.HRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xh on 2016-01-23.
 * <p/>
 * 信用卡配置相关的类，信用卡参数用json形式保存
 * 算出json形式的参数md5值，每次拿这个MD5值到服务器进行匹配，如果MD5值不同则更新
 */
public class ParamHelper {

    private static Param param;

    private final static String SPLITTER = "$$";

    public static Param reloadParam(Activity ac) {
        String paramS = Settings.loadCreditParam(ac.getApplication());//从首选项中获取参数
        if (paramS == null || paramS.equals("")) {
            param = null;
            return param;
        }

        String[] parts = parseParamValue(paramS);//  分割匹配好的的字符串
        param = Param.fromJson(parts[2]);//Json解析切割好的正则匹配字符串
        return param;
    }

    public static Param getParam() {
        return param;
    }

    /**
     * 打开activity时，检验信用卡参数状态
     */
    public static void updateParam(final Activity ac, final Handler handler, final String lvl3BrId) {
        String value = Settings.loadCreditParam(ac.getApplication());//从首选项中获取信用卡参数
        final String key;
        if (value != null && value.length() != 0) {
            String[] parts = parseParamValue(value); //  分割匹配好的的字符串
            if (!parts[0].equals(lvl3BrId)) {
                key = null;
            } else {
                key = parts[1];
            }
        } else {
            key = null;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient hc = new DefaultHttpClient();
                hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);   //这个时长合理不合理？？？
                hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);

                String respJson = null;
                try {//http://27.17.37.104:9995/+ "credit/param?md5=" + key + "&branch=" + lvl3BrId
                    HttpGet get = new HttpGet(HRequest.BMC_URL_DOMAIN + "credit/param?md5=" + key + "&branch=" + lvl3BrId);
                    HttpResponse response = hc.execute(get);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        respJson = EntityUtils.toString(response.getEntity(), "utf-8");
                    }
                } catch (Exception e) {
                    respJson = null;
                    e.printStackTrace();
                }

                Message obtain = Message.obtain(handler, 2);
                //返回值为空
                if (respJson == null) {
                    obtain.obj = "下载信用卡参数失败";
                    handler.sendMessage(obtain);
                    return;
                }
                //if 返回值为不为空，判断是否保护错误
                JSONObject jsonObject = JSON.parseObject(respJson);
                if (0 != jsonObject.getInteger("errCode")) {
                    obtain.obj = "下载信用卡参数失败，" + jsonObject.getString("errMsg");
                    handler.sendMessage(obtain);
                    return;
                }

                JSONObject jsonData = jsonObject.getJSONObject("data");
                if (jsonData == null) {
                    obtain.obj = "信用卡参数已是最新";
                    handler.sendMessage(obtain);
                    return;
                }
                String nKey = jsonData.getString("md5");
                String json = jsonData.getString("json");

                if (!TextUtils.isEmpty(nKey) && !TextUtils.isEmpty(json)) {
                    Settings.saveCreditParam(ac.getApplication(), buildParamValue(lvl3BrId, nKey, json));
                    obtain.obj = "更新信用卡参数成功" + jsonObject.getString("errMsg");
                    handler.sendMessage(obtain);
                }

            }
        }).start();
    }

    private static String buildParamValue(String lvl3BrId, String md5Key, String json) {
        return lvl3BrId + SPLITTER + md5Key + SPLITTER + json;
    }
    //  分割匹配好的的字符串
    private static String[] parseParamValue(String value) {
        return value.split(Pattern.quote(SPLITTER));  //正则匹配 Pattern.quote() 原有的字符串变成了\Qs (内容的开始)   \E(内容的结束)的样式
    }                                           //  分割匹配好的的字符串


    static class Param {
        public List<Entry> projects;
        public List<Entry> units;
        public List<Product> products;

        public static Param fromJson(String json) {
            Param p = new Param();
            JSONObject jsonObject = JSON.parseObject(json);
            p.units = toEntryList(jsonObject.getJSONArray("units"));
            p.projects = toEntryList(jsonObject.getJSONArray("projects"));

            JSONArray pa = jsonObject.getJSONArray("products");
            p.products = new ArrayList<Product>(pa.size());
            for (int i = 0; i < pa.size(); i++) {
                JSONObject po = pa.getJSONObject(i);
                Product pr = new Product();
                pr.code = po.getString("code");
                pr.desc = po.getString("desc");
                pr.brands = toEntryList(po.getJSONArray("brands"));

                JSONArray ja = po.getJSONArray("statementDates");
                pr.statementDates = new ArrayList<String>();
                for (int j = 0; j < ja.size(); j++) {
                    pr.statementDates.add(ja.getString(j));
                }
                p.products.add(pr);
            }
            return p;
        }


        private static List<Entry> toEntryList(JSONArray ua) {
            List<Entry> l = new ArrayList<Entry>(ua.size());
            for (int i = 0; i < ua.size(); i += 2) {
                String k = ua.getString(i);
                String v = ua.getString(i + 1);
                l.add(new Entry(k, v));
            }
            return l;
        }

        public String productDesc(String prodCode) {
            if (products == null) {
                return "";
            }
            for (Product e : products) {
                if (e.code.equals(prodCode)) {
                    return e.desc;
                }
            }
            return "";
        }
    }

    static class Product {
        public String code;
        public String desc;
        public List<Entry> brands;
        public List<String> statementDates;
    }

    static class Entry {
        final public String key;
        final public String value;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
