package com.pactera.financialmanager.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.model.BigFund;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

/**
 * 这是大额资金变动 对应的子fragment
 */
public class FragmentBigMoneyChange extends ParentFragment implements
		OnClickListener {

	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private TextView tvSingleOne, tvSingleTotal;
	private View line1, line4;
	private TextView tvAccNo, tvChangetime;
//	private ListView lvProInfo;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private BigFundAdapter bigfundAdapter = null;

	private String selectType = "7";
	private int offset = 1;	
	private boolean isLoadmore = false;			// 是否上拉加载更多
	
	// 接口请求
	private HConnection HCon;
	private static final int INDEX_ONE = 1;
	private static final int INDEX_TOTAL = 2;
	private int selectTab = INDEX_ONE;

	// 单笔交易
	private List<BigFund> singleOneForperson = new ArrayList<BigFund>(); // 对个人
	private List<BigFund> singleOneForcommon = new ArrayList<BigFund>(); // 对公
	// 单日累计
	private List<BigFund> singleTotalForperson = new ArrayList<BigFund>();
	private List<BigFund> singleTotalForcommon = new ArrayList<BigFund>();

	public boolean isForperson = true;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(isLoadmore){
				// 隐藏地步
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			
			switch (msg.arg1) {
			// 1.单笔超额
			case INDEX_ONE:
				List<BigFund> singleOneResults = (ArrayList<BigFund>) msg.obj;
				if (isForperson) {
					singleOneForperson.addAll(singleOneResults);
					setBigMoneyChange(singleOneForperson);
				} else {
					singleOneForcommon.addAll(singleOneResults);
					setBigMoneyChange(singleOneForcommon);
				}
				
				break;

			// 2.当日累计超额
			case INDEX_TOTAL:
				List<BigFund> singleTotalResults = (ArrayList<BigFund>) msg.obj;
				if (isForperson) {
					singleTotalForperson.addAll(singleTotalResults);
					setBigMoneyChange(singleTotalForperson);
				} else {
					singleTotalForcommon.addAll(singleTotalResults);
					setBigMoneyChange(singleTotalForcommon);
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
		View view = inflater.inflate(
				R.layout.fragment_personitembigmoneychange, null);
		setupView(view);
		setListener();

		setTabTitle();
		setBgAndTextColor(tvSingleOne, INDEX_ONE);
		sendRequestForInfo(INDEX_ONE);

		return view;
	}

	private void setupView(View view) {
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		tvSingleOne = (TextView) view.findViewById(R.id.tv_itemcust51);
		tvSingleTotal = (TextView) view.findViewById(R.id.tv_itemcust52);
//		lvProInfo = (ListView) view.findViewById(R.id.lv_workplaceicon52);
		ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
		
		line1 = (View) view.findViewById(R.id.line1);
		line4 = (View) view.findViewById(R.id.line4);
		tvAccNo = (TextView) view.findViewById(R.id.lvitem_info2);
		tvChangetime = (TextView) view.findViewById(R.id.lvitem_info5);
		bigfundAdapter = new BigFundAdapter();
//		lvProInfo.setAdapter(bigfundAdapter);
		lvList.setAdapter(bigfundAdapter);
		lvList.setPullstatus(false, true);
		bigfundAdapter.setSelectTab(selectTab);
	}

	private void setListener() {
		tvSingleOne.setOnClickListener(this);
		tvSingleTotal.setOnClickListener(this);
		
		ptrl.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// 下拉刷新
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// 上拉加载更多
				isLoadmore = true;
				offset++;
				
				sendRequestForInfo(selectTab);
			}
		});
	}

	/**
	 * 设置是否为对个人状态
	 * 
	 * @param isForperson
	 */
	public void setIsForperson(boolean isForperson) {
		this.isForperson = isForperson;
		if (getActivity() == null) {
			return;
		}
		offset = 1;
		setTabTitle();

		// 判断是否为对公、对个人
		if (isForperson) {
			singleOneForperson.removeAll(singleOneForperson);
			selectType = "7";
			sendRequestForInfo(INDEX_ONE);
		} else {
			singleOneForcommon.removeAll(singleOneForcommon);
			selectType = "23";
			sendRequestForInfo(INDEX_ONE);
		}
	}

	/**
	 * 设置二级标签标题
	 */
	private void setTabTitle() {
		// 消息提醒头集合（改变二级tab标题）
		List<CustEvent> events = WorkWarnFragment.tempEventsForPerson;
		if (!isForperson) {
			events = WorkWarnFragment.tempEventsForCommon;
		}

		if (events != null) {
			// 迭代获取标题名称
			for (CustEvent event : events) {
				String eventType = event.getEvent_type(); // 类型
				String eventName = event.getEvent_type_name(); // 名称
				// 单笔超额
				if (eventType.equals("7") || eventType.equals("23")) {
					tvSingleOne.setText(eventName);
				}
				// 累计超额
				if (eventType.equals("8") || eventType.equals("24")) {
					tvSingleTotal.setText(eventName);
				}
			}
		}
	}

	/**
	 * 显示进度条
	 */
	private void loading() {
		layLoading.setVisibility(View.VISIBLE);
		//lvProInfo.setVisibility(View.GONE);
		if(offset == 1){
			ptrl.setVisibility(View.GONE);
		}
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
			//lvProInfo.setVisibility(View.GONE);
			ptrl.setVisibility(View.GONE);
		} else {
			layNodata.setVisibility(View.GONE);
			//lvProInfo.setVisibility(View.VISIBLE);
			ptrl.setVisibility(View.VISIBLE);
		}
	}

	private void setBgAndTextColor(TextView tv, int selectTab) {
		tvSingleOne.setEnabled(true);
		tvSingleTotal.setEnabled(true);
		tvSingleOne.setTextColor(getResources().getColor(
				R.color.separatelightredline));
		tvSingleTotal.setTextColor(getResources().getColor(
				R.color.separatelightredline));
		line1.setVisibility(View.VISIBLE);
		line4.setVisibility(View.VISIBLE);
		tvAccNo.setVisibility(View.VISIBLE);
		tvChangetime.setVisibility(View.VISIBLE);
		// 累计消费
		if (selectTab == INDEX_TOTAL) {
			line1.setVisibility(View.GONE);
			line4.setVisibility(View.GONE);
			tvAccNo.setVisibility(View.GONE);
			tvChangetime.setVisibility(View.GONE);
		}

		tv.setEnabled(false);
		tv.setTextColor(getResources().getColor(R.color.white));
	}

	private void sendRequestForInfo(int index) {
		// 对公对私
		String spareTwo = "01";
		if(!isForperson){
			spareTwo = "02";
		}
				
		if (Tool.haveNet(getActivity())) {
			loading();
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.PRODATA_Get;
			String info =  "&size=20&offset="+offset+"&spareOne=" + selectType + "&spareTwo=" + spareTwo
					+ "&jsonData={staid:\"" + staid + "\"}";
			HCon = RequestInfo(this, Constants.requestType.Other, requestType,
					info, index, false);
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);

		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
			break;

		case INDEX_ONE:
		case INDEX_TOTAL:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
		}
	}

	/**
	 * 解析接口数据
	 * 
	 * @param tmpJsonObject
	 * @param connectionIndex
	 */
	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
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

			// 结果集合
			List<BigFund> bigfundForperson = null;
			String group = "";
			try {
				switch (connectionIndex) {
				case INDEX_ONE:
				case INDEX_TOTAL:
					group = tmpJsonObject.getString("group");
					bigfundForperson = JSON.parseArray(group, BigFund.class);
					msg.obj = bigfundForperson;
					break;

				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			handler.sendMessage(msg);
		} else {
			hiddenLoading(true);
			Toast.makeText(getActivity(), "请求失败! 错误代码:" + retCode,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 设置头数据
	 * 
	 * @param bigfundLists
	 */
	private void setBigMoneyChange(List<BigFund> bigfundLists) {
		if (bigfundLists == null || bigfundLists.size() == 0) {
			hiddenLoading(true);
			return;
		}

		hiddenLoading(false);
		bigfundAdapter.setInfolist(bigfundLists);
		bigfundAdapter.setSelectTab(selectTab);
		bigfundAdapter.notifyDataSetChanged();
	}

	public void setTestDatas() {
		// 添加测试数据
		List<BigFund> list = new ArrayList<BigFund>();
		for (int i = 0; i < 9; i++) {
			BigFund entity = new BigFund("PN1010000" + i, "张三", "77788828" + i,
					"转入", "10000.00", "HU010101", "2015-10-10");
			list.add(entity);
		}
		bigfundAdapter.setInfolist(list);
		bigfundAdapter.setSelectTab(selectTab);
		bigfundAdapter.notifyDataSetChanged();
	}

	/* ********************************************************************************* */
	/* ********************************************************************************* */
	/* ********************************************************************************* */

	private static class HolderView {
		private TextView lvItemName;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextView lvItemInfo5;
		private View line1;
		private View line2;
		private View line3;
		private View line4;
	}

	public class BigFundAdapter extends BaseAdapter {

		private List<BigFund> infolist;
		private int selectTab = INDEX_ONE;

		@Override
		public int getCount() {
			if (infolist != null && infolist.size() > 0) {
				return infolist.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.workplacelvtop_bigfund, null);
				holder = new HolderView();
				holder.lvItemName = (TextView) convertView
						.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextView) convertView
						.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView
						.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView
						.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView
						.findViewById(R.id.lvitem_info5);
				holder.line1 = (View) convertView.findViewById(R.id.line1);
				holder.line2 = (View) convertView.findViewById(R.id.line2);
				holder.line3 = (View) convertView.findViewById(R.id.line3);
				holder.line4 = (View) convertView.findViewById(R.id.line4);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.white));
			}
			// 设置单条是否显示隐藏
			if (selectTab == INDEX_TOTAL) {
				holder.lvItemInfo2.setVisibility(View.GONE);
				holder.lvItemInfo5.setVisibility(View.GONE);
			} else {
				holder.lvItemInfo2.setVisibility(View.VISIBLE);
				holder.lvItemInfo5.setVisibility(View.VISIBLE);
			}
			// 将获取到的数据进行展示
			BigFund bigFund = infolist.get(position);
			if (bigFund != null) {
				String transType = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.transType, bigFund.getTRANS_TYPE());
				holder.lvItemName.setText(bigFund.getCST_NAME());
				holder.lvItemInfo2.setText(bigFund.getACC_NO());
				holder.lvItemInfo3.setText(transType);
				holder.lvItemInfo4.setText(Tool.setFormatValue(bigFund.getBAL()));
				holder.lvItemInfo5.setText(bigFund.getTRADE_DT());
			}

			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);
			holder.line4.setVisibility(View.GONE);
			return convertView;
		}

		public void setInfolist(List<BigFund> infolist) {
			this.infolist = infolist;
		}

		public void setSelectTab(int selectTab) {
			this.selectTab = selectTab;
		}
	}

	/* ********************************************************************************* */
	/* ********************************************************************************* */
	/* ********************************************************************************* */

	@Override
	public void onClick(View v) {
		offset = 1;
		switch (v.getId()) {
		// 客户单笔超过10万元
		case R.id.tv_itemcust51:
			selectTab = INDEX_ONE;
			setBgAndTextColor(tvSingleOne, INDEX_ONE);
			if (isForperson) {
				singleOneForperson.removeAll(singleOneForperson);
				//setBigMoneyChange(singleOneForperson);
				//if (singleOneForperson == null) {
					selectType = "7";
					sendRequestForInfo(INDEX_ONE);
				//}
			} else {
				singleOneForcommon.removeAll(singleOneForcommon);
				//setBigMoneyChange(singleOneForcommon);
				//if (singleOneForcommon == null) {
					selectType = "23";
					sendRequestForInfo(INDEX_ONE);
				//}
			}
			break;

		// 客户单日累计超过10万元
		case R.id.tv_itemcust52:
			selectTab = INDEX_TOTAL;
			setBgAndTextColor(tvSingleTotal, INDEX_TOTAL);
			if (isForperson) {
				singleTotalForperson.removeAll(singleTotalForperson);
				//setBigMoneyChange(singleTotalForperson);
				//if (singleTotalForperson == null) {
					selectType = "8";
					sendRequestForInfo(INDEX_TOTAL);
				//}
			} else {
				singleTotalForcommon.removeAll(singleTotalForcommon);
				//setBigMoneyChange(singleTotalForcommon);
				//if (singleTotalForcommon == null) {
					selectType = "24";
					sendRequestForInfo(INDEX_TOTAL);
				//}
			}
			break;
		default:
			break;
		}
	}

}
