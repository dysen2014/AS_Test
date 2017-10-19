package com.example.lenovo.query;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

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
 * Created by lenovo on 2017/8/11.
 */

public class BusinessTypeBg extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recyclerView;

    private JSONObject jsonObject;
    private JSONObject jsonObject1;
    private List<BusinessTypeItem> BusinessTypeItem;
    private Handler handler;
    private String jsonArray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_type);
        mContext = this;
        BusinessTypeItem = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.BusinessTypeBg);

        jsonObject1 = new JSONObject();
        jsonObject = new JSONObject();
//        Bundle bundle = getArguments();
        Bundle bundle = new Bundle();
        bundle.putString("UserID","B129966");
        bundle.putString("SortNo","");
        Log.i("bundle", String.valueOf(bundle));
        Set<String> keys = bundle.keySet();
        for(String key:keys){
            Log.i("ddd",key+bundle.get(key));
            try {
                Log.i("ddd",key+bundle.get(key));
                jsonObject1.put(key, JSONObject.wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonObject.put("deviceType","Android");
            jsonObject.put("RequestParams",jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("jsonObject", String.valueOf(jsonObject));
        new BusinessTypeBg.HttpThread().sendRequestWithOkHttp("http://192.168.1.100:9080/ALS7M/JSONService?method=businessType",jsonObject);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("msg", String.valueOf(msg.obj));
                if (msg.obj != null) {
                    final List<ListBusinessType> list = (List<ListBusinessType>) msg.obj;

                    for (int i = 0; i < list.size(); i++) {
                        Log.i("TypeName",list.get(i).getTypeName()+"sortNo:"+list.get(i).getSortNo());
                        BusinessTypeItem.add(new BusinessTypeItem(
                                list.get(i).getTypeName(),
                                list.get(i).getSortNo()
                        ));
                    }
//                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//                    recyclerView.setAdapter(new MyAdaptorBusinessType(BusinessTypeItem));
                }
            }
        };
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
                        if(!TextUtils.isEmpty(responseData)||responseData!=null){
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
                Log.i("jsonArray",jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").toString());
                jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").toString();
//                }
                Log.d("tag10", "jsonObject: " + jsonArray);
                Gson gson = new Gson();
                List<ListBusinessType> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListBusinessType>>(){}.getType());
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
