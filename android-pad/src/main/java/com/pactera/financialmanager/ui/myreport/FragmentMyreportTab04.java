package com.pactera.financialmanager.ui.myreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.pactera.financialmanager.ui.model.ComparatorCusttype;
import com.pactera.financialmanager.ui.model.Proportion;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Catevalue;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

/**
 * 我的业绩-对个人-客户价值评级
 * 
 * @author dg
 */
public class FragmentMyreportTab04 extends ParentFragment {

	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private WebView wbEChart;
	private ImageView imvRefresh;

	private HConnection HCon;
	private static final int INDEX_CITY = 1;
	private static final int INDEX_PRO = 2;
	private final String TYPE_CITY = "01";
	private final String TYPE_PRO = "02";
	private List<Proportion> cityForperson = null;
	private List<Proportion> proForperson = null;
	private List<Proportion> cityForcommon = null;
	private List<Proportion> proForcommon = null;

	private boolean isForperson = true;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case INDEX_CITY:
				List<Proportion> resultCityPortion = (ArrayList<Proportion>) msg.obj;
				if (resultCityPortion != null && resultCityPortion.size() > 0) {
					resultCityPortion = combineCollect(resultCityPortion);
					if (isForperson) {
						cityForperson = resultCityPortion;
					} else {
						cityForcommon = resultCityPortion;
					}
					getDatas(INDEX_PRO, TYPE_PRO);
				} else {
					hiddentLoading(true);
					//Toast.makeText(getActivity(), "error:请求接口数据解析失败！！！", Toast.LENGTH_SHORT).show();
				}
				break;

			case INDEX_PRO:
				List<Proportion> resultProPortion = (ArrayList<Proportion>) msg.obj;
				if (resultProPortion != null && resultProPortion.size() > 0) {
					resultProPortion = combineCollect(resultProPortion);
					if (isForperson) {
						proForperson = resultProPortion;
					} else {
						proForcommon = resultProPortion;
					}

					// 根据集合显示内容
					List<Proportion> resultCityDatas = null;
					List<Proportion> resultProDatas = null;
					if (isForperson) {
						resultCityDatas = cityForperson;
						resultProDatas = proForperson;
					} else {
						resultCityDatas = cityForcommon;
						resultProDatas = proForcommon;
					}
					setECharsViews(resultCityDatas, resultProDatas);
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
		View view = inflater.inflate(R.layout.fragment_myreport_tab04, null);

		findViews(view);
		getDatas(INDEX_CITY, TYPE_CITY);
		// getDatas(INDEX_PRO, TYPE_PRO);

		return view;
	}

	private void findViews(View view) {
		// 初始化本地控件
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		wbEChart = (WebView) view.findViewById(R.id.chartshow_wb);

		// 开启本地文件读取（默认为true，不设置也可以）
		wbEChart.getSettings().setAllowFileAccess(true);
		wbEChart.getSettings().setJavaScriptEnabled(true);// 开启脚本支持
		wbEChart.loadUrl("file:///android_asset/echart/myreport_persontab04.html");

		imvRefresh = (ImageView) view.findViewById(R.id.imv_refresh);
		// 手动刷新
		imvRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 根据集合显示内容
				List<Proportion> resultCityDatas = null;
				List<Proportion> resultProDatas = null;
				if (isForperson) {
					resultCityDatas = cityForperson;
					resultProDatas = proForperson;
				} else {
					resultCityDatas = cityForcommon;
					resultProDatas = proForcommon;
				}
				setECharsViews(resultCityDatas, resultProDatas);
			}
		});
	}

	private void loading() {
		layLoading.setVisibility(View.VISIBLE);
		layNodata.setVisibility(View.GONE);
		wbEChart.setVisibility(View.GONE);
	}

	private void hiddentLoading(boolean isNodata) {
		layLoading.setVisibility(View.GONE);
		if (isNodata) {
			layNodata.setVisibility(View.VISIBLE);
			wbEChart.setVisibility(View.GONE);
		} else {
			layNodata.setVisibility(View.GONE);
			wbEChart.setVisibility(View.VISIBLE);
		}
	}

	public void setIsForperson(boolean isForperson) {
		this.isForperson = isForperson;
		if (getActivity() == null) {
			return;
		}

		List<Proportion> resultCityDatas = null;
		List<Proportion> resultProDatas = null;
		if (isForperson) {
			resultCityDatas = cityForperson;
			resultProDatas = proForperson;
		} else {
			resultCityDatas = cityForcommon;
			resultProDatas = proForcommon;
		}
		setECharsViews(resultCityDatas, resultProDatas);
	}

	/**
	 * 设置Echars视图
	 * 
	 * @param cityDatas
	 * @param proDatas
	 */
	private void setECharsViews(List<Proportion> cityDatas,
			List<Proportion> proDatas) {
		// 显示数据
		if (cityDatas != null && cityDatas.size() > 0 && proDatas != null
				&& proDatas.size() > 0) {
			loadECharsView(cityDatas, proDatas);
		} else {
			if (cityDatas == null) {
				getDatas(INDEX_CITY, TYPE_CITY);
			} else if (proDatas == null) {
				getDatas(INDEX_PRO, TYPE_PRO);
			}
		}
	}

	/**
	 * 合并集合信息
	 * @param aList
	 * @return
	 */
	private List<Proportion> combineCollect(List<Proportion> aList) {
		// 新集合
		List<Proportion> bList = new ArrayList<Proportion>();
		// map集合
		Map<String, Integer> map = new HashMap<String, Integer>();
		String custType = "";
		int custNum = 0;
		// 存出类型值
		for (int i = 0; i < aList.size(); i++) {
			custType = aList.get(i).getCUSTTYPE();
			custNum = Integer.parseInt(aList.get(i).getCUST_NUM());
			map.put(custType, custNum);
		}

		// 根据类型值对数量相加
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			int value = map.get(key);
			for (int i = 0; i < aList.size(); i++) {
				custType = aList.get(i).getCUSTTYPE();
				custNum = Integer.parseInt(aList.get(i).getCUST_NUM());
				if (key.equals(custType)) {
					value += custNum;
				}
			}
			// 减去自己的数
			int newNum = value - map.get(key);
			Proportion p = new Proportion(key, newNum + "");
			bList.add(p);
		}

		// 排序结果
		ComparatorCusttype<Proportion> comparator = new ComparatorCusttype<Proportion>();
		Collections.sort(bList, comparator);
		return bList;
	}

	public void loadTestDatas() {
		// 延迟加载
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 市州级数据
				String title1Array = "['理财1','理财2','理财3','理财4','理财5']";
				String data1Array = "[{name:'理财1',value:200},"
						+ "{name:'理财2',value:100}," + "{name:'理财3',value:120},"
						+ "{name:'理财4',value:140}," + "{name:'理财5',value:80}]";
				// 省级数据
				String title2Array = "['理财6','理财7','理财8']";
				String data2Array = "[{name:'理财6',value:200},"
						+ "{name:'理财7',value:100}," + "{name:'理财8',value:80}]";
				wbEChart.loadUrl("javascript:createChart('pie'," + title1Array
						+ "," + data1Array + "," + title2Array + ","
						+ data2Array + ");");
			}
		}, 1000);
	}

	/**
	 * 加载数据
	 * 
	 * @param cityPortions
	 * @param proPortions
	 */
	private void loadECharsView(final List<Proportion> cityPortions,
			final List<Proportion> proPortions) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 类型和数量
				String cityTypes = "";
				String cityDatas = "";
				for (int i = 0; i < cityPortions.size(); i++) {
					String cType = cityPortions.get(i).getCUSTTYPE();
					cType = "'"
							+ NewCatevalue.getLabel(getActivity(),
									NewCatevalue.ent_cust_value_level, cType)
							+ "'"; // 值转码
					String cNum = cityPortions.get(i).getCUST_NUM();

					String itemData = "{name:" + cType + ",value:" + cNum + "}";
					if (i < cityPortions.size() - 1) {
						cType += ",";
						itemData += ",";
					}
					cityTypes += cType;
					cityDatas += itemData;
				}

				String proTypes = "";
				String proDatas = "";
				int totalNum = 0;
				for (int i = 0; i < proPortions.size(); i++) {
					String cType = proPortions.get(i).getCUSTTYPE();
					cType = "'"
							+ NewCatevalue.getLabel(getActivity(),
									NewCatevalue.ent_cust_value_level, cType)
							+ "'"; // 值转码
					String cNum = proPortions.get(i).getCUST_NUM();
					if (TextUtils.isEmpty(cNum) || cNum.equals("0")) {
						cNum = "0";
					}
					totalNum += Integer.parseInt(cNum);
					String itemData = "{name:" + cType + ",value:" + cNum + "}";
					if (i < proPortions.size() - 1) {
						cType += ",";
						itemData += ",";
					}
					proTypes += cType;
					proDatas += itemData;
				}

				if(totalNum != 0){
					hiddentLoading(false);
					// EChars加载地址
					String loadUrl = "javascript:createChart('pie', [" + cityTypes
							+ "], [" + cityDatas + "], [" + proTypes + "], ["
							+ proDatas + "]);";
					wbEChart.loadUrl(loadUrl);
					Log.d("loadUrl", "-->" + loadUrl);
				}else{
					hiddentLoading(true);
				}
			}
		}, 500);
	}

	/**
	 * 查询结果集
	 * 
	 * @param typeStr
	 */
	private void getDatas(int index, String typeStr) {
		if (Tool.haveNet(getActivity())) {
			loading();
			String staidStr = LogoActivity.user.getStaId();

			String requestType = InterfaceInfo.MYREPORTTAB04_Get;
			String custtypeStr = "01";
			if (!isForperson) {
				custtypeStr = "02";
			}

			JSONObject jsinfo = null;
			try {
				jsinfo = new JSONObject();
				jsinfo.put("staid", staidStr);
				jsinfo.put("custtype", custtypeStr);
				jsinfo.put("type", typeStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			String info = jsinfo.toString();
			HCon = RequestInfo(this, Constants.requestType.JsonData,
					requestType, info, index, false);
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
		case INDEX_PRO:
		case INDEX_CITY:
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
			try {
				String group = tmpJsonObject.getString("group");
				List<Proportion> returnProportion = JSON.parseArray(group,
						Proportion.class);
				msg.obj = returnProportion;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		} else {
			hiddentLoading(true);
			Toast.makeText(getActivity(), "操作失败! 错误:" + retCode,
					Toast.LENGTH_SHORT).show();
		}
	}

}
