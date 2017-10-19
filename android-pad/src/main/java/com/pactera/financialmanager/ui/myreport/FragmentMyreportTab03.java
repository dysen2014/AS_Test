package com.pactera.financialmanager.ui.myreport;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Channel;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-对个人-渠道签约情况
 * 
 * @author dg
 */
public class FragmentMyreportTab03 extends ParentFragment {

	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private WebView wbEChart;
	private ImageView imvRefresh;

	private HConnection HCon;
	private static final int INDEX_CHANNEL = 1;
	private Channel channelForperson = null;
	private Channel channelForcommon = null;
	
	private boolean isForperson = true;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case INDEX_CHANNEL:
				Channel channel = (Channel) msg.obj;
				if(channel != null){
					if(isForperson){
						channelForperson = channel;
					}else{
						channelForcommon = channel;
					}
					loadingDatas(channel);
				}else{
					hiddentLoading(true);
					//Toast.makeText(getActivity(), "error:数据解析失败！！！", Toast.LENGTH_SHORT).show();
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
		View view = inflater.inflate(R.layout.fragment_myreport_tab03, null);

		findViews(view);
		getDatas();
		//loadTestDatas();
		
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
		wbEChart.loadUrl("file:///android_asset/echart/myreport_persontab03.html");

		imvRefresh = (ImageView) view.findViewById(R.id.imv_refresh);
		// 手动刷新
		imvRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isForperson){
					loadingDatas(channelForperson);
				}else{
					loadingDatas(channelForcommon);
				}
			}
		});
	}

	
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
		
		Channel channelTemp = null;
		if(isForperson){
			channelTemp = channelForperson;
		}else{
			channelTemp = channelForcommon;
		}
		if(channelTemp != null){
			loadingDatas(channelTemp);
		}else{
			getDatas();
		}
		//loadTestDatas();
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
	
	/**
	 * 添加测试数据
	 */
	private void loadTestDatas(){
		// 延迟加载
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(isForperson){
					wbEChart.loadUrl("javascript:createChart('bar',['网银','手机银行','商务手机银行','POS','卡支付', '微信银行', '信用卡', '短信银行'],[380,470,320,390,220,480,510,460]);"); 
				}else{                                                                   //网银 ,POS，卡乐付, 短信银行、代发工资
					wbEChart.loadUrl("javascript:createChart('bar',['网银','POS','卡支付','短信银行','代发工资'],[738,470,620,480,810]);"); 
				}
			}
		}, 1000);
	}
	
	/**
	 * 显示数据
	 * @param channel
	 */
	private void loadingDatas(final Channel channel){
		if(channel == null){
			return;
		}
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				String eBank = checkStr(channel.getE_BANK());
				String mobileBank = checkStr(channel.getMOBIL_BANK());
				String busiMobilBank = checkStr(channel.getBUSI_MOBIL_BANK());
				String pos = checkStr(channel.getPOS());
				String carlPay = checkStr(channel.getCARL_PAY());
				String wechatBank = checkStr(channel.getWECHAT_BANK());
				String depsCard = checkStr(channel.getDEPS_CARD());
				String noteService = checkStr(channel.getNOTE_SERVICE());
				String dfSalary = checkStr(channel.getDF_SALARY());
				hiddentLoading(false);
				
				// 拼装数据
				String xTitles = "'网银','手机银行','商务手机银行','POS','卡乐付', '微信银行', '电话银行', '短信银行'";
				String yDatas = eBank+","+mobileBank+","+busiMobilBank+","+pos
						+","+carlPay+","+wechatBank+","+depsCard+","+noteService;
				if(!isForperson){
					xTitles = "'网银','POS','卡支付','短信银行','代发工资'";
					yDatas = eBank+","+pos+","+carlPay+","+noteService+","+dfSalary;
				}
				final String loadUrl = "javascript:createChart('bar',["+xTitles+"]," +"["+yDatas+"]);";
				// EChars容器显示数据
				wbEChart.loadUrl(loadUrl);
			}
			
			/**
			 * 转义非空字段为0
			 * @param str
			 * @return
			 */
			private String checkStr(String str){
				if(str == null || TextUtils.isEmpty(str)){
					str = "0";
				}
				return str;
			}
		}, 1000);
	}
	

	/**
	 * 查询List列表信息
	 */
	private void getDatas() {
		if (Tool.haveNet(getActivity())) {
			loading();
			
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.MYREPORTTAB03_Get;
			String typeStr = "01";
			if(!isForperson){
				typeStr = "02";
			}
			String info = "&spareOne=" + staid + "&spareTwo=" + typeStr;
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_CHANNEL, false);
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
		case INDEX_CHANNEL:
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
			if(connectionIndex == INDEX_CHANNEL){
				Channel channel = JSON.parseObject(tmpJsonObject.toString(), Channel.class);
				msg.obj = channel;
			}
			handler.sendMessage(msg);
		} else {
			hiddentLoading(true);
			Toast.makeText(getActivity(), "操作失败! 错误:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
}
