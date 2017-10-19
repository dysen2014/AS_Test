package com.example.lenovo.query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/7/17.
 */

public class Keyman extends Fragment {
    private Context mContext;
    private Button back;
    private ListView listView;
    private Intent intent;
    private JSONObject jsonObject;
    private JSONObject jsonObject1;

    private List<KeymanItem> keymanItem;
    private Handler handler;
    private Boolean aBoolean;
    private String jsonArray;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.query__details_keyman, container, false);
        keymanItem = new ArrayList<>();
        listView = (ListView) v.findViewById(R.id.listViewKeyman);
        mContext = getActivity();
        Log.i("mContext:", String.valueOf(mContext));
        jsonObject1 = new JSONObject();
        jsonObject = new JSONObject();
        Bundle bundle = getArguments();
//        Bundle bundle = new Bundle();
//        bundle.putString("CustomerID","2017012000001496");
//        bundle.putString("CustomerType","030");
        Log.i("bundle", String.valueOf(bundle));
        Set<String> keys = bundle.keySet();
        for(String key:keys){
            Log.i("ddd",key+bundle.get(key));
            if(key.equals("CustomerID")||key.equals("CustomerType")){
                try {
                    Log.i("ddd",key+bundle.get(key));
                    jsonObject1.put(key, JSONObject.wrap(bundle.get(key)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            jsonObject1.put("CurPage","1");
            jsonObject1.put("PageSize","10");
            jsonObject.put("deviceType","Android");
            jsonObject.put("RequestParams",jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("jsonObject", String.valueOf(jsonObject));
        try {
            Log.i("CustomerType",jsonObject1.getString("CustomerType"));
            if(jsonObject1.getString("CustomerType").equals("030") ||jsonObject1.getString("CustomerType").equals("040") ){
                aBoolean = true;
            }else{
                aBoolean = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("boolean", String.valueOf(aBoolean));
        new Keyman.HttpThread().sendRequestWithOkHttp("http://192.168.1.100:9080/ALS7M/JSONService?method=crmEntKeyman",jsonObject);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    final List<ListKeyman> list = (List<ListKeyman>) msg.obj;
                    for (int i = 0; i < list.size(); i++) {
                        keymanItem.add(new KeymanItem(
                                list.get(i).getCertName(),
                                list.get(i).getCertID(),
                                list.get(i).getCustomerName(),
                                list.get(i).getRelationName(),
                                list.get(i).getUserName(),
                                list.get(i).getOrgName(),
                                list.get(i).getInputDate(),
                                list.get(i).getUpdateDate(),
                                list.get(i).getTelephone(),
                                list.get(i).getOfficeTime(),
                                list.get(i).getPractitionerTime()
                        ));
                    }

//                Log.i("","listView:"+listView+"\tlist size:"+keymanItem.size());
                    listView.setAdapter(new MyAdaptorKyeman(mContext, keymanItem,aBoolean));
                }
            }
        };
        return v;
    }
    class HttpThread extends Thread {
        private final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

        protected void sendRequestWithOkHttp(final String url, final JSONObject jsonObject) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, jsonObject.toString());
                        Log.i("rrr", String.valueOf(jsonObject));
                        Request request = new Request.Builder().url(url).post(body).build();
                        Response response = client.newCall(request).execute();
                        Log.i("lh", String.valueOf(response));
                        String responseData = response.body().string();
//                        JSONObject t = responseData;
                        Log.i("gh",responseData);
                        if(!responseData.equals("")||responseData!=null){
                            parseJSONWithGson(responseData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private Object parseJSONWithGson(String jsonData) throws JSONException {
            if (!TextUtils.isEmpty(jsonData) || jsonData != null){
                JSONObject jsonObject2 = new JSONObject(jsonData);
                if(aBoolean == true){
                    jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").getJSONObject(0).getJSONArray("IndRelative").toString();
                }else{
                    jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").getJSONObject(0).getJSONArray("EntRelative").toString();
                }
                Log.d("tag10", "jsonObject: " + jsonArray);
                Gson gson = new Gson();
                List<ListKeyman> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListKeyman>>(){}.getType());
                Log.i("tag11", "customerList: " + customerList);
                Message msg = new Message();
                msg.obj = customerList;
                handler.sendMessage(msg);
                return customerList;
            }else
                return false;
        }
    }
}
