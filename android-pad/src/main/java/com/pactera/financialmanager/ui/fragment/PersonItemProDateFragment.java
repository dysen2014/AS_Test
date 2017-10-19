package com.pactera.financialmanager.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.commontool.TextViewMarquee;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.ProCHDP;
import com.pactera.financialmanager.ui.model.ProDQCK;
import com.pactera.financialmanager.ui.model.ProDQDK;
import com.pactera.financialmanager.ui.model.ProDQLC;
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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 工作提醒中的 产品到期 下的界面
 * @author Administrator
 *
 */
public class PersonItemProDateFragment extends ParentFragment implements OnClickListener {

	// 加载布局
	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private LinearLayout llLvTopOut;
	// 选项卡(定期存款、理财、贷款、兑票)
	private TextView tvCK, tvLC, tvDK, tvDP;
//	private ListView lvPro;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	
	// 接口请求
	private HConnection HCon;
	private static final int INDEX_CK = 1;
	private static final int INDEX_LC = 2;
	private static final int INDEX_DK = 3;
	private static final int INDEX_DP = 4;
	
	// 未来十天内定期存款到期
	private List<ProDQCK> prodateCKForperson = new ArrayList<ProDQCK>();
	private List<ProDQCK> prodateCKForcommon = new ArrayList<ProDQCK>();
	// 未来十天内理财产品到期
	private List<ProDQLC> prodateLCForperson = new ArrayList<ProDQLC>();
	private List<ProDQLC> prodateLCForcommon = new ArrayList<ProDQLC>();
	// 未来十天内贷款产品到期
	private List<ProDQDK> prodateDKForperson = new ArrayList<ProDQDK>();
	private List<ProDQDK> prodateDKForcommon = new ArrayList<ProDQDK>();
	// 未来三十天承兑汇票到期
	private List<ProCHDP> prodateDPForcommon = new ArrayList<ProCHDP>();

	private PersonProductEndDateAdapter personPEDAdapter = new PersonProductEndDateAdapter();
	private PersonLiCaiAdapter personLCAdapter = new PersonLiCaiAdapter();
	private PersonDaiKuanAdapter personDKAdapter = new PersonDaiKuanAdapter();
	private PersonDuipiaoAdapter personDPAdapter = new PersonDuipiaoAdapter();

	private int selectIndex = INDEX_CK;
	private String selectType = "1";
	private int offset = 1;	
	private boolean isLoadmore = false;			// 是否上拉加载更多
	
	/** 是否为对个人提醒 (默认显示对个人) */
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
			// 1.未来十天内定期存款到期
			case INDEX_CK:
				List<ProDQCK> resultCK = (ArrayList<ProDQCK>)msg.obj;
				if(isForperson){
					prodateCKForperson.addAll(resultCK);
					setDefaultItemProFirst(prodateCKForperson);
				}else{
					prodateCKForcommon.addAll(resultCK);
					setDefaultItemProFirst(prodateCKForcommon);
				}
				break;
			
			// 2.未来十天内理财产品到期
			case INDEX_LC:
				List<ProDQLC> resultLC = (ArrayList<ProDQLC>)msg.obj;
				if(isForperson){
					prodateLCForperson.addAll(resultLC);
					setDefaultItemProSec(prodateLCForperson);
				}else{
					prodateLCForcommon.addAll(resultLC);
					setDefaultItemProSec(prodateLCForcommon);
				}
				break;
			
			// 3.未来十天内贷款产品到期
			case INDEX_DK:
				List<ProDQDK> resultDK = (ArrayList<ProDQDK>)msg.obj;
				if(isForperson){
					prodateDKForperson.addAll(resultDK);
					setDefaultItemProThir(prodateDKForperson);
				}else{
					prodateDKForcommon.addAll(resultDK);
					setDefaultItemProThir(prodateDKForcommon);
				}
				break;
				
			// 4.未来三十天承兑汇票到期
			case INDEX_DP:
				List<ProCHDP> resultDP = (ArrayList<ProCHDP>)msg.obj;
				prodateDPForcommon.addAll(resultDP);
				setDefaultItemProFourth(prodateDPForcommon);
				break;
				
			default:
				break;
			}
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_personitemprodate, null);
		setupViews(view);
		setListener();
		
		//要在这里设置第一个默认被选中的状态
		setTabTitle();
		setBgAndTextColor(tvCK);
		sendRequestForInfo(INDEX_CK);
		
		return view;
	}
	
	private void setupViews(View view) {
		// 未来十天内定期存款到期 这三个
		tvCK = (TextView) view.findViewById(R.id.tv_itempro1);
		tvLC = (TextView) view.findViewById(R.id.tv_itempro2);
		tvDK = (TextView) view.findViewById(R.id.tv_itempro3);
		tvDP = (TextView) view.findViewById(R.id.tv_itempro4);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
		
		// listview，届时，不同的条目对应不同的适配器
//		lvPro = (ListView) view.findViewById(R.id.lv_workplace);
		ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
		lvList.setAdapter(personPEDAdapter);
		
		//只能将顶部的标题栏通过代码动态的添加到界面上了
		llLvTopOut=(LinearLayout) view.findViewById(R.id.ll_lvtop);
		
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
		tvCK.setOnClickListener(this);
		tvLC.setOnClickListener(this);
		tvDK.setOnClickListener(this);
		tvDP.setOnClickListener(this);
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
	 * 设置是否为对个人状态-->默认显示第一个标签
	 * @param isForperson
	 */
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
		
		setTabTitle();
		setBgAndTextColor(tvCK);
		offset = 1;
		lvList.setAdapter(personPEDAdapter);
		if(isForperson){
			selectType = "1";
			// 对个人
			prodateCKForperson.removeAll(prodateCKForperson);
			sendRequestForInfo(INDEX_CK);
			tvDP.setVisibility(View.INVISIBLE);
		}else{
			selectType = "16";
			// 对公
			prodateCKForcommon.removeAll(prodateCKForcommon);
			sendRequestForInfo(INDEX_CK);
			tvDP.setVisibility(View.VISIBLE);
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
				// 未来十天内定期存款到期
				if(eventType.equals("1")
						|| eventType.equals("16")){
					tvCK.setText(eventName);
				}
				// 未来十天内理财产品到期
				if(eventType.equals("2")
						|| eventType.equals("17")){
					tvLC.setText(eventName);
				}
				// 未来三十天内贷款产品到期
				if(eventType.equals("3")
						|| eventType.equals("18")){
					tvDK.setText(eventName);
				}
				// 未来三十天内承兑汇票到期
				if(eventType.equals("19")){
					tvDP.setText(eventName);
				}
			}
		}
	}
	

	/**
	 * 请求方法
	 * @param index
	 * @param spareOne
	 * @param spareTwo
	 */
	public void sendRequestForInfo(int index){
		String spareTwo  = "01";
		if(!isForperson){
			spareTwo  = "02";
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
			
		case INDEX_CK:
		case INDEX_LC:
		case INDEX_DK:
		case INDEX_DP:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			Log.i("RETURNCUS", "res:-->"+res);
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
			List<ProDQCK> prodateCKForperson = null;
			List<ProDQLC> prodateLCForperson = null;
			List<ProDQDK> prodateDKForperson = null;
			Message msg = new Message();
			msg.arg1 = connectionIndex;
			
			try {
				String group = tmpJsonObject.getString("group");
				switch (connectionIndex) {
				// 定期存款
				case INDEX_CK:
					prodateCKForperson = JSON.parseArray(group, ProDQCK.class);
					msg.obj = prodateCKForperson;
					break;
					
				// 理财产品	
				case INDEX_LC:
					prodateLCForperson = JSON.parseArray(group, ProDQLC.class);
					msg.obj = prodateLCForperson;
					break;
					
				// 贷款产品
				case INDEX_DK:
					prodateDKForperson = JSON.parseArray(group, ProDQDK.class);
					msg.obj = prodateDKForperson;
					break;
	
				// 承兑汇票
				case INDEX_DP:
					prodateDPForcommon = JSON.parseArray(group, ProCHDP.class);
					msg.obj = prodateDPForcommon;
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
	
	
	
	

	/* ********************************************************************************* */
	/* ********************************************************************************* */
	/* ********************************************************************************* */
	

	/**
	 * 1.未来十天内定期存款到期(个人客户) 
	 * @param prodateCK 
	 */
	private void setDefaultItemProFirst(List<ProDQCK> prodateCK) {
		if(getActivity() == null){
			return;
		}
		
		//listview顶部的标题栏
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.workplacelvtop, null);
		LinearLayout llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_workplaceitem);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llLvTopOut.removeAllViews();
		llLvTopOut.addView(llWorkPlaceLvTopItem,params);
		
		// 设置数据
		if(prodateCK == null || prodateCK.size() == 0){
			hiddenLoading(true);
		}else{
			hiddenLoading(false);
			//PersonProductEndDateAdapter personProAdapter = new PersonProductEndDateAdapter(prodateCK);
			//lvPro.setAdapter(personProAdapter);
			personPEDAdapter.setInfolist(prodateCK);
			personPEDAdapter.notifyDataSetChanged();
		}
	}

	
	/**
	 * 2.未来十天内理财产品到期(个人客户)
	 * @param prodateLC 
	 */
	private void setDefaultItemProSec(List<ProDQLC> prodateLC) {
		//listview顶部的标题栏
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.workplacelvtop2, null);
		LinearLayout llWorkPlaceLvTopItem= (LinearLayout) view.findViewById(R.id.ll_workplaceitem2);
		LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llLvTopOut.removeAllViews();
		llLvTopOut.addView(llWorkPlaceLvTopItem,params);

		if(prodateLC == null || prodateLC.size() == 0 ){
			hiddenLoading(true);
		}else{
			hiddenLoading(false);
			//PersonLiCaiAdapter personProAdapter = new PersonLiCaiAdapter(prodateLC);
			//lvPro.setAdapter(personProAdapter);
			personLCAdapter.setInfolist(prodateLC);
			personLCAdapter.notifyDataSetChanged();
		}
	}
	

	/**
	 * 3.未来三十天内贷款产品到期(个人客户)
	 * @param prodateDK 
	 */
	private void setDefaultItemProThir(List<ProDQDK> prodateDK) {
		//listview顶部的标题栏
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.workplacelvtop3, null);
		LinearLayout llWorkPlaceLvTopItem= (LinearLayout) view.findViewById(R.id.ll_workplaceitem3);
		LinearLayout.LayoutParams  params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llLvTopOut.removeAllViews();
		llLvTopOut.addView(llWorkPlaceLvTopItem,params);

		if(prodateDK == null || prodateDK.size() == 0){
			hiddenLoading(true);
		}else{
			hiddenLoading(false);
			//PersonDaiKuanAdapter personProAdapter = new PersonDaiKuanAdapter(prodateDK);
			//lvPro.setAdapter(personProAdapter);
			personDKAdapter.setInfolist(prodateDK);
			personDKAdapter.notifyDataSetChanged();
		}
	}


	/**
	 * 4.未来三十天内承兑汇票到期(对公客户)
	 * @param prodateDP 
	 */
	private void setDefaultItemProFourth(List<ProCHDP> prodateDP){
		//listview顶部的标题栏
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.workplacelvtop4f, null);
		LinearLayout llWorkPlaceLvTopItem= (LinearLayout) view.findViewById(R.id.ll_workplaceitem4f);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		llLvTopOut.removeAllViews();
		llLvTopOut.addView(llWorkPlaceLvTopItem, params);
		
		if(prodateDP == null || prodateDP.size() == 0){
			hiddenLoading(true);
		}else{
			hiddenLoading(false);
			//PersonDuipiaoAdapter personProAdapter = new PersonDuipiaoAdapter(prodateDP);
			//lvPro.setAdapter(personProAdapter);
			personDPAdapter.setInfolist(prodateDP);
			personDPAdapter.notifyDataSetChanged();
		}
	}
	

	/* ********************************************************************************* */
	/* ********************************************************************************* */
	/* ********************************************************************************* */
	
	
	private static class HolderView {
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextView lvItemInfo5;
		private TextView lvItemInfo6;
		private TextView lvItemInfo7;
		private TextView lvItemInfo8;
		private TextView lvItemInfo9;
	}
	
	
	/**
	 * 1 未来十天内定期存款到期(个人客户)对应的adapter
	 * @author Administrator
	 */
	public class PersonProductEndDateAdapter extends BaseAdapter {
		private List<ProDQCK>  infolist;
		
//		public PersonProductEndDateAdapter(List<ProDQCK> infolist) {
//			super();
//			this.infolist = infolist;
//		}

		public void setInfolist(List<ProDQCK> infolist) {
			this.infolist = infolist;
		}

		@Override
		public int getCount() {
			if(infolist != null){
				return infolist.size();
			}else{
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			if(infolist != null && infolist.size()>0){
				return infolist.size();
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
				convertView = View.inflate(getActivity(), R.layout.workplacelistviewitem, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
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
			ProDQCK dqck = infolist.get(position);
			if(dqck != null){
				holder.lvItemInfo1.setText(dqck.getCST_NAME());
				holder.lvItemInfo2.setText(dqck.getACC_DEP_ACC());
				holder.lvItemInfo3.setText(dqck.getACC_DEP_ACC_NAME());
				holder.lvItemInfo4.setText(Tool.setFormatValue(dqck.getEVENT_BAL()));
				holder.lvItemInfo5.setText(dqck.getEXPIRE_DT());
			}
			return convertView;
		}
	}

	
	/**
	 * 2 未来十天内理财产品到期(个人客户)对应的adapter
	 * @author Administrator
	 *
	 */
	public class PersonLiCaiAdapter extends BaseAdapter {
		 private List<ProDQLC>  infolist;
		
//		 public PersonLiCaiAdapter(List<ProDQLC> infolist) {
//			 this.infolist=infolist;
//		 }

		public void setInfolist(List<ProDQLC> infolist) {
			this.infolist = infolist;
		}

		@Override
		public int getCount() {
			if(infolist!=null){
				return infolist.size();
			}else{
				return 0;
			}
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
				convertView = View.inflate(getActivity(), R.layout.workplacelistviewitem, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
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
			ProDQLC dqlc = infolist.get(position);
			if(dqlc != null){
				holder.lvItemInfo1.setText(dqlc.getCST_NAME());
				holder.lvItemInfo2.setText(dqlc.getPROD_NAME());
				holder.lvItemInfo3.setText(dqlc.getEXPECTED_YIELD());
				holder.lvItemInfo4.setText(Tool.setFormatValue(dqlc.getEVENT_BAL()));
				holder.lvItemInfo5.setText(dqlc.getEXPIRE_DT());
			}
			return convertView;
		}
	}
	
	/**
	 * 3 未来三十天内贷款产品到期(个人客户)对应的adapter
	 * @author Administrator
	 *
	 */
	public class PersonDaiKuanAdapter extends BaseAdapter {
		private List<ProDQDK> infolist;
		
//		public PersonDaiKuanAdapter(List<ProDQDK> infolist) {
//			this.infolist=infolist;
//		}
		
		@Override
		public int getCount() {
			if(infolist != null){
				return infolist.size();
			}
			return 0;
		}
		
		public void setInfolist(List<ProDQDK> infolist) {
			this.infolist = infolist;
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
				convertView = View.inflate(getActivity(), R.layout.workplacelistviewitemdaikuan, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextViewMarquee) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
				holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.lvitem_info6);
				holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.lvitem_info7);
				holder.lvItemInfo8 = (TextView) convertView.findViewById(R.id.lvitem_info8);
				holder.lvItemInfo9 = (TextViewMarquee) convertView.findViewById(R.id.lvitem_info9);
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
			ProDQDK dqdk = infolist.get(position);
			if(dqdk != null){
				holder.lvItemInfo1.setText(dqdk.getCST_NAME());
				holder.lvItemInfo2.setText(dqdk.getLOAN_ACC());
				holder.lvItemInfo3.setText(dqdk.getPROD_NAME());
				holder.lvItemInfo4.setText(Tool.setFormatValue(dqdk.getLOAN_RATE())+"%");
				holder.lvItemInfo5.setText(Tool.setFormatValue(dqdk.getEVENT_BAL()));
				holder.lvItemInfo6.setText(dqdk.getISSUE_DATE());
				holder.lvItemInfo7.setText(dqdk.getEXPIRE_DT());
				holder.lvItemInfo8.setText(dqdk.getOFFICER());
				holder.lvItemInfo9.setText(dqdk.getBRNAME());
			}
			return convertView;
		}
	}

	/**
	 * 4 未来三十天内承诺兑换票(对公客户)对应的adapter
	 * @author Administrator
	 *
	 */
	public class PersonDuipiaoAdapter extends BaseAdapter {
		private List<ProCHDP>  infolist;
		
//		public PersonDuipiaoAdapter(List<ProCHDP> infolist) {
//			this.infolist=infolist;
//		}
		
		@Override
		public int getCount() {
			if(infolist!=null&&infolist.size()>0){
				return infolist.size();
			}else{
				return 0;
			}
		}
		
		public void setInfolist(List<ProCHDP> infolist) {
			this.infolist = infolist;
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
				convertView = View.inflate(getActivity(), R.layout.workplacelistviewitemduipiao, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
				holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.lvitem_info6);
				holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.lvitem_info7);
				holder.lvItemInfo8 = (TextView) convertView.findViewById(R.id.lvitem_info8);
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
			ProCHDP chdp = infolist.get(position);
			if(chdp != null){
				holder.lvItemInfo1.setText(chdp.getCST_NAME());
				holder.lvItemInfo2.setText(chdp.getPAGE_NUMBER());
				holder.lvItemInfo3.setText(chdp.getDRAWER_DT());
				holder.lvItemInfo4.setText(chdp.getEXPIRE_DT());
				holder.lvItemInfo5.setText(Tool.setFormatValue(chdp.getEVENT_BAL()));
				holder.lvItemInfo6.setText(Tool.setFormatValue(chdp.getDEPOSIT_AMOUNT()));
				holder.lvItemInfo7.setText(chdp.getDEPOSIT_SCALE());
				holder.lvItemInfo8.setText(chdp.getPAYEE_NAME());
			}
			return convertView;
		}
	}
	
	/* ********************************************************************************* */
	/* ********************************************************************************* */
	/* ********************************************************************************* */
	

	/**
	 * 设置背景颜色
	 * @param tv
	 */
	private void setBgAndTextColor(TextView tv){
		tvCK.setEnabled(true);
		tvLC.setEnabled(true);
		tvDK.setEnabled(true);
		tvDP.setEnabled(true);
		tvCK.setTextColor(getResources().getColor(R.color.separatelightredline));
		tvLC.setTextColor(getResources().getColor(R.color.separatelightredline));
		tvDK.setTextColor(getResources().getColor(R.color.separatelightredline));
		tvDP.setTextColor(getResources().getColor(R.color.separatelightredline));
		// 选中项
		tv.setEnabled(false);
		tv.setTextColor(getResources().getColor(R.color.white));
	}


	@Override
	public void onClick(View v) {
		offset = 1;
		
		switch (v.getId()) {
		case R.id.tv_itempro1:
			selectIndex = INDEX_CK;
			lvList.setAdapter(personPEDAdapter);
			setBgAndTextColor(tvCK);
			if(isForperson){
				prodateCKForperson.removeAll(prodateCKForperson);
				selectType = "1";
				sendRequestForInfo(selectIndex);
			}else{
				prodateCKForcommon.removeAll(prodateCKForcommon);
				selectType = "16";
				sendRequestForInfo(selectIndex);
			}
			break;
		case R.id.tv_itempro2:
			selectIndex = INDEX_LC;
			lvList.setAdapter(personLCAdapter);
			setBgAndTextColor(tvLC);
			if(isForperson){
				prodateLCForperson.removeAll(prodateLCForperson);
				selectType = "2";
				sendRequestForInfo(selectIndex);
			}else{
				prodateLCForcommon.removeAll(prodateLCForcommon);
				selectType = "17";
				sendRequestForInfo(selectIndex);
			}
			break;
		case R.id.tv_itempro3:
			selectIndex = INDEX_DK;
			lvList.setAdapter(personDKAdapter);
			setBgAndTextColor(tvDK);
			if(isForperson){
				prodateDKForperson.removeAll(prodateDKForperson);
				selectType = "3";
				sendRequestForInfo(selectIndex);
			}else{
				prodateDKForcommon.removeAll(prodateDKForcommon);
				selectType = "18";
				sendRequestForInfo(selectIndex);
			}
			break;
		case R.id.tv_itempro4:
			selectIndex = INDEX_DP;
			lvList.setAdapter(personDPAdapter);
			setBgAndTextColor(tvDP);
			prodateDPForcommon.removeAll(prodateDPForcommon);
			selectType = "19";
			sendRequestForInfo(selectIndex);
			break;
		default:
			break;
		}
	}
	
}
