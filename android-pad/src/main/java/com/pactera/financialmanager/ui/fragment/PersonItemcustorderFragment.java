package com.pactera.financialmanager.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.Overdue;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 这是 客户违约 对应的子fragment
 */
public class PersonItemcustorderFragment extends ParentFragment implements OnClickListener {

	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private TextView tvOverdueDK, tvOverdueLX, tvOverdueXYK;
	//private ListView lvPro;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private LinearLayout llLvTopOut;
	private OverdueAdapter overDueAdapter = null;
	
	// 接口请求
	private HConnection HCon;
	private static final int INDEX_DK = 1;
	private static final int INDEX_XYK = 2;
	private static final int INDEX_LX = 3;
	
	// 贷款逾期
	private List<Overdue> overdueDKForperson = new ArrayList<Overdue>(); // 对个人
	private List<Overdue> overdueDKForcommon = new ArrayList<Overdue>(); // 对公
	// 信用卡逾期
	private List<Overdue> overdueXYKForperson = new ArrayList<Overdue>();
	private List<Overdue> overdueXYKForcommon = new ArrayList<Overdue>();
	// 利息逾期
	private List<Overdue> overdueLXForperson = new ArrayList<Overdue>();
	private List<Overdue> overdueLXForcommon = new ArrayList<Overdue>();
	
	private int selectIndex = INDEX_DK;
	private String selectType = "4";
	private int offset = 1;	
	private boolean isLoadmore = false;			// 是否上拉加载更多
	private boolean isCreateEnd = false;
	
	// violate 违约
	private boolean isForperson = true;

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
			// 1.贷款逾期
			case INDEX_DK:
				List<Overdue> resultDK = (ArrayList<Overdue>) msg.obj;
				if(isForperson){
					overdueDKForperson.addAll(resultDK);
					setOverdueChanges(overdueDKForperson);
				}else{
					overdueDKForcommon.addAll(resultDK);
					setOverdueChanges(overdueDKForcommon);
				}
				break;
			
			// 2.信用卡逾期
			case INDEX_XYK:
				List<Overdue> resultXYK = (ArrayList<Overdue>) msg.obj;
				if(isForperson){
					overdueXYKForperson.addAll(resultXYK);
					setOverdueChanges(overdueXYKForperson);
				}else{
					overdueXYKForcommon.addAll(resultXYK);
					setOverdueChanges(overdueXYKForcommon);
				}
				break;
			
			// 3.利息逾期
			case INDEX_LX:
				List<Overdue> resultLX = (ArrayList<Overdue>) msg.obj;
				if(isForperson){
					overdueLXForperson.addAll(resultLX);
					setOverdueChanges(overdueLXForperson);
				}else{
					overdueLXForcommon.addAll(resultLX);
					setOverdueChanges(overdueLXForcommon);
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
		View view = inflater.inflate(R.layout.fragment_personitemcustorder, null);
		setupViews(view);
		setListener();
		
		setTabTitle();
		setBgAndTextColor(tvOverdueDK);
		sendRequestForInfo(selectIndex);

		isCreateEnd = false;
		return view;
	}


	private void setupViews(View view) {
		// 顶部的 贷款逾期 这三个
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		tvOverdueDK = (TextView) view.findViewById(R.id.tv_overdueDK);
		tvOverdueLX = (TextView) view.findViewById(R.id.tv_overdueLX);
		tvOverdueXYK = (TextView) view.findViewById(R.id.tv_overdueXYK);
		llLvTopOut=(LinearLayout) view.findViewById(R.id.ll_lvtopicon2);
		//lvPro = (ListView) view.findViewById(R.id.lv_workplaceicon2);
		ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
		
		overDueAdapter = new OverdueAdapter();
		//lvPro.setAdapter(overDueAdapter);
		lvList.setAdapter(overDueAdapter);
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
				
				sendRequestForInfo(selectIndex);
			}
		});
	}


	private void setListener() {
		// 三个文字的点击事件，在这里要做的是切换背景，和为listview换不同的适配器
		tvOverdueDK.setOnClickListener(this);
		tvOverdueXYK.setOnClickListener(this);
		tvOverdueLX.setOnClickListener(this);
	}


	/**
	 * 显示进度条
	 */
	private void loading(){
		layLoading.setVisibility(View.VISIBLE);
		//lvPro.setVisibility(View.GONE);
		if(offset == 1){
			ptrl.setVisibility(View.GONE);
		}
		layNodata.setVisibility(View.GONE);
	}
	
	/**
	 * 隐藏进度条
	 * @param noDatas
	 */
	private void hiddenLoading(boolean noDatas){
		layLoading.setVisibility(View.GONE);
		if(noDatas){
			layNodata.setVisibility(View.VISIBLE);
			//lvPro.setVisibility(View.GONE);
			ptrl.setVisibility(View.GONE);
		}else{
			layNodata.setVisibility(View.GONE);
			//lvPro.setVisibility(View.VISIBLE);
			ptrl.setVisibility(View.VISIBLE);
		}
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
		if(!isCreateEnd){
			hiddentTitle();
		}
		setTabTitle();
		setBgAndTextColor(tvOverdueDK);
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
				// 贷款本金
				if(eventType.equals("4")
						|| eventType.equals("20")){
					tvOverdueDK.setText(eventName);
				}
				// 信用卡逾期
				if(eventType.equals("5")
						|| eventType.equals("21")){
					tvOverdueXYK.setText(eventName);
				}
				// 贷款利息
				if(eventType.equals("6")
						|| eventType.equals("22")){
					tvOverdueLX.setText(eventName);
				}
			}
		}
	}

	/**
	 * 隐藏头标题
	 */
	private void hiddentTitle() {
		if (isForperson) {
			overdueDKForperson.removeAll(overdueDKForperson);
			selectType = "4";
			sendRequestForInfo(selectIndex);
			tvOverdueXYK.setVisibility(View.VISIBLE);
		} else {
			// 当对公时候选择信用卡，切换到
			if (selectIndex == INDEX_XYK) {
				selectIndex = INDEX_DK;
				setBgAndTextColor(tvOverdueDK);
			}
			overdueDKForcommon.removeAll(overdueDKForcommon);
			selectType = "20";
			sendRequestForInfo(selectIndex);
			tvOverdueXYK.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 设置头背景
	 * @param tv
	 */
	private void setBgAndTextColor(TextView tv){
		tvOverdueDK.setEnabled(true);
		tvOverdueLX.setEnabled(true);
		tvOverdueXYK.setEnabled(true);
		tvOverdueDK.setTextColor(getResources().getColor(R.color.separatelightredline));
		tvOverdueLX.setTextColor(getResources().getColor(R.color.separatelightredline));
		tvOverdueXYK.setTextColor(getResources().getColor(R.color.separatelightredline));
		tv.setEnabled(false);
		tv.setTextColor(getResources().getColor(R.color.white));
		
		//listview顶部的标题栏
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.workplacelvtop_violate1, null);
		LinearLayout llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_workplaceitem);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llLvTopOut.removeAllViews();
		llLvTopOut.addView(llWorkPlaceLvTopItem, params);
		// 信用卡选项 隐藏产品名称
		TextView tvLoanAcc = (TextView) view.findViewById(R.id.lvitem_info2);
		TextView tvProdName = (TextView) view.findViewById(R.id.lvitem_info3);
		TextView tvProdMoney = (TextView) view.findViewById(R.id.lvitem_info4);
		TextView tvCompany = (TextView) view.findViewById(R.id.lvitem_info6);
		View line3 = (View) view.findViewById(R.id.line3);
		View line4 = (View) view.findViewById(R.id.line4);
		
		if(selectIndex == INDEX_DK){
			tvLoanAcc.setText("贷款账户");
			tvProdName.setVisibility(View.VISIBLE);
			tvProdMoney.setText("逾期本金金额");
			line3.setVisibility(View.VISIBLE);
			line4.setVisibility(View.VISIBLE);
			// 判断是否为对公对私
			if(isForperson){
				tvCompany.setVisibility(View.GONE);
			}else{
				tvCompany.setVisibility(View.VISIBLE);
			}
		}else if(selectIndex == INDEX_LX){
			tvLoanAcc.setText("贷款账户");
			tvProdName.setVisibility(View.VISIBLE);
			tvProdMoney.setText("逾期利息金额");
			line3.setVisibility(View.VISIBLE);
			line4.setVisibility(View.VISIBLE);
			// 判断是否为对公对私
			if(isForperson){
				tvCompany.setVisibility(View.GONE);
			}else{
				tvCompany.setVisibility(View.VISIBLE);
			}
		}else if(selectIndex == INDEX_XYK){
			tvLoanAcc.setText("信用卡号");
			tvProdName.setVisibility(View.GONE);
			tvProdMoney.setText("逾期金额");
			line3.setVisibility(View.GONE);
			tvCompany.setVisibility(View.GONE);
			line4.setVisibility(View.VISIBLE);
		}
	}
	

	
	/**
	 * 接口请求
	 * @param index
	 * @param spareOne
	 */
	private void sendRequestForInfo(int index){
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
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);	
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
			
		case INDEX_DK:
		case INDEX_XYK:
		case INDEX_LX:
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
			List<Overdue> overList = null;
			String group = "";
			try {
				switch (connectionIndex) {
				case INDEX_DK:
				case INDEX_XYK:
				case INDEX_LX:
					group = tmpJsonObject.getString("group");
					overList = JSON.parseArray(group, Overdue.class);
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
	 * 设置单个选项卡内容
	 * @param overdueList
	 */
	private void setOverdueChanges(List<Overdue> overdueList) {
		// 如果集合为空，则返回
		if(overdueList.size() == 0){
			hiddenLoading(true);
		}else{
			hiddenLoading(false);
		}
		
		overDueAdapter.setInfolist(overdueList);
		overDueAdapter.setTypeIndex(selectIndex);
		overDueAdapter.notifyDataSetChanged();
	}
	
	public void setTestData(){
		List<Overdue> alist = new ArrayList<Overdue>();
		for (int i = 0; i < 9; i++) {
			Overdue entity = new Overdue("PN1000109"+i, "张三","888377718333","定期存款","1000.00","2016-5-7","1000920","6666667888372","***机构");
			alist.add(entity);
		}
		overDueAdapter.setInfolist(alist);
		overDueAdapter.setTypeIndex(selectIndex);
		overDueAdapter.notifyDataSetChanged();
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
		private TextView lvItemInfo6;
		private View line1;
		private View line2;
		private View line3;
		private View line4;
		private View line5;
	}
	
	public class OverdueAdapter extends BaseAdapter {
		
		private int typeIndex;
		private List<Overdue>  infolist;
		
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
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.workplacelvtop_violate1, null);
				holder = new HolderView();
				holder.lvItemName = (TextView) convertView.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
				holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.lvitem_info6);
				holder.line1 = (View) convertView.findViewById(R.id.line1);
				holder.line2 = (View) convertView.findViewById(R.id.line2);
				holder.line3 = (View) convertView.findViewById(R.id.line3);
				holder.line4 = (View) convertView.findViewById(R.id.line4);
				holder.line5 = (View) convertView.findViewById(R.id.line5);
				
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
			Overdue overdue = infolist.get(position);
			if(overdue != null){
				holder.lvItemName.setText(overdue.getCST_NAME());
				holder.lvItemInfo2.setText(overdue.getLOAN_ACC());
				holder.lvItemInfo3.setText(overdue.getPROD_NAME());
				holder.lvItemInfo4.setText(Tool.setFormatValue(overdue.getOVERDUE_AMOUNT()));
				holder.lvItemInfo5.setText(overdue.getOVERDUE_DT());
				holder.lvItemInfo6.setText(overdue.getBRNAME());
				// 信用卡选项 隐藏产品名称
				if(typeIndex == INDEX_XYK){
					holder.lvItemInfo3.setVisibility(View.GONE);
					holder.lvItemInfo2.setText(overdue.getCREDIT_CARD()); //显示信用卡帐号
					// 隐藏机构
					holder.lvItemInfo6.setVisibility(View.GONE);
				}else{
					holder.lvItemInfo3.setVisibility(View.VISIBLE);
					holder.lvItemInfo2.setText(overdue.getLOAN_ACC());  //显示贷款账户
					// 判断是否为对公对私
					if(isForperson){
						holder.lvItemInfo6.setVisibility(View.GONE);
					}else{
						holder.lvItemInfo6.setVisibility(View.VISIBLE);
					}
				}
			}
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);
			holder.line4.setVisibility(View.GONE);
			holder.line5.setVisibility(View.GONE);
			
			return convertView;
		}

		public void setInfolist(List<Overdue> infolist) {
			this.infolist = infolist;
		}
		
		public void setTypeIndex(int typeIndex){
			this.typeIndex = typeIndex;
		}
	}
	
	@Override
	public void onClick(View v) {
		offset = 1;
		switch (v.getId()) {
		// 贷款逾期
		case R.id.tv_overdueDK:
			selectIndex = INDEX_DK;
			setBgAndTextColor(tvOverdueDK);
			if (isForperson) {
				overdueDKForperson.removeAll(overdueDKForperson);
				selectType = "4";
				sendRequestForInfo(selectIndex);
			} else {
				overdueDKForcommon.removeAll(overdueDKForcommon);
				selectType = "20";
				sendRequestForInfo(selectIndex);
			}
			break;

		// 信用卡逾期
		case R.id.tv_overdueXYK:
			selectIndex = INDEX_XYK;
			setBgAndTextColor(tvOverdueXYK);
			if (isForperson) {
				overdueXYKForperson.removeAll(overdueXYKForperson);
				selectType = "5";
				sendRequestForInfo(selectIndex);
			} else {
				overdueXYKForcommon.removeAll(overdueXYKForcommon);
				selectType = "21";
				sendRequestForInfo(selectIndex);
			}
			break;

		// 利息逾期
		case R.id.tv_overdueLX:
			selectIndex = INDEX_LX;
			setBgAndTextColor(tvOverdueLX);
			if (isForperson) {
				overdueLXForperson.removeAll(overdueLXForperson);
				selectType = "6";
				sendRequestForInfo(selectIndex);
			} else {
				overdueLXForcommon.removeAll(overdueLXForcommon);
				selectType = "22";
				sendRequestForInfo(selectIndex);
			}
			break;
		default:
			break;
		}
	}
	
}
