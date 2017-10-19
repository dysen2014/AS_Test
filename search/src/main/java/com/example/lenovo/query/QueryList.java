package com.example.lenovo.query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by lenovo on 2017/7/11.
 */

public class QueryList extends AppCompatActivity {
    private Context mContext;
//    private ListView mList;
    private TextView back;

//    private ListViewBean listViewBean;
    private StringBuffer js;
    private JSONObject jsonObject;
    private JSONObject jsonObject1;

    private Handler handler;
    private List<CustomerItem> customerItem;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_list);
        mContext = this;
        jsonObject = new JSONObject();
        jsonObject1 = new JSONObject();
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        Set<String> keys = bundle.keySet();
        for(String key:keys){
            try {
                Log.i("参数：", (String) bundle.get(key));
                jsonObject1.put(key,JSONObject.wrap(bundle.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
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
        Log.i("this de jsonObject", String.valueOf(jsonObject));
        new HttpThread().sendRequestWithOkHttp("http://192.168.1.100:9080/ALS7M/JSONService?method=crmCustomerQuery",jsonObject);
        customerItem = new ArrayList<>();
        final ListView listView = (ListView) findViewById(R.id.listView);
//        mList = listView;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null ){
                    final List<ListPerson> list = (List<ListPerson>) msg.obj;
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("list",list.get(i).getCustomerName());
                    }
                    for(int i = 0;i<list.size();i++){
                        customerItem.add(new CustomerItem(
                                list.get(i).getIrscreditLevel(),
                                list.get(i).getCustomerName(),
                                list.get(i).getCertID(),
                                list.get(i).getMobilePhone()
                        ));
                    }

                    listView.setAdapter(new MyAdaptor(mContext,customerItem));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //获得选中项的值

                        Intent intent1 = new Intent(mContext,QueryDetails.class);
                        Bundle bundle1 = new Bundle();
                            bundle1.putString("CertID",list.get(i).getCertID());
                            bundle1.putString("CustomerID",list.get(i).getCustomerId());
                            bundle1.putString("CustomerType",list.get(i).getCustomerType());
                            bundle1.putString("MobilePhone",list.get(i).getMobilePhone());
                            bundle1.putString("IrscreditLevel",list.get(i).getIrscreditLevel());
                            bundle1.putString("CustomerName",list.get(i).getCustomerName());
                            intent1.putExtras(bundle1);
                        startActivity(intent1);
                        }
                    });
                }
            }
        };


    }

    class HttpThread extends Thread {
        private final MediaType MEDIA_TYPE_MARKDOWN  = MediaType.parse("application/json; charset=utf-8");
        protected void sendRequestWithOkHttp(final String url, final JSONObject jsonObject) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,  jsonObject.toString());
                        Request request = new Request.Builder().url(url).post(body).build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        parseJSONWithGson(responseData);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private Object parseJSONWithGson(String jsonData) throws JSONException {
            if (!TextUtils.isEmpty(jsonData) || jsonData != null){
                JSONObject jsonObject = new JSONObject(jsonData);
                String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
                Log.d("tag10", "jsonObject: " + jsonArray);
//            StringBuffer jsonBuffer = new StringBuffer(jsonArray);
//            jsonBuffer.deleteCharAt(0);
//            jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
//            jsonArray = jsonBuffer.toString();
                //Log.d("tag19", jsonArray);
                Gson gson = new Gson();
                List<ListPerson> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListPerson>>(){}.getType());
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
