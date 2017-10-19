package com.pactera.financialmanager.ui.myreport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.LanRecover;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-对公-贷款回收情况
 * @author dg
 */
public class FragmentMyreportTab05 extends ParentFragment {

	private PropertyAdapter adapter = null;
	private ListView lvProperty;
	private boolean isForperson = true;
	private List<LanRecover> listProperties = new ArrayList<LanRecover>();
	
	// 数据请求
	private HConnection HCon;
	private static final int INDEX_DK 	= 11;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case INDEX_DK:	
				LanRecover lanRecover = (LanRecover)msg.obj;
				if(lanRecover != null){
					listProperties.add(lanRecover);
					adapter.setListProperties(listProperties);
					adapter.notifyDataSetChanged();
				}
				break;
			
			default:
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_myreport_tab05, null);
		findView(view);
		
		sendRequestForLanRecover();
		return view;
	}

	private void findView(View view){
		lvProperty = (ListView) view.findViewById(R.id.lv_property);
		adapter = new PropertyAdapter();
		lvProperty.setAdapter(adapter);
	}
	
	/**
	 * 判断是否为个人或对公
	 * @param isForperson
	 */
	public void setIsForperson(boolean isForperson) {
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
		
		if(listProperties != null && listProperties.size()>0){
			adapter.setListProperties(listProperties);
			adapter.notifyDataSetChanged();
		}else{
			sendRequestForLanRecover();
		}
	}
	
	private void sendRequestForLanRecover(){
		if (Tool.haveNet(getActivity())) {
			String requestType = InterfaceInfo.MYREPORTTAB05_Get;
			String info = "&spareOne=" + "" + "&spareTwo=" + "";
			
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_DK, false);
		}
	}
	
	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
			break;
			
		case INDEX_DK:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			Log.i("RETURNCUS", "res:-->"+res);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
		}
	}
	
	/**
	 * 解析接口数据
	 * @param tmpJsonObject
	 * @param connectionIndex
	 */
	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
		Log.e("RETURNCUS", tmpJsonObject.toString());
		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");
			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}

		// 获取接口成功
		if (retCode.equals("0000")) {
			Message msg = new Message();
			msg.arg1 = connectionIndex;			
			try {
				LanRecover lanRecover= JSON.parseObject(tmpJsonObject.toString(), LanRecover.class);
				msg.obj = lanRecover;
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), "请求失败! 错误代码:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
	
	class HolderView{
		private TextView tvValue1;
		private TextView tvValue2;
		private TextView tvValue3;
	}
	
	/**
	 * ListView 适配器
	 * @author Administrator
	 *
	 */
	class PropertyAdapter extends BaseAdapter{

		private List<LanRecover> listProperties;

		@Override
		public int getCount() {
			if(listProperties == null){
				return 0;
			}
			return listProperties.size();
		}

		@Override
		public Object getItem(int arg0) {
			if(listProperties == null){
				return null;
			}
			return listProperties.get(arg0);
		}


		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.myreport_common_tab04_item, null);
				holder = new HolderView();
				holder.tvValue1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.tvValue2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.tvValue3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				convertView.setTag(holder);
			}else{
				holder = (HolderView) convertView.getTag();
			}
			// 设置单行背景颜色
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.white));
			}
			// 设置值
			LanRecover property = listProperties.get(position);
			if(property != null){
				if(isForperson){
					holder.tvValue1.setText(Tool.setFormatValue(property.getPSN_LON_CREDIT_AMT()));
					holder.tvValue2.setText(Tool.setFormatValue(property.getPSN_LON_Y_DEP_MADD()));
					holder.tvValue3.setText(Tool.setFormatValue(property.getPSN_LON_Y_DEP_N_MADD()));
				}else{
					holder.tvValue1.setText(Tool.setFormatValue(property.getENT_LON_CREDIT_AMT()));
					holder.tvValue2.setText(Tool.setFormatValue(property.getENT_LON_Y_DEP_MADD()));
					holder.tvValue3.setText(Tool.setFormatValue(property.getENT_LON_Y_DEP_N_MADD()));
				}
			}
			return convertView;
		}

		public void setListProperties(List<LanRecover> listProperties) {
			this.listProperties = listProperties;
		}
		
	}

}
