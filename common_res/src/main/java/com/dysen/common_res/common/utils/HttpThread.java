package com.dysen.common_res.common.utils;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/7/11.
 */

public class HttpThread extends Thread {

    private static final String TAG = "HttpThread";
    public static String jsonData;
    private static final MediaType MEDIA_TYPE_MARKDOWN  = MediaType.parse("application/json; charset=utf-8");

    public static void sendRequestGet(final String url, final String params, final Handler handler, final int warnType){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url + params)
//                .url("https://github.com/hongyangAndroid")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = -1;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) {
                LogUtils.d("http", "sendRequest:"+String.valueOf(response));

                String tmpBody = null;
                JSONObject dataJsonObject = null;
                JSONArray dataJsonArray = null;
                try {
                    tmpBody = getJsonString(response.body().byteStream(), "UTF-8");
                    if (tmpBody.startsWith("[")) {
                        dataJsonArray = new JSONArray(tmpBody);
                    } else {
                        dataJsonObject = new JSONObject(tmpBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogUtils.d("http", "Response completed: "+dataJsonObject);
                Message msg = new Message();
                msg.what = warnType;
                msg.obj = dataJsonObject;
                handler.sendMessage(msg);
            }
        });
    }
    public static String getJsonString(InputStream inputStream, String type) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, type);

        int event = parser.getEventType();// 产生第一个事件
        String tmpJsonString = null;
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
                    break;
                case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
                    if ("getJSONReturn".equals(parser.getName())) {// 判断开始标签元素是否是student
                        tmpJsonString = parser.nextText();// 得到student标签的属性值，并设置student的id
                    }
                    break;
                case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
                    break;
            }
            event = parser.next();// 进入下一个元素并触发相应事件
        }
        return tmpJsonString;
    }
    /**
     *  /**
     * 发送请求
     * @param url
     * @param obj
     * @param handler
     */
    public static void sendRequestWithOkHttp(final String url, final JSONObject obj, final Handler handler) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,  String.valueOf(obj));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)//请求地址
                            .post(body)//post 请求参数
                            .build();
                    LogUtils.d("http", "sendRequest:"+ url + obj.toString());
                    Response response = client.newCall(request).execute();
//                    LogUtils.d("http", "response:"+String.valueOf(response));
                    String responseData = response.body().string();

                    LogUtils.d("http", "Response completed: " + responseData);
//                    jsonData = parseJSONWithGson(responseData);
//
//                    Message msg = new Message();
//                    msg.obj = jsonData;
//                    handler.sendMessage(msg);

                    Message msg = new Message();
                            msg.obj = responseData;

                    handler.sendMessage(msg);
                } catch (Exception e) {
                    LogUtils.d("http", "sokect time out");
                    Message msg = new Message();
                    msg.what = -1;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void parseXMLWithSAX(String xmlData){
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            MyContentHandler contentHandler = new MyContentHandler() ;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestWithOkHttp(final String url, final JSONObject obj, final String type, final Handler handler) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,  String.valueOf(obj));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)//请求地址
                            .post(body)//post 请求参数
                            .build();
                    LogUtils.d("http", request+"\nsendRequestWithOkHttp:"+url+obj.toString());
                    Response response = client.newCall(request).execute();
                    LogUtils.d("http", "response:"+String.valueOf(response));
                    String responseData = response.body().string();

                    LogUtils.d("http", "Response completed: " + responseData);
//                    jsonData = parseJSONWithGson(responseData);
                    Message msg = new Message();
//                    if (parseJSON(responseData).get("returnCode").toString().equals("SUCCESS")){//返回成功
//                        if (parseJSON(responseData, "Result").has("Result")){
//                            if (parseJSON(responseData, "Result").toString().equals("N")) {//参数有无
//
//                            } }else {//
                            msg.obj = responseData;
                            if("buss".equals(type)){        //业务品种
                                msg.what = 1;
                            }else if("cust".equals(type)){      //客户信息
                                msg.what = 2;
                            }else if("rate".equals(type)){      //测算项信息
                                msg.what = 3;
                            }else if("rateTol".equals(type)){       //测算结果
                                msg.what = 4;
                            }
//                        }
//                    }else {//返回失败
//
//                    }
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    LogUtils.d("http", "sokect time out");
                    Message msg = new Message();
                    msg.what = -1;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static JSONObject parseJSON(String jsonData, String name) throws JSONException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null){
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject json = jsonObject.getJSONObject(name);
            LogUtils.d("http parse", "jsonObject: " + json.toString());

            return json;
        }else
            return null;
    }

    public static JSONObject parseJSON(String jsonData) throws JSONException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null){
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject json = jsonObject.getJSONObject("ResponseParams");
            LogUtils.d("http parse", "jsonObject: " + json.toString());

            return json;
        }else
            return null;
    }

    /**
     * 解析 返回单组数据
     * @param jsonData
     * @return
     * @throws JSONException
     */
    public static String parseJSONWithGson(String jsonData) throws JSONException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null){
            JSONObject jsonObject = new JSONObject(jsonData);
            String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
            LogUtils.d("http parse", "jsonObject: " + jsonArray);

            return jsonArray;
        }else
            return null;
    }

    /**
     * 解析 返回多组数据
     * @param jsonData
     * @param handler
     * @return
     * @throws JSONException
     */
    public static String parseJSONWithGson(String jsonData, Handler handler) throws JSONException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            JSONObject jsonObject = new JSONObject(jsonData);
            String jsonArrayHeader = jsonObject.getJSONObject("ResponseParams").getJSONArray("header").toString();
            String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
            LogUtils.d("http parse", jsonArrayHeader+"===============json parse==============" +  jsonArray);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("header", jsonArrayHeader);
            bundle.putString("data", jsonArray);
            msg.setData(bundle);
            handler.sendMessage(msg);
            return jsonArrayHeader;
        } else
            return null;
    }

    public static <T> T parseObject(String jsonData, Class<T> classOfT) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            Object object = gson.fromJson(jsonData, classOfT);

            return Primitives.wrap(classOfT).cast(object);
        }else
            return null;
    }

    public static <T> List<T> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<T> list = gson.fromJson(jsonData, new TypeToken<List<T>>() {}.getType());

            return list;
        } else
            return null;
    }

    private String responseData = null;
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * post请求方式
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String sendRequestWithOkHttp(final String url, final JSONObject json) throws IOException {

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).post(body).build();
                    Log.d(TAG, "request: "+request);
                    Response response = client.newCall(request).execute();
                    //Log.d(TAG, "body: "+response.body());
                    responseData = response.body().string();
                    Log.d(TAG, "responseData: "+responseData);

                    //parseJSONWithGson(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return responseData;
    }

    /**
     * url请求
     * @param url
     * @return
     * @throws IOException
     */
    public String sendRequestWithOkHttp(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Log.d(TAG, "request: "+request);
        Response response = client.newCall(request).execute();
        //Log.d(TAG, "body: "+response.body());
        String responseData = response.body().string();
        Log.d(TAG, "responseData: "+responseData);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static void sendRequestWithOkHttp(final String url, final JSONObject jsonObject, final Handler handler, final int index) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,  String.valueOf(jsonObject));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)//请求地址
                            .post(body)//post 请求参数
                            .build();
                    LogUtils.d("http", "sendRequest:"+ url + jsonObject.toString());
                    Response response = client.newCall(request).execute();
//                    LogUtils.d("http", "response:"+String.valueOf(response));
                    String responseData = response.body().string();

                    LogUtils.d("http", "Response completed: " + responseData);

                    Message msg = new Message();
//                    if (parseJSON(responseData).get("returnCode").toString().equals("SUCCESS")) {//返回成功
//                        if (parseJSON(responseData, "Result").has("Result")){
//                            if (parseJSON(responseData, "Result").toString().equals("N")) {//参数有无
//
//                            } }else {//
                             msg.obj = responseData;
                            msg.what = index;
//                        }
//                    }else {
//
//                    }

                    handler.sendMessage(msg);
                } catch (Exception e) {
                    LogUtils.d("http", "sokect time out");
                    Message msg = new Message();
                    msg.what = -1;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}