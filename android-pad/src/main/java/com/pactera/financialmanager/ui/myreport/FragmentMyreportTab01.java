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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Propertiesinfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-客户经理-全行金融资产
 * @author dg
 */
public class FragmentMyreportTab01 extends ParentFragment{

	private LinearLayout layQH, layGH;
	private TextView tvTitle1, tvTitle2;
	private TextView[] tvQHs = new TextView[18];
	private TextView[] tvGHs = new TextView[18];
	
	private HConnection HConQH;
	private HConnection HConGH;
	private static final int INDEX_QH = 1; 	// 全行资产
	private static final int INDEX_GH = 2; 	// 管辖资产
	private Propertiesinfo QHPropertyinfo = null;  	// 全行数据
	private Propertiesinfo GHPropertyinfo = null;	// 管行数据

	private boolean isForperson = true;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 全行资产列表 */
			case INDEX_QH:
				QHPropertyinfo = (Propertiesinfo) msg.obj;
				setPropertyInfo(tvQHs, QHPropertyinfo);
				if(QHPropertyinfo == null){
					Toast.makeText(getActivity(), "全行资产数据解析失败！！！", Toast.LENGTH_SHORT).show();
				}
				break;
				
			/* 管辖资产列表 */
			case INDEX_GH:
				GHPropertyinfo = (Propertiesinfo) msg.obj;
				setPropertyInfo(tvGHs, GHPropertyinfo);
				if(GHPropertyinfo == null){
					Toast.makeText(getActivity(), "管户资产数据解析失败！！！", Toast.LENGTH_SHORT).show();
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
		View view = inflater.inflate(R.layout.fragment_myreport_tab01, null);
		findView(view);
		if(getActivity() instanceof WorkPlaceItemChange){
			((WorkPlaceItemChange)getActivity()).workMyreportPersonChange(1, isForperson);
		}
		
		getQHDatas();
		getGHDatas();
		return view;
	}
	

	private void findView(View view){
		layQH = (LinearLayout) view.findViewById(R.id.lay_benhang);
		layGH = (LinearLayout) view.findViewById(R.id.lay_guanhang);
		tvTitle1 = (TextView) layQH.findViewById(R.id.tv_title);
		tvTitle2 = (TextView) layGH.findViewById(R.id.tv_title);
		tvTitle1.setText("本行客户数据(单位:万元、个)");
		tvTitle2.setText("管户客户数据(单位:万元、个)");
		// 全行_个人业务 - [横向初始化控件]
		tvQHs[0] = (TextView) layQH.findViewById(R.id.tv_p_hq);
		tvQHs[1] = (TextView) layQH.findViewById(R.id.tv_p_dq);
		tvQHs[2] = (TextView) layQH.findViewById(R.id.tv_p_lc);
		tvQHs[3] = (TextView) layQH.findViewById(R.id.tv_p_total);
		tvQHs[4] = (TextView) layQH.findViewById(R.id.tv_p_dk);
		tvQHs[5] = (TextView) layQH.findViewById(R.id.tv_p_num);
		// 全行_对公业务
		tvQHs[6] = (TextView) layQH.findViewById(R.id.tv_c_hq);
		tvQHs[7] = (TextView) layQH.findViewById(R.id.tv_c_dq);
		tvQHs[8] = (TextView) layQH.findViewById(R.id.tv_c_lc);
		tvQHs[9] = (TextView) layQH.findViewById(R.id.tv_c_total);
		tvQHs[10] = (TextView) layQH.findViewById(R.id.tv_c_dk);
		tvQHs[11] = (TextView) layQH.findViewById(R.id.tv_c_num);
		// 全行_合计
		tvQHs[12] = (TextView) layQH.findViewById(R.id.tv_z_hq);
		tvQHs[13] = (TextView) layQH.findViewById(R.id.tv_z_dq);
		tvQHs[14] = (TextView) layQH.findViewById(R.id.tv_z_lc);
		tvQHs[15] = (TextView) layQH.findViewById(R.id.tv_z_total);
		tvQHs[16] = (TextView) layQH.findViewById(R.id.tv_z_dk);
		tvQHs[17] = (TextView) layQH.findViewById(R.id.tv_z_num);
		// 管行_个人业务 - [横向初始化控件]
		tvGHs[0] = (TextView) layGH.findViewById(R.id.tv_p_hq);
		tvGHs[1] = (TextView) layGH.findViewById(R.id.tv_p_dq);
		tvGHs[2] = (TextView) layGH.findViewById(R.id.tv_p_lc);
		tvGHs[3] = (TextView) layGH.findViewById(R.id.tv_p_total);
		tvGHs[4] = (TextView) layGH.findViewById(R.id.tv_p_dk);
		tvGHs[5] = (TextView) layGH.findViewById(R.id.tv_p_num);
		// 管行_对公业务
		tvGHs[6] = (TextView) layGH.findViewById(R.id.tv_c_hq);
		tvGHs[7] = (TextView) layGH.findViewById(R.id.tv_c_dq);
		tvGHs[8] = (TextView) layGH.findViewById(R.id.tv_c_lc);
		tvGHs[9] = (TextView) layGH.findViewById(R.id.tv_c_total);
		tvGHs[10] = (TextView) layGH.findViewById(R.id.tv_c_dk);
		tvGHs[11] = (TextView) layGH.findViewById(R.id.tv_c_num);
		// 管行_合计
		tvGHs[12] = (TextView) layGH.findViewById(R.id.tv_z_hq);
		tvGHs[13] = (TextView) layGH.findViewById(R.id.tv_z_dq);
		tvGHs[14] = (TextView) layGH.findViewById(R.id.tv_z_lc);
		tvGHs[15] = (TextView) layGH.findViewById(R.id.tv_z_total);
		tvGHs[16] = (TextView) layGH.findViewById(R.id.tv_z_dk);
		tvGHs[17] = (TextView) layGH.findViewById(R.id.tv_z_num);
	}
	
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
	}
	
	/**
	 * 显示管行数据
	 * @param propertyinfo
	 */
	private void setPropertyInfo(TextView[] tvValues, Propertiesinfo propertyinfo){
		String[] values = { "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", 
							"0.00", "0.00", "0.00", "0.00", "0.00", "0.00",
							"0.00", "0.00", "0.00", "0.00", "0.00", "0.00" };
		if(propertyinfo != null){
			// 个人业务_全行资产信息
			values[0] = propertyinfo.getPSN_CUR_DEPS_BAL();
			values[1] = propertyinfo.getPSN_FIX_DEPS_BAL();
			values[2] = propertyinfo.getPSN_FINA_BAL();
			values[3] = propertyinfo.getPSN_BAL();
			values[4] = propertyinfo.getPSN_LOAN_BAL();
			values[5] = propertyinfo.getPSN_CUST_NUM();
			// 对公业务
			values[6] = propertyinfo.getENT_CUR_DEPS_BAL();
			values[7] = propertyinfo.getENT_FIX_DEPS_BAL();
			values[8] = propertyinfo.getENT_FINA_BAL();
			values[9] = propertyinfo.getENT_BAL();
			values[10] = propertyinfo.getENT_LOAN_BAL();
			values[11] = propertyinfo.getENT_CUST_NUM();
			// 合计
			values[12] = propertyinfo.getCUR_DEP_BAL();
			values[13] = propertyinfo.getRMB_FIX_DEP_BAL();
			values[14] = propertyinfo.getFINA_BAL();
			values[15] = propertyinfo.getBAL();
			values[16] = propertyinfo.getLOAN_BAL();
			values[17] = propertyinfo.getCUST_NUM();
		}
		for(int i=0; i<tvValues.length; i++){
			tvValues[i].setText(Tool.setFormatValue(values[i]));
		}
	}
	
	/**
	 * 查询全行管辖资产List列表信息
	 */
	private void getQHDatas() {
		String staid = LogoActivity.user.getStaId();// 岗位ID
		String requestType = InterfaceInfo.MYREPORTTAB01_QH_Get;// 全行管辖资产
		
		String info = "&spareOne=" + staid;
		HConQH = RequestInfo(this, Constants.requestType.Other, requestType,
				info, INDEX_QH, false);
	}
	
	/**
	 * 查询管户管辖资产List列表信息
	 */
	private void getGHDatas() {
		String staid = LogoActivity.user.getStaId();// 岗位ID
		String requestType = InterfaceInfo.MYREPORTTAB01_GH_Get;// 管户管辖资产
		
		String info = "&spareOne=" + staid;
		HConGH = RequestInfo(this, Constants.requestType.Other, requestType,
				info, INDEX_GH, false);
	}

	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
			break;
		// 解析返回结果列表
		case INDEX_QH:
			res = HConQH.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
			
		case INDEX_GH:
			res = HConGH.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
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
			Toast.makeText(getActivity(), "请求失败! 错误代码:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
}
