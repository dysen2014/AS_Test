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
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.model.OrderCust;
import com.pactera.financialmanager.ui.model.OrderOrg;
import com.pactera.financialmanager.ui.model.OrderPM;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的业绩-对个人
 * @author dg
 */
public class FragmentOrderLists extends ParentFragment implements OnClickListener {

	private RadioButton rbtnForperson, rbtnForcommon;
	private RadioButton rbtnTab01, rbtnTab02, rbtnTab03;
	private LinearLayout layOrgTypes;
	private RadioButton rbtnOrg1, rbtnOrg2, rbtnOrg3;
	private TextView tvTitle1, tvTitle2, tvTitle3;
	private LinearLayout layLoading = null;
	private LinearLayout layNodata = null;
	//private ListView lvResults = null;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;

	private int offset = 1;	
	private boolean isLoadmore = false;			// 是否上拉加载更多
	private OrderListAdapter adapter = null;
	
	private boolean isForperson = true; 			// 个人 & 对公
	public static final int TYPE_CUST 	= 1; 		// 客户
	public static final int TYPE_PM 	= 2;		// 客户经理
	public static final int TYPE_ORG 	= 3;		// 机构
	private int selectType = TYPE_CUST;
	private final int ORDER_CK 	= 1;		// 存款
	private final int ORDER_DK 	= 2;		// 贷款
	private final int ORDER_LC 	= 3;		// 理财
	private int selectOrder = ORDER_CK;
	private final String ORG_SJ = "1";		// 省级
	private final String ORG_SZ = "3";	// 市州
	private final String ORG_FR = "2";	// 法人
	private String selectOrgType = ORG_SJ;
	
	// 数据请求
	private HConnection HCon;
	private static final int INDEX_ORDER_CUST_CK 	= 11;
	private static final int INDEX_ORDER_CUST_DK 	= 12;
	private static final int INDEX_ORDER_CUST_LC 	= 13;
	private static final int INDEX_ORDER_PM_CK 	= 21;
	private static final int INDEX_ORDER_PM_DK 	= 22;
	private static final int INDEX_ORDER_PM_LC 	= 23;
	private static final int INDEX_ORDER_ORG 	= 31;
	private int requestIndex = INDEX_ORDER_CUST_CK;
	// 数据集合
	private List<OrderCust> custCKListsForperson = new ArrayList<OrderCust>();
	private List<OrderCust> custCKListsForcommon = new ArrayList<OrderCust>();
	private List<OrderCust> custDKListsForperson = new ArrayList<OrderCust>();
	private List<OrderCust> custDKListsForcommon = new ArrayList<OrderCust>();
	private List<OrderCust> custLCListsForperson = new ArrayList<OrderCust>();
	private List<OrderCust> custLCListsForcommon = new ArrayList<OrderCust>();
	private List<OrderPM> 	pmCKListsForperson 	= new ArrayList<OrderPM>();
	private List<OrderPM> 	pmCKListsForcommon 	= new ArrayList<OrderPM>();
	private List<OrderPM> 	pmDKListsForperson 	= new ArrayList<OrderPM>();
	private List<OrderPM> 	pmDKListsForcommon 	= new ArrayList<OrderPM>();
	private List<OrderPM> 	pmLCListsForperson 	= new ArrayList<OrderPM>();
	private List<OrderPM> 	pmLCListsForcommon 	= new ArrayList<OrderPM>();
	private List<OrderOrg> 	orgLists = new ArrayList<OrderOrg>();

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
			/* === 客户排名 ===*/ 
			case INDEX_ORDER_CUST_CK:	// 存款
				List<OrderCust> resultCustCK = (ArrayList<OrderCust>)msg.obj;
				if(resultCustCK != null && resultCustCK.size() > 0){
					if(isForperson){
						custCKListsForperson = resultCustCK;
					}else{
						custCKListsForcommon = resultCustCK;
					}
					
					adapter.setListCusts(resultCustCK);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
			case INDEX_ORDER_CUST_DK:	// 贷款	
				List<OrderCust> resultCustDK = (ArrayList<OrderCust>)msg.obj;
				if(resultCustDK != null && resultCustDK.size() > 0){
					if(isForperson){
						custDKListsForperson = resultCustDK;
					}else{
						custDKListsForcommon = resultCustDK;
					}
					adapter.setListCusts(resultCustDK);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
			case INDEX_ORDER_CUST_LC:	// 理财	
				List<OrderCust> resultCustLC = (ArrayList<OrderCust>)msg.obj;
				if(resultCustLC != null && resultCustLC.size() > 0){
					if(isForperson){
						custLCListsForperson = resultCustLC;
					}else{
						custLCListsForcommon = resultCustLC;
					}
					adapter.setListCusts(resultCustLC);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
				
			/* === 客户经理排名 ===*/ 
			case INDEX_ORDER_PM_CK:		// 存款
				List<OrderPM> resultPMCK = (ArrayList<OrderPM>)msg.obj;
				if(resultPMCK != null && resultPMCK.size() > 0){
					if(isForperson){
						pmCKListsForperson = resultPMCK;
					}else{
						pmCKListsForcommon = resultPMCK;
					}
					adapter.setListPMs(resultPMCK);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
			case INDEX_ORDER_PM_DK:		// 贷款	
				List<OrderPM> resultPMDK = (ArrayList<OrderPM>)msg.obj;
				if(resultPMDK != null && resultPMDK.size() > 0){
					if(isForperson){
						pmDKListsForperson = resultPMDK;
					}else{
						pmDKListsForcommon = resultPMDK;
					}
					adapter.setListPMs(resultPMDK);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
			case INDEX_ORDER_PM_LC:		// 理财	
				List<OrderPM> resultPMLC = (ArrayList<OrderPM>)msg.obj;
				if(resultPMLC != null && resultPMLC.size() > 0){
					if(isForperson){
						pmLCListsForperson = resultPMLC;
					}else{
						pmLCListsForcommon = resultPMLC;
					}
					adapter.setListPMs(resultPMLC);
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				}else{
					hiddentLoading(true);
				}
				break;
				
			// 机构排名
			case INDEX_ORDER_ORG: // 存款
				List<OrderOrg> resultOrg = (List<OrderOrg>) msg.obj;
				if(resultOrg != null){
					orgLists.addAll(resultOrg);
				}
				adapter.setListOrgs(orgLists);
				adapter.notifyDataSetChanged();
				hiddentLoading(false);
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
		View view = inflater.inflate(R.layout.fragment_pm_order, null);
		findView(view);
		bindOnclick();
		
		setTitleValue(selectType, selectOrder);
		setListviewInfo(selectType, selectOrder);
		return view;
	}
	
	private void findView(View view){
		rbtnForperson = (RadioButton) view.findViewById(R.id.rbtn_warnperson);
		rbtnForcommon = (RadioButton) view.findViewById(R.id.rbtn_warncommon);
		layOrgTypes = (LinearLayout) view.findViewById(R.id.lay_orgTypes);
		rbtnOrg1 = (RadioButton) view.findViewById(R.id.rbtn_type1);
		rbtnOrg2 = (RadioButton) view.findViewById(R.id.rbtn_type2);
		rbtnOrg3 = (RadioButton) view.findViewById(R.id.rbtn_type3);
		tvTitle1 = (TextView) view.findViewById(R.id.lvitem_info1); 
		tvTitle2 = (TextView) view.findViewById(R.id.lvitem_info2); 	// 名称
		tvTitle3 = (TextView) view.findViewById(R.id.lvitem_info3);		// 类别
		rbtnTab01 = (RadioButton) view.findViewById(R.id.rbtn_person_tab01);
		rbtnTab02 = (RadioButton) view.findViewById(R.id.rbtn_person_tab02);
		rbtnTab03 = (RadioButton) view.findViewById(R.id.rbtn_person_tab03);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
//		lvResults = (ListView) view.findViewById(R.id.lv_results);
		ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
	}
	
	private void bindOnclick(){
		rbtnForperson.setOnClickListener(this);
		rbtnForcommon.setOnClickListener(this);
		rbtnTab01.setOnClickListener(this);
		rbtnTab02.setOnClickListener(this);
		rbtnTab03.setOnClickListener(this);
		rbtnOrg1.setOnClickListener(this);
		rbtnOrg2.setOnClickListener(this);
		rbtnOrg3.setOnClickListener(this);
		
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

				setListviewInfo(selectType, selectOrder);
			}
		});
		
		adapter = new OrderListAdapter();
//		lvResults.setAdapter(adapter);
		lvList.setAdapter(adapter);
	}

	
	/**
	 * 显示进度条
	 * @param isNodata
	 */
	private void loading(){
		layLoading.setVisibility(View.VISIBLE);
//		lvResults.setVisibility(View.GONE);
		if(offset == 1){
			ptrl.setVisibility(View.GONE);
		}
		layNodata.setVisibility(View.GONE);
	}
	
	
	/**
	 * 隐藏进度条
	 * @param isNodata
	 */
	private void hiddentLoading(boolean isNodata){
		layLoading.setVisibility(View.GONE);
//		lvResults.setVisibility(View.VISIBLE);
		ptrl.setVisibility(View.VISIBLE);
		layNodata.setVisibility(View.GONE);
		// 没数据
		if(isNodata){
//			lvResults.setVisibility(View.GONE);
			ptrl.setVisibility(View.GONE);
			layNodata.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * 设置选项卡内容
	 * @param selectType
	 */
	public void setOrderType(int selectType){
		this.selectType = selectType;
		if(getActivity() == null){
			return;
		}
		
		setTitleValue(selectType, selectOrder);
	}
	
	/**
	 * 设置机构权限
	 */
	private void setOrgType(){
		// 机构ID
		String staId = LogoActivity.user.getStaId();
		layOrgTypes.setVisibility(View.VISIBLE);
		// 省级  法人
		if (staId.equals("ST000901") || staId.equals("ST001101")) {
			rbtnOrg1.setVisibility(View.VISIBLE);
			rbtnOrg2.setVisibility(View.GONE);
			rbtnOrg3.setVisibility(View.VISIBLE);
		}
		// 省级  市州
		else if(staId.equals("ST000701")){
			rbtnOrg1.setVisibility(View.VISIBLE);
			rbtnOrg2.setVisibility(View.VISIBLE);
			rbtnOrg3.setVisibility(View.GONE);
		}
		// 省级
		else if(staId.equals("ST000501")){
			rbtnOrg1.setVisibility(View.VISIBLE);
			rbtnOrg2.setVisibility(View.GONE);
			rbtnOrg3.setVisibility(View.GONE);
		}
		// relevance_other  不显示
		else{
			layOrgTypes.setVisibility(View.GONE);
		}
	}
	
	
	/**
	 * 设置标签头信息
	 * @param type
	 * @param order
	 */
	private void setTitleValue(int type, int order){
		// 一级类别
		if(type == TYPE_CUST){			// 客户排名
			layOrgTypes.setVisibility(View.GONE);
			tvTitle1.setText("排名");
			tvTitle2.setText("客户名");
		}else if(type == TYPE_PM){		// 客户经理排名
			layOrgTypes.setVisibility(View.GONE);
			tvTitle1.setText("排名");
			tvTitle2.setText("客户经理");
		}else if(type == TYPE_ORG){	// 机构排名
			layOrgTypes.setVisibility(View.VISIBLE);
			tvTitle1.setText("排名");
			tvTitle2.setText("机构名称");
			
			setOrgType();
		}
		// 二级标题
		if(order == ORDER_CK){
			tvTitle3.setText("存款余额(万元)");
		}else if(order == ORDER_DK){
			tvTitle3.setText("贷款余额(万元)");
		}else if(order == ORDER_LC){
			tvTitle3.setText("理财余额(万元)");
		}
		
		// 客户	&&	客户经理
		if(type == TYPE_CUST){
			if(order == ORDER_CK){
				requestIndex = INDEX_ORDER_CUST_CK;
			}else if(order == ORDER_DK){
				requestIndex = INDEX_ORDER_CUST_DK;
			}else if(order == ORDER_LC){
				requestIndex = INDEX_ORDER_CUST_LC;
			}
		}else if(type == TYPE_PM){
			if(order == ORDER_CK){
				requestIndex = INDEX_ORDER_PM_CK;
			}else if(order == ORDER_DK){
				requestIndex = INDEX_ORDER_PM_DK;
			}else if(order == ORDER_LC){
				requestIndex = INDEX_ORDER_PM_LC;
			}
		}
	}
	
	private void setListviewInfo(int type, int order){
		// 客户排名
		if(type == TYPE_CUST){
			lvList.setPullstatus(false, false);
			rbtnTab01.setText("存\n款\n余\n额\n前\n十");
			rbtnTab02.setText("贷\n款\n余\n额\n前\n十");
			rbtnTab03.setText("理\n财\n余\n额\n前\n十");
			
			List<OrderCust> custLists = null;
			// 存款
			if(order == ORDER_CK){ 
				requestIndex = INDEX_ORDER_CUST_CK;
				if(isForperson){
					custLists = custCKListsForperson;
				}else{
					custLists = custCKListsForcommon;
				}
			}
			// 贷款
			else if(order == ORDER_DK){
				requestIndex = INDEX_ORDER_CUST_DK;
				if(isForperson){
					custLists = custDKListsForperson;
				}else{
					custLists = custDKListsForcommon;
				}
			}
			// 理财
			else if(order == ORDER_LC){
				requestIndex = INDEX_ORDER_CUST_LC;
				if(isForperson){
					custLists = custLCListsForperson;
				}else{
					custLists = custLCListsForcommon;
				}
			}
			
			if(custLists != null && custLists.size()>0){
				hiddentLoading(false);
				adapter.setListCusts(custLists);
				adapter.notifyDataSetChanged();
			}else{
				sendRequestForOrderCust();
			}
		}
		// 客户经理排名
		else if(type == TYPE_PM){
			lvList.setPullstatus(false, false);
			rbtnTab01.setText("存\n款\n余\n额");
			rbtnTab02.setText("贷\n款\n余\n额");
			rbtnTab03.setText("理\n财\n余\n额");
			
			List<OrderPM> pmLists = null;
			// 存款
			if(order == ORDER_CK){
				requestIndex = INDEX_ORDER_PM_CK;
				if(isForperson){
					pmLists = pmCKListsForperson;
				}else{
					pmLists = pmCKListsForcommon;
				}
			}
			// 贷款
			else if(order == ORDER_DK){
				requestIndex = INDEX_ORDER_PM_DK;
				if(isForperson){
					pmLists = pmDKListsForperson;
				}else{
					pmLists = pmDKListsForcommon;
				}
			}
			// 理财
			else if(order == ORDER_LC){
				requestIndex = INDEX_ORDER_PM_LC;
				if(isForperson){
					pmLists = pmLCListsForperson;
				}else{
					pmLists = pmLCListsForcommon;
				}
			}
			
			if(pmLists != null && pmLists.size() > 0){
				hiddentLoading(false);
				adapter.setListPMs(pmLists);
				adapter.notifyDataSetChanged();
			}else{
				sendRequestForOrderCust();
			}
		}
		// 机构排名
		else if(type == TYPE_ORG){
			lvList.setPullstatus(false, true);
			// 仅市州分页
			if(selectOrgType.equals(ORG_SZ)){
				lvList.setPullstatus(false, true);
			}
			rbtnTab01.setText("存\n款\n余\n额");
			rbtnTab02.setText("贷\n款\n余\n额");
			rbtnTab03.setText("理\n财\n余\n额");
			
//			List<OrderOrg> orgLists = null;
//			if(isForperson){
//				orgLists = orderOrgsForperson;
//			}else{
//				orgLists = orderOrgsForcommon;
//			}
//			
//			if(orgLists != null && orgLists.size()>0){
//				hiddentLoading(false);
//				adapter.setListOrgs(orgLists);
//				adapter.notifyDataSetChanged();
//			}else{
				sendRequestForOrderOrg();
//			}
		}
	}
	

	/**
	 * 客户排名 && 客户经理排名
	 * @param requestIndex
	 */
	private void sendRequestForOrderCust(){
		if (Tool.haveNet(getActivity())) {
			loading();
			
			String staid = LogoActivity.user.getStaId();
			String requestType = "";
			if(selectType == TYPE_CUST){
				requestType = InterfaceInfo.ORDER_CUST_Get;// 客户查询
			}else if(selectType == TYPE_PM){
				requestType = InterfaceInfo.ORDER_PM_Get;// 客户经理
			}else{
				return;  // 其它类型直接返回
			}
			
			String custtypeStr = "01";  // 个人	
			if(!isForperson){
				custtypeStr = "02";		// 对公
			}

			String typeStr = "";		
			if(selectOrder == ORDER_CK){
				typeStr = "1";	// 存款
			}else if(selectOrder == ORDER_DK){
				typeStr = "2";	// 贷款
			}else if(selectOrder == ORDER_LC){
				typeStr = "3";	// 理财
			}
			
			JSONObject jsinfo = null;
			try {
				jsinfo = new JSONObject();
				jsinfo.put("staid", staid);
				jsinfo.put("custtype", custtypeStr);
				jsinfo.put("type", typeStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String info = jsinfo.toString();
			HCon = RequestInfo(this, Constants.requestType.JsonData, requestType, info, requestIndex, false);
		}
	}
	
	/**
	 * 机构请求数据
	 */
	private void sendRequestForOrderOrg(){
		if (Tool.haveNet(getActivity())) {
			loading();
			String staid = LogoActivity.user.getStaId();
			String custtypeStr = "01";  // 个人	
			if(!isForperson){
				custtypeStr = "02";		// 对公
			}
			
			// 拼装jsonData
			JSONObject jsinfo = new JSONObject();
			try {
				jsinfo.put("baltype", "0"+selectOrder);
				jsinfo.put("staid", staid);
				jsinfo.put("custtype", custtypeStr);
				jsinfo.put("type", selectOrgType);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String jsonData = "&size=20&offset="+offset+"&jsonData="+jsinfo.toString();
			
			String requestType = InterfaceInfo.ORDER_ORG_Get;
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, jsonData, INDEX_ORDER_ORG, false);
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
			
		case INDEX_ORDER_CUST_CK:
		case INDEX_ORDER_CUST_DK:
		case INDEX_ORDER_CUST_LC:
		case INDEX_ORDER_PM_CK:
		case INDEX_ORDER_PM_DK:
		case INDEX_ORDER_PM_LC:
		case INDEX_ORDER_ORG:
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
			Message msg = new Message();
			msg.arg1 = connectionIndex;			
			try {
				switch (connectionIndex) {
				// 客户排名
				case INDEX_ORDER_CUST_CK:	//存款
				case INDEX_ORDER_CUST_DK:	//贷款
				case INDEX_ORDER_CUST_LC:	//理财
					String custGroup = tmpJsonObject.getString("group");
					List<OrderCust> custLists = JSON.parseArray(custGroup, OrderCust.class);
					msg.obj = custLists;
					break;
				
				// 客户经理排名
				case INDEX_ORDER_PM_CK:		//存款
				case INDEX_ORDER_PM_DK: 	//贷款
				case INDEX_ORDER_PM_LC: 	//理财
					String pmGroup = tmpJsonObject.getString("group");
					List<OrderPM> pmLists = JSON.parseArray(pmGroup, OrderPM.class);
					msg.obj = pmLists;
					break;
					
				// 结构排名
				case INDEX_ORDER_ORG:
					String orgGroup = tmpJsonObject.getString("rows");
					List<OrderOrg> orgs = JSON.parseArray(orgGroup, OrderOrg.class);
					msg.obj = orgs;
					break;

				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		} else {
			hiddentLoading(true);
			Toast.makeText(getActivity(), "操作失败! 错误:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
	
	class HolderView{
		private TextView tvValue1;
		private TextView tvValue2;
		private TextView tvValue3;
	}
	
	/**
	 * ListView 适配器
	 * @author Administrator
	 *
	 */
	@SuppressLint("ResourceAsColor")
	class OrderListAdapter extends BaseAdapter{

		private List<OrderCust> listCusts = null;
		private List<OrderPM> 	listPMs	= null;
		private List<OrderOrg> 	listOrgs = null;

		@Override
		public int getCount() {
			if(selectType == TYPE_CUST && listCusts != null){
				return listCusts.size();
			}else if(selectType == TYPE_PM && listPMs != null){
				return listPMs.size();
			}else if(selectType == TYPE_ORG && listOrgs != null){
				return listOrgs.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(selectType == TYPE_CUST && listCusts != null){
				return listCusts.get(position);
			}else if(selectType == TYPE_PM && listPMs != null){
				return listPMs.get(position);
			}else if(selectType == TYPE_ORG && listOrgs != null){
				return listOrgs.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.myreport_person_tab03_item, null);
				holder = new HolderView();
				holder.tvValue1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.tvValue2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.tvValue3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				convertView.setTag(holder);
			}else{
				holder = (HolderView) convertView.getTag();
			}
			// 设置单行背景颜色
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}
			
			String value1 = "";	//排名
			String value2 = "";	//名称
			String value3 = "";	//金额
			// 客户排名
			if(selectType == TYPE_CUST){
				OrderCust property = listCusts.get(position);
				value1 = property.getRANK();
				value2 = property.getUSER_NAME();
				value3 = property.getBAL();
			}
			// 客户经理排名
			else if(selectType == TYPE_PM){
				OrderPM property = listPMs.get(position);
				value1 = property.getRANK();
				value2 = property.getOWNERNAME();
				value3 = property.getBAL();
			}
			// 机构排名
			else if(selectType == TYPE_ORG){
				OrderOrg property = listOrgs.get(position);
				value2 = property.getBRNAME();
				String cmBrName = LogoActivity.user.getBrName();
				if(cmBrName != null && cmBrName.equals(value2)){
					holder.tvValue1.setTextColor(getActivity().getResources().getColor(R.color.red));
					holder.tvValue2.setTextColor(getActivity().getResources().getColor(R.color.red));
					holder.tvValue3.setTextColor(getActivity().getResources().getColor(R.color.red));
				}else{
					holder.tvValue1.setTextColor(getActivity().getResources().getColor(R.color.workplacewordcolor));
					holder.tvValue2.setTextColor(getActivity().getResources().getColor(R.color.workplacewordcolor));
					holder.tvValue3.setTextColor(getActivity().getResources().getColor(R.color.workplacewordcolor));
				}
				
				if(selectOrder == ORDER_CK){		// 存款
					value1 = property.getDEPS_BAL_RANK();
					value3 = property.getDEPS_BAL();
				}else if(selectOrder == ORDER_DK){	// 贷款
					value1 = property.getLOAN_BAL_RANK();
					value3 = property.getLOAN_BAL();
				}else if(selectOrder == ORDER_LC){	// 理财
					value1 = property.getFINA_BAL_RANK();
					value3 = property.getFINA_BAL();
				}
			}
			
			// 赋值
			if(TextUtils.isEmpty(value1)){
				value1 = "--";
			}
			if(TextUtils.isEmpty(value2)){
				value2 = "--";
			}
			holder.tvValue1.setText(value1);
			holder.tvValue2.setText(value2);
			holder.tvValue3.setText(Tool.setFormatValue(value3));
			return convertView;
		}

		public void setListCusts(List<OrderCust> listCusts) {
			this.listCusts = listCusts;
		}

		public void setListPMs(List<OrderPM> listPMs) {
			this.listPMs = listPMs;
		}

		public void setListOrgs(List<OrderOrg> listOrgs) {
			this.listOrgs = listOrgs;
		}
	}

	
	@Override
	public void onClick(View v) {
		// 清空集合
		offset = 1;
		orgLists.removeAll(orgLists);
		
		switch (v.getId()) {
		// 个人
		case R.id.rbtn_warnperson:
			isForperson = true;
			setListviewInfo(selectType, selectOrder);
			break;
		// 对公	
		case R.id.rbtn_warncommon:
			isForperson = false;
			setListviewInfo(selectType, selectOrder);
			break;
			
		// 存款余额
		case R.id.rbtn_person_tab01:
			selectOrder = ORDER_CK;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;
		// 贷款余额
		case R.id.rbtn_person_tab02:
			selectOrder = ORDER_DK;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;
		// 理财余额
		case R.id.rbtn_person_tab03:
			selectOrder = ORDER_LC;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;
			
		// 省级排名
		case R.id.rbtn_type1:
			selectOrgType = ORG_SJ;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;
		// 市州级排名
		case R.id.rbtn_type2:
			selectOrgType = ORG_SZ;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;
		// 法人排名
		case R.id.rbtn_type3:
			selectOrgType = ORG_FR;
			setTitleValue(selectType, selectOrder);
			setListviewInfo(selectType, selectOrder);
			break;

		default:
			break;
		}
	}
	
}
