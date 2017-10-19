package com.pactera.financialmanager.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.commontool.TextViewMarquee;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.Usemoney;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是客户账户余额不足  对应的子fragment
 */
public class FragmentBigUseMoneyLose extends ParentFragment {
	
	private LinearLayout layLoading;
	private LinearLayout layNodata;
	//private ListView lvProInfo;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private TextView tvTitle;
	private UsemoneyAdapter usemoneyAdapter;
	
	private boolean isForperson = true;

	// 接口请求
	private HConnection HCon;
	private static final int INDEX_USEMONEY = 1;
	// 账户余额
	private List<Usemoney> usemoneyForperson = new ArrayList<Usemoney>();
	private List<Usemoney> usemoneyForcommon = new ArrayList<Usemoney>();

	private String selectType = "9";
	private int offset = 1;
	private boolean isLoadmore = false;			// 是否上拉加载更多
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(isLoadmore){
				// 隐藏地步
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
			
			switch (msg.arg1) {
			// 1.账户余额不足
			case INDEX_USEMONEY:
				List<Usemoney> usemoneyResults = (ArrayList<Usemoney>) msg.obj;
				if(isForperson){
					usemoneyForperson.addAll(usemoneyResults);
					setUsermoneyChange(usemoneyForperson);
				}else{
					usemoneyForcommon.addAll(usemoneyResults);
					setUsermoneyChange(usemoneyForcommon);
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
		View view=inflater.inflate(R.layout.fragment_personitembigusemoneylose, null);
		setupView(view);
		
		setTabTitle();
		sendRequestForInfo();
		return view;
	}

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		//lvProInfo = (ListView) view.findViewById(R.id.lv_workplaceicon62);
		ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
		
		usemoneyAdapter = new UsemoneyAdapter();
		//lvProInfo.setAdapter(usemoneyAdapter);
		lvList.setAdapter(usemoneyAdapter);
		lvList.setPullstatus(false, true);
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
				
				sendRequestForInfo();
			}
		});
	}
	

	/**
	 * 设置是否为对个人状态
	 * @param isForperson
	 */
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
		offset = 1;
		if (isForperson) {
			usemoneyForperson.removeAll(usemoneyForperson);
			selectType = "9";
			sendRequestForInfo();
		} else {
			usemoneyForcommon.removeAll(usemoneyForcommon);
			selectType = "25";
			sendRequestForInfo();
		}
	}
	
	/**
	 * 设置二级标签标题
	 */
	private void setTabTitle(){
		// 消息提醒头集合（改变二级tab标题）
		List<CustEvent> events = WorkWarnFragment.tempEventsForPerson;
		if(!isForperson){
			events = WorkWarnFragment.tempEventsForCommon;
		}
		
		if(events != null){
			// 迭代获取标题名称
			for(CustEvent event : events){
				String eventType = event.getEvent_type(); // 类型
				String eventName = event.getEvent_type_name(); // 名称
				// 余额不足
				if(eventType.equals("9")
						|| eventType.equals("25")){
					tvTitle.setText(eventName);
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

	/**
	 * 请求方式
	 * @param spareOne
	 * @param spareTwo
	 */
	private void sendRequestForInfo(){
		// 对公对私
		String spareTwo = "01";
		if(!isForperson){
			spareTwo = "02";
		}
		
		if (Tool.haveNet(getActivity())) {
			loading();
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.PRODATA_Get;
			String info = "&size=20"+"&offset="+offset
					+ "&spareOne=" + selectType
					+ "&spareTwo=" + spareTwo
					+ "&jsonData={staid:\""+staid+"\"}";
			
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_USEMONEY, false); 
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
			
		case INDEX_USEMONEY:
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
			List<Usemoney> overList = null;
			String group = "";
			try {
				switch (connectionIndex) {
				case INDEX_USEMONEY:
					group = tmpJsonObject.getString("group");
					overList = JSON.parseArray(group, Usemoney.class);
					msg.obj = overList;
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
			Toast.makeText(getActivity(), "请求失败! 错误代码:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 设置值
	 * @param usermoneyLists
	 */
	private void setUsermoneyChange(List<Usemoney> usermoneyLists) {
		if(usermoneyLists == null || usermoneyLists.size() == 0){
			hiddenLoading(true);
			return;
		}
		
		hiddenLoading(false);
		
		usemoneyAdapter.setInfolist(usermoneyLists);
		usemoneyAdapter.notifyDataSetChanged();
	}

//	public void setTestDatas(){
//		List<Usemoney> infolist = new ArrayList<Usemoney>();
//		for (int i = 0; i < 9; i++) {
//			Usemoney entity = new Usemoney("PN10210010"+i, "张三", "100,000", "543,200", "7776367882374", "10,000", "10,000", "10,000", "HU1000923");
//			infolist.add(entity);
//		}
//		usemoneyAdapter.setInfolist(infolist);
//		usemoneyAdapter.notifyDataSetChanged();
//	}
//
	private static class HolderView {
		private TextView lvItemName;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextViewMarquee lvItemInfo5;
		private TextViewMarquee lvItemInfo6;
		private TextViewMarquee lvItemInfo7;
		private TextView lvItemInfo8;
		private View line1;
		private View line2;
		private View line3;
		private View line4;
		private View line5;
		private View line6;
		private View line7;
	}
	
	/**
	 * 账户余额适配器
	 * @author Administrator
	 */
	private class UsemoneyAdapter extends BaseAdapter {
		
		private List<Usemoney>  infolist;
		
		@Override
		public int getCount() {
			if(infolist != null){
				return infolist.size();
			}
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			if(infolist != null && infolist.size()>0){
				return infolist.get(position);
			}
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
				convertView = View.inflate(getActivity(), R.layout.workplacelvtop_usemoney, null);
				holder = new HolderView();
				holder.lvItemName = (TextView) convertView.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextViewMarquee) convertView.findViewById(R.id.lvitem_info5);
				holder.lvItemInfo6 = (TextViewMarquee) convertView.findViewById(R.id.lvitem_info6);
				holder.lvItemInfo7 = (TextViewMarquee) convertView.findViewById(R.id.lvitem_info7);
				holder.lvItemInfo8 = (TextView) convertView.findViewById(R.id.lvitem_info8);
				holder.line1 = (View) convertView.findViewById(R.id.line1);
				holder.line2 = (View) convertView.findViewById(R.id.line2);
				holder.line3 = (View) convertView.findViewById(R.id.line3);
				holder.line4 = (View) convertView.findViewById(R.id.line4);
				holder.line5 = (View) convertView.findViewById(R.id.line5);
				holder.line6 = (View) convertView.findViewById(R.id.line6);
				holder.line7 = (View) convertView.findViewById(R.id.line7);

				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if(position%2!=0){
				convertView.setBackgroundColor(getResources().getColor(R.color.lvtopbg));
			}else{
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}
			// 将获取到的数据进行展示
			Usemoney overdue = infolist.get(position);
			if(overdue != null){
				holder.lvItemName.setText(overdue.getCST_NAME());
				holder.lvItemInfo2.setText(overdue.getLOAN_ACC());
				holder.lvItemInfo3.setText(Tool.setFormatValue(overdue.getLOAN_BAL()));
				holder.lvItemInfo4.setText(overdue.getREPAYMENT_ACC());
				holder.lvItemInfo5.setText(Tool.setFormatValue(overdue.getAMOUNT_BAL()));
				holder.lvItemInfo6.setText(Tool.setFormatValue(overdue.getSHOULD_BAL()));
				holder.lvItemInfo7.setText(Tool.setFormatValue(overdue.getSHOULD_ACCRUAL()));
				holder.lvItemInfo8.setText(overdue.getBACKDATE());
			}
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);
			holder.line4.setVisibility(View.GONE);
			holder.line5.setVisibility(View.GONE);
			holder.line6.setVisibility(View.GONE);
			holder.line7.setVisibility(View.GONE);

			return convertView;
		}

		public void setInfolist(List<Usemoney> infolist) {
			this.infolist = infolist;
		}
	}
	
	
}
