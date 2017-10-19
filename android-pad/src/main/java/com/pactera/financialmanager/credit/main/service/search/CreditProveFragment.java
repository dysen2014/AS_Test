package com.pactera.financialmanager.credit.main.service.search;

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
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;

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

/** 贷款证(农户)
 * Created by lenovo on 2017/7/17.
 */

public class CreditProveFragment extends ParentFragment {
    private Context mContext;
    private Button back;
    private ListView listView;
    private Intent intent;
    private JSONObject jsonObject;
    private JSONObject jsonObject1;
    private String customerType;
    private List<CreditProveItem> CreditProveItem;
    private Handler handler;
    //    private Boolean aBoolean;
    private String jsonArray;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.query_details_creditprove, container, false);
        CreditProveItem = new ArrayList<>();
        listView = (ListView) v.findViewById(R.id.listViewCreditProve);
        mContext = getActivity();
        Log.i("mContext:", String.valueOf(mContext));
        jsonObject1 = new JSONObject();
        jsonObject = new JSONObject();
        Bundle bundle = QueryDetails.bundle;
//        Bundle bundle = new Bundle();
//        bundle.putString("CustomerID","20070927000004");
//        bundle.putString("CustomerType","030");
        Log.i("bundle", String.valueOf(bundle));
        Set<String> keys = bundle.keySet();
        for(String key:keys){
            Log.i("ddd",key+bundle.get(key));
            if(key.equals("CustomerID")){
                try {
                    Log.i("ddd",key+bundle.get(key));
                    jsonObject1.put(key, JSONObject.wrap(bundle.get(key)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        customerType = (String) bundle.get("CustomerType");
        try {
            jsonObject.put("deviceType","Android");
            jsonObject.put("RequestParams",jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("jsonObject", String.valueOf(jsonObject));
        new HttpThread().sendRequestWithOkHttp("http://192.168.1.100:9080/ALS7M/JSONService?method=crmCreditProve",jsonObject);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("msg", String.valueOf(msg.obj));
                if (msg.obj != null) {
                    final List<ListCreditProve> list = (List<ListCreditProve>) msg.obj;
                    for (int i = 0; i < list.size(); i++) {
                        CreditProveItem.add(new CreditProveItem(
                                list.get(i).getKeyName(),
                                list.get(i).getValue()
                        ));
                    }

//                Log.i("","listView:"+listView+"\tlist size:"+keymanItem.size());
                    listView.setAdapter(new MyAdaptorCreditProve(mContext, CreditProveItem,customerType));
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
//                if(aBoolean == true){
//                    jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").getJSONObject(0).getJSONArray("IndRelative").toString();
//                }else{
                Log.i("jsonArray",jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").getJSONObject(0).getJSONArray("groupColArray").toString());
                jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").getJSONObject(0).getJSONArray("groupColArray").toString();
//                }
                Log.d("tag10", "jsonObject: " + jsonArray);
                Gson gson = new Gson();
                List<ListCreditProve> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListCreditProve>>(){}.getType());
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
