package com.pactera.financialmanager.ui.hallfirst;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.ProductInfo;
import com.pactera.financialmanager.ui.productcenter.HotProductInfoActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 推荐的理财产品 的fragment
 */
public class HallCustomerlicaiproFragment extends ParentFragment implements OnItemClickListener {
	
	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private GridView gvLicaiPro;
	private HConnection customerLicaiCon;
	private final int customerLicaiFlag = 97;

	private String custID = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_licaipro, null);
		findID(view);
		
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

	private void findID(View view) {
		gvLicaiPro = (GridView) view.findViewById(R.id.gv_gridview);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		
		gvLicaiPro.setOnItemClickListener(this);
	}

	/**
	 * 显示进度条
	 */
	private void loading() {
		layLoading.setVisibility(View.VISIBLE);
		gvLicaiPro.setVisibility(View.GONE);
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
			gvLicaiPro.setVisibility(View.GONE);
		} else {
			layNodata.setVisibility(View.GONE);
			gvLicaiPro.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 请求方式
	 * @param custID
	 */
	private void setRequestForInfo(String custID) {
		if (Tool.haveNet(getActivity())) {
			loading();
//			// 一句话营销
//			String requestType = InterfaceInfo.HALLCUSTOMER_LICAIPRODUCT;
//			String info = "&custID=" + custID; 
			
			// 推荐产品
			String requestType = InterfaceInfo.HALLCUSTOMER_LICAIPRODUCT2;
			String info = ""; 
			customerLicaiCon = RequestInfo(this, Constants.requestType.Other, requestType, info, customerLicaiFlag, false);
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			break;
			
		case customerLicaiFlag:
			HResponse res = customerLicaiCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				hiddenLoading(true);
				Toast.makeText(getActivity(), "返回数据为空", Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			readCustomerLicaiInfo(tmpJsonObject);
			break;
		default:
			break;
		}
	}

	private void readCustomerLicaiInfo(JSONObject tmpJsonObject) {
		String code = null;// 返回码
		try {
			if (tmpJsonObject.has("retCode")) {
				code = tmpJsonObject.getString("retCode");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if ("0000".equals(code)) {
			String rows=null;
			try {
				rows=tmpJsonObject.getString("group");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			// 理财产品
//			List<LiCaiProInfo> list = JSON.parseArray(rows, LiCaiProInfo.class);
//			if(list != null && list.size()>0){
//				PictureAdapter adapter = new PictureAdapter(list);
//				gvLicaiPro.setAdapter(adapter);
//				hiddenLoading(false);
//			}else{
//				hiddenLoading(true);
//			}
			List<ProductInfo> list = JSON.parseArray(rows, ProductInfo.class);
			if(list != null && list.size()>0){
				PictureAdapter adapter = new PictureAdapter(list);
				gvLicaiPro.setAdapter(adapter);
				hiddenLoading(false);
			}else{
				hiddenLoading(true);
			}
		} else {
			hiddenLoading(true);
			//Toast.makeText(getActivity(), "错误码：" + code, Toast.LENGTH_SHORT).show();
		}
	}


	class PictureAdapter extends BaseAdapter {
		private List<ProductInfo> list;

		public PictureAdapter(List<ProductInfo> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			if(list != null){
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(list != null && list.size() > 0){
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
				convertView = View.inflate(getActivity(), R.layout.gridview_licaipro, null);
				holder = new HolderView();
				holder.tvPercent = (TextView) convertView.findViewById(R.id.tv_percent);
				holder.tvProName = (TextView) convertView.findViewById(R.id.tv_proName);
				holder.tvRisk = (TextView) convertView.findViewById(R.id.tv_risk);
				holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
				
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			
			ProductInfo product = list.get(position);
			if(product != null){
				String percent = product.getZGNSYL();
				String risk = product.getCPFXDJ();
				String proName = product.getPRD_NAME();
				String date = product.getMJQSR();
				// 年收益率＊100
				if(!TextUtils.isEmpty(percent)){
					percent = (Double.parseDouble(percent)*100) + "";
				}
				holder.tvPercent.setText(Tool.setFormatValue(percent));
				holder.tvProName.setText(proName);
				holder.tvRisk.setText(risk);
				holder.tvDate.setText(date);
			}
			return convertView;
		}
	}

	public class HolderView {
		private TextView tvPercent;
		private TextView tvProName;
		private TextView tvRisk;
		private TextView tvDate;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ProductInfo product = (ProductInfo)arg0.getItemAtPosition(arg2);
		if(product != null){
			Intent intent = new Intent();
			intent.putExtra("PRD_CLASS", product.getPRD_CLASS());
			intent.putExtra("PRD_ID", product.getPRD_ID());
			intent.setClass(getActivity(), HotProductInfoActivity.class);
			startActivity(intent);
		}
	}

}
