package com.pactera.financialmanager.ui.myreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.ComparatorDeposit;
import com.pactera.financialmanager.ui.model.Deposit;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 我的业绩-对个人-存款趋势图&金融资产月日趋势图
 * @author dg
 */
public class FragmentMyreportTab02 extends ParentFragment {

	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private WebView wbEChart;
	private ImageView imvRefresh;

	private HConnection HCon;
	private static final int INDEX_TREND = 1;
	private List<Deposit> depositsForperson = null;
	private List<Deposit> depositsForcommon = null;
	
	private boolean isForperson = true;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 获取数据列表 */
			case INDEX_TREND:
				List<Deposit> result = (ArrayList<Deposit>)msg.obj;
				if(result != null && result.size()>0){
					// 根据日期排序
					ComparatorDeposit<Deposit> comparator = new ComparatorDeposit<Deposit>();
					Collections.sort(result, comparator);
					  
					if(isForperson){
						depositsForperson = result;
					}else{
						depositsForcommon = result;
					}
					loadEChars(result);
				}else{
					hiddentLoading(true);
					//Toast.makeText(getActivity(), "error:数据失败！！！", Toast.LENGTH_SHORT).show();
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
		View view = inflater.inflate(R.layout.fragment_myreport_tab02, null);
		
		findViews(view);
		getDepositDatas();
		//loadTestData();
		return view;
	}
	
	private void findViews(View view){
		// 初始化本地控件
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		wbEChart = (WebView) view.findViewById(R.id.chartshow_wb);
		
		// 开启本地文件读取（默认为true，不设置也可以）
		wbEChart.getSettings().setAllowFileAccess(true);
		wbEChart.getSettings().setJavaScriptEnabled(true);// 开启脚本支持
		wbEChart.loadUrl("file:///android_asset/echart/myreport_persontab02.html");
		
//		if(isForperson){
//			chartshow_wb.loadUrl("file:///android_asset/echart/myreport_persontab02.html");
//		}else{
//			chartshow_wb.loadUrl("file:///android_asset/echart/myreport_persontab02_c.html");
//		}

		imvRefresh = (ImageView) view.findViewById(R.id.imv_refresh);
		// 手动刷新
		imvRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isForperson){
					loadEChars(depositsForperson);
				}else{
					loadEChars(depositsForcommon);
				}
			}
		});
	}
	
	private void loading(){
		layLoading.setVisibility(View.VISIBLE);
		layNodata.setVisibility(View.GONE);
		wbEChart.setVisibility(View.GONE);
	}
	
	private void hiddentLoading(boolean isNodata){
		layLoading.setVisibility(View.GONE);
		if(isNodata){
			layNodata.setVisibility(View.VISIBLE);
			wbEChart.setVisibility(View.GONE);
		}else{
			layNodata.setVisibility(View.GONE);
			wbEChart.setVisibility(View.VISIBLE);
		}
	}
	
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
		
		// 对公对私切换视图
		List<Deposit> depositTemps = null;
		if(isForperson){
			depositTemps = depositsForperson;
		}else{
			depositTemps = depositsForcommon;
		}
		if(depositTemps != null && depositTemps.size()>0){
			loadEChars(depositTemps);
		}else{
			getDepositDatas();
		}
		
		// 判断对公对私-对公对私不同视图（暂时不实现此功能）
//		if(isForperson){
//			chartshow_wb.loadUrl("file:///android_asset/echart/myreport_persontab02.html");
//		}else{
//			chartshow_wb.loadUrl("file:///android_asset/echart/myreport_persontab02_c.html");
//		}
//		loadTestData();
	}
	
	/**
	 * 测试数据
	 */
	public void loadTestData(){
		hiddentLoading(false);
		
		//测试数据
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				String xDates = "";
				String yValues = "";
				Random random = new Random();
				String day = "00";
				for(int i=0; i<15; i++){
					day = ""+i;
					if(i<10){
						day = "0"+(i+1);
					}
					String dateStr = "'201510"+day+"'";
					String valueStr = (10000 + random.nextInt((5*1000)))+"";
					if(i<14){
						dateStr += ",";
						valueStr += ",";
					}
					xDates += dateStr;
					yValues += valueStr;
				}
				wbEChart.loadUrl("javascript:createChart('line', "
						+"["+xDates+"], "
						+"["+yValues+"]);");  
			}
		}, 500);
	}
	
	/**
	 * 显示折线图
	 * @param deposits
	 */
	private void loadEChars(final List<Deposit> deposits) {
		if(deposits == null){
			return;
		}
		
		// 测试数据
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				String xDatas = "";
				String yDatas = "";
				// 循环遍历结果集
				for (int i = 0; i < deposits.size(); i++) {
					String tjDate = "'" + deposits.get(i).getTJ_DATE() + "'"; // '20151101'
					String bal = deposits.get(i).getDEPS_BAL(); // 5000
					// 添加分隔符
					if (i < deposits.size() - 1) {
						tjDate += ", ";
						bal += ", ";
					}
					xDatas += tjDate;
					yDatas += bal;
				}
				
				hiddentLoading(false);
				
				// EChars加载地址
				String loadUrl = "javascript:createChart('line', "+"[" + xDatas + "]"+",[" + yDatas + "]);";
				// 判断是否有拼装数据
				wbEChart.loadUrl(loadUrl);
			}
		}, 1000);
	}

	/**
	 * 查询List列表信息
	 */
	private void getDepositDatas() {
		if (Tool.haveNet(getActivity())) {
			loading();

			String requestType = InterfaceInfo.MYREPORTTAB02_Get;
			String staidStr = LogoActivity.user.getStaId();
			String type = "01";
			if (!isForperson) {
				type = "02";
			}
			String info = "&spareOne=" + staidStr + "&spareTwo=" + type;
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_TREND, false);
		}
	}
	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddentLoading(true);
			Toast.makeText(getActivity(), "系统请求错误", Toast.LENGTH_SHORT).show();
			break;
		// 解析返回结果列表
		case INDEX_TREND:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			Log.i("RETURNCUS", "res:-->"+res);
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
			if(connectionIndex == INDEX_TREND){
				List<Deposit> deposits = analyDepositLists(tmpJsonObject);
				msg.obj = deposits;
			}
			handler.sendMessage(msg);
		} else {
			hiddentLoading(true);
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
	
	
}
