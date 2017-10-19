package com.pactera.financialmanager.ui.hallfirst;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.CustomerPowerInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 客户购买力的fragment
 */
public class HallCustomerPowerFragment extends ParentFragment {
	
	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private TextView tvDeptbal;
	private ListView lvCustomerpower;
	private CustomerPowerAdapter adapter = null;
	
	private HConnection customerPowerCon;
	private final int FLAG_POWER = 98;
	
	private String custID = "";		// 客户ID
	private String deptbal = ""; 	// 活期金额

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			List<CustomerPowerInfo> results = (List<CustomerPowerInfo>) msg.obj;
			if(results != null && results.size() > 0){
				hiddenLoading(false);
				
				adapter.setList(results);
				adapter.notifyDataSetChanged();
			}else{
				hiddenLoading(true);
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_customerpower, null);
		
		setupView(view);
		setRequestForInfo(custID);
		
		return view;
	}
	
	/**
	 * 设置custID
	 * @param custID
	 */
	public void setCustID(String custID) {
		this.custID = custID;
	}

	/**
	 * 接口请求
	 * @param custID
	 */
	private void setRequestForInfo(String custID) {
		if (Tool.haveNet(getActivity())) {
			loading();
			
			String requestType = InterfaceInfo.HALLCUSTOMER_BUYPOWER;
			String info = "&custID=" + custID; 
			customerPowerCon = RequestInfo(this, Constants.requestType.Other, requestType, info, FLAG_POWER, false);
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
			break;
			
		case FLAG_POWER:
			HResponse res = customerPowerCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				hiddenLoading(true);
				Toast.makeText(getActivity(), "返回数据为空", Toast.LENGTH_SHORT).show();
				return;
			}
			
			JSONObject tmpJsonObject = res.dataJsonObject;
			readCustomerPowerInfo(tmpJsonObject);
			break;
			
		default:
			break;
		}
	}

	private void setupView(View view) {
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		tvDeptbal = (TextView) view.findViewById(R.id.tv_huoqijine);
		lvCustomerpower = (ListView) view.findViewById(R.id.lv_customerpower);
		
		adapter = new CustomerPowerAdapter();
		lvCustomerpower.setAdapter(adapter);
	}

	/**
	 * 显示进度条
	 */
	private void loading() {
		layLoading.setVisibility(View.VISIBLE);
		lvCustomerpower.setVisibility(View.GONE);
		layNodata.setVisibility(View.GONE);
	}

	/**
	 * 隐藏进度条
	 *
	 * @param noDatas
	 */
	private void hiddenLoading(boolean noDatas) {
		layLoading.setVisibility(View.GONE);
		if (noDatas) {
			layNodata.setVisibility(View.VISIBLE);
			lvCustomerpower.setVisibility(View.GONE);
		} else {
			layNodata.setVisibility(View.GONE);
			lvCustomerpower.setVisibility(View.VISIBLE);
		}
	}

	
	private void readCustomerPowerInfo(JSONObject tmpJsonObject) {
		String code = "";//返回码
		try {
			if (tmpJsonObject.has("retCode")) {
				code = tmpJsonObject.getString("retCode");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if("0000".equals(code)){
			try {
				if (tmpJsonObject.has("deptBal")) {
					deptbal = tmpJsonObject.getString("deptBal");
				}
				//活期存款金额
				//tvDeptbal.setText(Tool.setFormatValue(deptbal));
				List<CustomerPowerInfo> listResult = new ArrayList<CustomerPowerInfo>();
				CustomerPowerInfo deptBal = new CustomerPowerInfo("活期", "", deptbal);
				listResult.add(deptBal);
				
				// 解析得到的数据保存并显示出来
				if(tmpJsonObject.has("group")){
					String customerpowerinfo=tmpJsonObject.getString("group");
					List<CustomerPowerInfo> list = JSON.parseArray(customerpowerinfo, CustomerPowerInfo.class);
					listResult.addAll(list);
					
					// 消息
					Message msg = new Message();
					msg.arg1 = FLAG_POWER;
					msg.obj = listResult;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			hiddenLoading(true);
			Toast.makeText(getActivity(), "错误码："+code, Toast.LENGTH_SHORT).show();
		}
	}

	private class HolderView {
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
	}
	
	/**
	 * 客户购买力adapter
	 * @author Administrator
	 *
	 */
	class CustomerPowerAdapter extends BaseAdapter{
		
		List<CustomerPowerInfo> list = null;
		
		@Override
		public int getCount() {
			if(list != null){
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(list != null && list.size()>0){
				return list.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.customer_power_item, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getActivity().getResources().getColor(R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
			}
			
			// 设置值
			CustomerPowerInfo powerInfo = list.get(position);
			if(powerInfo != null){
				holder.lvItemInfo1.setText(""+(position+1));
				holder.lvItemInfo2.setText(powerInfo.getPrd_name());
				holder.lvItemInfo3.setText(powerInfo.getProdEndDate());
				holder.lvItemInfo4.setText(Tool.setFormatValue(powerInfo.getProdAmt()));
			}
			return convertView;
		}

		public void setList(List<CustomerPowerInfo> list) {
			this.list = list;
		}

	}
	

}
