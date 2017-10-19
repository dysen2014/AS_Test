package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/7/28.
 */

public class BusinessList extends ParentActivity {
	@Bind(R.id.txt_back)
	TextView txtBack;
	@Bind(R.id.lay_back)
	LinearLayout layBack;
	@Bind(R.id.txt_title)
	TextView txtTitle;
	@Bind(R.id.txt_)
	TextView txt;
	@Bind(R.id.listView)
	ListView listView;

	private Context mContext;
	private StringBuffer js;
	private Handler handler;
	private JSONObject jsonObject;
	private JSONObject jsonObject1;
	private TextView back;
	private List<BusinessListItem> BusinessListItem;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_list);
		ButterKnife.bind(this);
		txtTitle.setText(getString(R.string.business_list));
		mContext = this;
		final Bundle bundle = this.getIntent().getExtras();
		Set<String> keys = bundle.keySet();
		jsonObject = new JSONObject();
		jsonObject1 = new JSONObject();
		for (String key : keys) {
			try {
				Log.i("key", String.valueOf(bundle.get(key)));
				jsonObject1.put(key, JSONObject.wrap(bundle.get(key)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		try {
			jsonObject1.put("CurPage", "1");
			jsonObject1.put("PageSize", "10");
			jsonObject.put("deviceType", "Android");
			jsonObject.put("RequestParams", jsonObject1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("jsonObject", String.valueOf(jsonObject));
		new HttpThread().sendRequestWithOkHttp(ParamUtils.urlTemp+"crmBusinessQuery", jsonObject);
		BusinessListItem = new ArrayList<>();
		final ListView listView = (ListView) findViewById(R.id.listView);
//        mList = listView;
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == -1){
					ShowDialog(BusinessList.this, "无数据");
					return;
				}
				if (msg.obj != null) {
					final List<ListBusinessList> list = (List<ListBusinessList>) msg.obj;
					for (int i = 0; i < list.size(); i++) {
						Log.d("list", list.get(i).getSerialNo() + ",这是：" + list.get(i).getTypeNo());
					}
					for (int i = 0; i < list.size(); i++) {
						BusinessListItem.add(new BusinessListItem(
								list.get(i).getCustomerName(),
								list.get(i).getBusinessName(),
								list.get(i).getBusinessSum(),
								list.get(i).getBalance(),
								list.get(i).getOverDueBalance(),
								list.get(i).getInterestBalance()
						));
					}

					listView.setAdapter(new MyAdaptorBusinessList(mContext, BusinessListItem));
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
							//获得选中项的值

							Intent intent1 = new Intent(mContext, BusinessDetails.class);
							Bundle bundle1 = new Bundle();
							bundle1.putString("CustomerName", list.get(i).getCustomerName());
							bundle1.putString("CustomerType", list.get(i).getCustomerType());
							bundle1.putString("BusinessName", list.get(i).getBusinessName());
							bundle1.putString("BusinessSum", list.get(i).getBusinessSum());
							bundle1.putString("Balance", list.get(i).getBalance());
							bundle1.putString("OverDueBalance", list.get(i).getOverDueBalance());
							bundle1.putString("InterestBalance", list.get(i).getInterestBalance());
							bundle1.putString("CustomerID", list.get(i).getCustomerID());
							bundle1.putString("SerialNo", list.get(i).getSerialNo());
							bundle1.putString("TypeNo", list.get(i).getTypeNo());
							bundle1.putString("ContractNo", list.get(i).getContractNo());
							intent1.putExtras(bundle1);
							startActivity(intent1);
						}
					});
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
						Request request = new Request.Builder().url(url).post(body).build();
						LogUtils.d("http", "sendRequest:"+ url + jsonObject.toString());
						Response response = client.newCall(request).execute();
						String responseData = response.body().string();
						LogUtils.d("http", "Response completed: " + responseData);
						parseJSONWithGson(responseData);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		private Object parseJSONWithGson(String jsonData) throws JSONException {
			if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
				JSONObject jsonObject = new JSONObject(jsonData);
				String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
				Log.d("tag10", "jsonObject: " + jsonArray);
				Gson gson = new Gson();
				List<ListBusinessList> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListBusinessList>>() {
				}.getType());
				Log.i("tag11", "customerList: " + customerList);
				Message msg = new Message();
				if (jsonArray.equals("[]")){
					handler.sendEmptyMessage(-1);
				}
				msg.obj = customerList;
				handler.sendMessage(msg);
				return customerList;
			} else
				return false;
		}
	}
}
