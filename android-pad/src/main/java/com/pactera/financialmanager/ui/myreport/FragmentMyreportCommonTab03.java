package com.pactera.financialmanager.ui.myreport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Deposit;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-对公-贷存款趋势图
 * 
 * @author dg
 */
public class FragmentMyreportCommonTab03 extends ParentFragment implements OnClickListener {

	private LinearLayout layLoading;
	private RadioButton rbtnItempro1, rbtnItempro2, rbtnItempro3, rbtnItempro4;
	private WebView chartshow_wb;

	private HConnection HCon;
	private static final int INDEX_STRUCTURE = 1;
	
	private List<Deposit> deposits = null;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 获取我的客户列表 */
			case INDEX_STRUCTURE:
				deposits = (ArrayList<Deposit>) msg.obj;
				if(deposits != null){
					//loadingDatas(channel);
					Toast.makeText(getActivity(), "数据请求成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "error:数据解析失败！！！", Toast.LENGTH_SHORT).show();
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
		View view = inflater.inflate(R.layout.fragment_myreport_common_tab03, null);
		findViewID(view);
		bindOnClick();
		getDatas();
		return view;
	}

	/**
	 * 初始化页面元素
	 */
	private void findViewID(View view) {
		// 初始化本地控件
		rbtnItempro1 = (RadioButton) view.findViewById(R.id.rbt_itempro1);
		rbtnItempro2 = (RadioButton) view.findViewById(R.id.rbt_itempro2);
		rbtnItempro3 = (RadioButton) view.findViewById(R.id.rbt_itempro3);
		rbtnItempro4 = (RadioButton) view.findViewById(R.id.rbt_itempro4);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		chartshow_wb = (WebView) view.findViewById(R.id.chartshow_wb);
		
		// 进行webwiev的一堆设置
		// 开启本地文件读取（默认为true，不设置也可以）
		chartshow_wb.getSettings().setAllowFileAccess(true);
		chartshow_wb.getSettings().setJavaScriptEnabled(true);// 开启脚本支持
		chartshow_wb.loadUrl("file:///android_asset/echart/myreport_commontab03.html");
		// 延迟加载
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				layLoading.setVisibility(View.GONE);
				chartshow_wb.loadUrl("javascript:createChart('bar',['按揭还款1','按揭还款2','按揭还款3','按揭还款4','按揭还款5'],[380,370,380,460,520]);"); 
			}
		}, 500);
	}
	
	private void bindOnClick(){
		rbtnItempro1.setOnClickListener(this);
		rbtnItempro2.setOnClickListener(this);
		rbtnItempro3.setOnClickListener(this);
		rbtnItempro4.setOnClickListener(this);
	}
	

	/**
	 * 查询List列表信息
	 */
	private void getDatas() {
		// 109001 111111
		if (Tool.haveNet(getActivity())) {
			String usercodeStr = LogoActivity.user.getUserCode();
			String brcodeStr = LogoActivity.user.getBrCode();
			HRequest request = null;
			// 此处默认查询全部
			String requestHttp = "T000204?method=getJSON"
					+ "&userCode=" + usercodeStr
					+ "&seriNo=DMRJRUWQF182&chnlCode=DMRJRUWQF182&transCode=T000204"
					+ "&brCode=" + brcodeStr
					+ "&spareOne=123";// 岗位ID
			
			request = new HRequest(requestHttp, "GET");
			Log.i("RETURNCUS", requestHttp);
			
			try {
				HConnection.isShowLoadingProcess = false; // 不显示loading
				HCon = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				HCon.setIndex(INDEX_STRUCTURE);
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
		case INDEX_STRUCTURE:
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
			Message msg = new Message();
			msg.arg1 = connectionIndex;
			// 获取列表
			if(connectionIndex == INDEX_STRUCTURE){
				List<Deposit> deposits = analyDepositLists(tmpJsonObject);
				msg.obj = deposits;
			}
			handler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), "操作失败! 错误:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
	

	/**
	 * 解析获取列表接口
	 * @param tmpJsonObject
	 * @return
	 */
	private List<Deposit> analyDepositLists(JSONObject tmpJsonObject){
		List<Deposit> depositList = new ArrayList<Deposit>();
		try {
			JSONArray theInfo = tmpJsonObject.getJSONArray("group");
			if (theInfo.length() < 0) {
				Toast.makeText(getActivity(), "没有数据!", Toast.LENGTH_SHORT).show();
			} else {
				for (int i = 0; i < theInfo.length(); i++) {
					JSONObject temps = (JSONObject) theInfo.opt(i);
					Deposit tempInfo = new Deposit();
					
					if (temps.has("TJ_DATE")) {
						tempInfo.setTJ_DATE(temps.getString("TJ_DATE"));// 客户ID
					}
					if (temps.has("DEPS_BAL")) {
						tempInfo.setDEPS_BAL(temps.getString("DEPS_BAL"));// 客户中文名称
					}
					depositList.add(tempInfo);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return depositList;
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rbt_itempro1:
			if(deposits != null){
				chartshow_wb.loadUrl("javascript:createChart('bar',['按揭还款1','按揭还款2','按揭还款3','按揭还款4','按揭还款5'],[380,370,380,460,520]);"); 
			}
			break;
			
		case R.id.rbt_itempro2:
			chartshow_wb.loadUrl("javascript:createChart('line',['第一种','第二种','第三种','第四种'],[20,25,20,35]);"); 
			break;
			
		case R.id.rbt_itempro3:
			chartshow_wb.loadUrl("javascript:createChart('pie', ['理财1','理财2','理财3','理财4','理财5'], [{name:'理财1',value:200},{name:'理财2',value:100},"
					+"{name:'理财3',value:120},{name:'理财4',value:140},{name:'理财5',value:80}]);");
			break;
			
			
		case R.id.rbt_itempro4:
			chartshow_wb.loadUrl("javascript:createChart('bar2',['短信银行','pos','手机银行','微信银行','信用卡'],[380,370,380,460,520]);"); 
			break;

		default:
			break;
		}
	}
}
