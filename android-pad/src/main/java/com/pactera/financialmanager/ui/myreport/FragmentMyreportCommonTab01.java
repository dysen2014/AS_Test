package com.pactera.financialmanager.ui.myreport;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Propertiesinfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-对公-资产和贷款
 * @author dg
 */
public class FragmentMyreportCommonTab01 extends ParentFragment {

	private TextView tvValue1, tvValue2, tvValue3, tvValue4, tvValue5;

	private HConnection HCon;
	private static final int INDEX_LISTS1 = 1; // 全行资产

	private Propertiesinfo propertyinfo;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 全行资产列表 */
			case INDEX_LISTS1:
				propertyinfo = (Propertiesinfo) msg.obj;
				setPropertyInfo(propertyinfo);
				
				if(propertyinfo == null){
					Toast.makeText(getActivity(), "全行资产数据解析失败！！！", Toast.LENGTH_SHORT).show();
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
		View view = inflater.inflate(R.layout.fragment_myreport_common_tab01, null);
		
		findView(view);
		getDatas();
		return view;
	}
	
	/**
	 * 初始化UI
	 * @param view
	 */
	private void findView(View view){
		tvValue1 = (TextView) view.findViewById(R.id.lvitem_info1);
		tvValue2 = (TextView) view.findViewById(R.id.lvitem_info2);
		tvValue3 = (TextView) view.findViewById(R.id.lvitem_info3);
		tvValue4 = (TextView) view.findViewById(R.id.lvitem_info4);
		tvValue5 = (TextView) view.findViewById(R.id.lvitem_info5);
	}
	

	public void setIsForperson(boolean isForperson) {
		
	}
	
	
	/**
	 * 查询List列表信息
	 */
	private void getDatas() {
		// 109001 111111
		if (Tool.haveNet(getActivity())) {
			String usercodeStr = LogoActivity.user.getUserCode();
			String brcodeStr = LogoActivity.user.getBrCode();
			String staid = LogoActivity.user.getStaId();// 岗位ID
			HRequest request = null;
			// 请求串
			String requestHttp = "T000201?method=getJSON"
					+ "&userCode=" + usercodeStr
					+ "&seriNo=DMRJRUWQF182&chnlCode=DMRJRUWQF182&transCode=T000201"
					+ "&brCode=" + brcodeStr
					+ "&spareOne"+ staid;
			
			request = new HRequest(requestHttp, "GET");
			Log.i("RETURNCUS", requestHttp);
			
			try {
				HConnection.isShowLoadingProcess = false; // 不显示loading
				HCon = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				HCon.setIndex(INDEX_LISTS1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			Toast.makeText(getActivity(), "系统请求错误", Toast.LENGTH_SHORT).show();
			break;
		// 解析返回结果列表
		case INDEX_LISTS1:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
		
		default:
			break;
		}
	}

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
			// 获取列表
			Propertiesinfo property = JSON.parseObject(tmpJsonObject.toString(), Propertiesinfo.class);
			
			Message msg = new Message();
			msg.arg1 = connectionIndex;
			msg.obj = property;
			handler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), "操作失败! 错误:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * 显示数据
	 * @param propertyinfo
	 */
	private void setPropertyInfo(Propertiesinfo propertyinfo){
		String value1 = "--";
		String value2 = "--";
		String value3 = "--";
		String value4 = "--";
		String value5 = "--";
		if(propertyinfo != null){
			// 对个人资产信息
			value1 = propertyinfo.getBAL();
			value2 = propertyinfo.getDEPS_BAL();
			value3 = propertyinfo.getLOAN_BAL();
			value4 = propertyinfo.getFINA_BAL();
			value5 = propertyinfo.getCUST_NUM();
		}
		tvValue1.setText(value1);
		tvValue2.setText(value2);
		tvValue3.setText(value3);
		tvValue4.setText(value4);
		tvValue5.setText(value5);
	}


}
