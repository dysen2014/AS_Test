package com.pactera.financialmanager.ui.customermanager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.CommonCustomerAdapter;
import com.pactera.financialmanager.adapter.PersonCustomerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.hallfirst.CMMarketingActivity2;
import com.pactera.financialmanager.ui.model.MycusCommon;
import com.pactera.financialmanager.ui.model.MycusPerson;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的建档客户
 * 
 * @author dg
 *
 */
public class MyscusActivity extends ParentActivity implements OnClickListener,
		OnItemClickListener {

	// 对公对私选择
	private RadioButton rbtnWarnPerson, rbtnWarnCommon;
	private TextView tvCusnametitle;	// 客户名称
	private EditText etCusname;			// 客户姓名
	private TextView etAreatype;		// 建档状态
	private TextView etCultivatedirct;	// 培植类型
	private TextView etCustype;			// 用户状态
	private Button btnQuery, btnClear;

	private LinearLayout layListviewTop;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private LinearLayout layLoading;
	private LinearLayout layNodata;

	private List<MycusPerson> mycusPersonLists = new ArrayList<MycusPerson>();
	private List<MycusCommon> mycusCommonLists = new ArrayList<MycusCommon>();
	private PersonCustomerAdapter adapterPerson = null;
	private CommonCustomerAdapter adapterCommon = null;

	private HConnection HCon;
	private static final int INDEX_MYCUSPERSON_LISTS = 1;
	private static final int INDEX_MYCUSCOMMON_LISTS = 2;
	private static final int QUERY_SIZE = 20;   // 查询条数
	private int CURRENT_PAGE_P = 1;				// 当前页
	private int CURRENT_PAGE_C = 1;				// 当前页
	private boolean isLoadmore = false;			// 是否上拉加载更多

	private String cusnameStr = ""; 	// 客户名称
	private String statusCodeStr = "";		// 区域类型
	private String custypeCodeStr = ""; 	// 客户类型
	private String cultivateCodeStr = ""; 	// 培植方向
	
	// 点击是否选中对私客户
	private boolean isForperson = true;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			// 对私客户列表
			case INDEX_MYCUSPERSON_LISTS:
				if(isLoadmore){
					ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				List<MycusPerson> personLists = (ArrayList<MycusPerson>)msg.obj;
				if(personLists != null){
					mycusPersonLists.addAll(personLists);
				}
				setListViewValue();
				break;
				
			// 对公客户列表
			case INDEX_MYCUSCOMMON_LISTS:
				if(isLoadmore){
					ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				List<MycusCommon> commonLists = (ArrayList<MycusCommon>)msg.obj;
				if(commonLists != null && commonLists.size() > 0){
					mycusCommonLists.addAll(commonLists);
				}
				setListViewValue();
				break;
				
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myscus);
		initTitle(this, R.drawable.customermanagercon);

		findViews();
		bindOnClickListener();

		hiddenLoading(true);
		layNodata.setVisibility(View.GONE);
	}

	private void findViews() {
		rbtnWarnPerson = (RadioButton) findViewById(R.id.rbtn_warnperson);
		rbtnWarnCommon = (RadioButton) findViewById(R.id.rbtn_warncommon);
		etCusname = (EditText) findViewById(R.id.myscus_cusname);
		tvCusnametitle = (TextView) findViewById(R.id.myscus_text1);
		etAreatype = (TextView) findViewById(R.id.myscus_areatype);
		etCultivatedirct = (TextView) findViewById(R.id.myscus_cultivateDirct);
		etCustype = (TextView) findViewById(R.id.myscus_custype);
		btnQuery = (Button) findViewById(R.id.myscus_query);
		btnClear = (Button) findViewById(R.id.myscus_clear);
		layLoading = (LinearLayout) findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) findViewById(R.id.lay_nodata);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		lvList = (PullableListView) findViewById(R.id.lv_pulllist);
		layListviewTop = (LinearLayout) findViewById(R.id.ll_lvtop);

		adapterPerson = new PersonCustomerAdapter(this);
		adapterCommon = new CommonCustomerAdapter(this);
		lvList.setAdapter(adapterPerson);
		lvList.setPullstatus(false, true);
	}

	private void bindOnClickListener() {
		rbtnWarnPerson.setOnClickListener(this);
		rbtnWarnCommon.setOnClickListener(this);
		etAreatype.setOnClickListener(this);
		etCultivatedirct.setOnClickListener(this);
		etCustype.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		lvList.setOnItemClickListener(this);
		ptrl.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// 下拉刷新
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// 上拉加载更多
				isLoadmore = true;
				if (isForperson) {
					CURRENT_PAGE_P++;
					getMycusPersonDatas(CURRENT_PAGE_P);
				} else {
					CURRENT_PAGE_C++;
					getMycusCommonDatas(CURRENT_PAGE_C);
				}
			}
		});
	}
	
	/**
	 * 显示进度条
	 */
	private void loading(){
		btnQuery.setEnabled(false);
		layLoading.setVisibility(View.VISIBLE);
		ptrl.setVisibility(View.GONE);
		layNodata.setVisibility(View.GONE);
	}
	
	/**
	 * 隐藏进度条
	 * @param noDatas
	 */
	private void hiddenLoading(boolean noDatas){
		btnQuery.setEnabled(true);
		layLoading.setVisibility(View.GONE);
		if(noDatas){
			layNodata.setVisibility(View.VISIBLE);
			ptrl.setVisibility(View.GONE);
		}else{
			layNodata.setVisibility(View.GONE);
			ptrl.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 查询对私客户List列表信息
	 * @param currentPage
	 */
	private void getMycusPersonDatas(int currentPage) {
		if (Tool.haveNet(this)) {
			String staID = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.MYSCUS_PERSON_Get;
			JSONObject jsinfo = new JSONObject();
			try {
				String chineseNameStr = Tool.getChineseEncode(cusnameStr);
				jsinfo.put("CUSTNAME", chineseNameStr);
				jsinfo.put("CUST_CLASS", custypeCodeStr);
				jsinfo.put("CULTIVATE_DIRCT", cultivateCodeStr);
				jsinfo.put("CUST_PSN_STATE", statusCodeStr);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			String info = "&spareOne=" + staID 
					+ "&size=" + QUERY_SIZE
					+ "&offset=" + CURRENT_PAGE_P
					+ "&jsonData=" + jsinfo.toString();
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_MYCUSPERSON_LISTS, false);
		}
	}
	
	/**
	 * 对公客户列表信息
	 * @param currentPage
	 */
	private void getMycusCommonDatas(int currentPage) {
		if (Tool.haveNet(this)) {
			String staID = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.MYSCUS_COMMON_Get;
			JSONObject jsinfo = new JSONObject();
			try {
				String chineseNameStr = Tool.getChineseEncode(cusnameStr);
				jsinfo.put("ORG_LGL_NM", chineseNameStr);
				jsinfo.put("CUST_TYPE", custypeCodeStr);
				jsinfo.put("CULTIVATE_DIRCT", cultivateCodeStr);
				jsinfo.put("CUST_ENT_STATE", statusCodeStr);
			}catch (Exception e) {
				e.printStackTrace();
			}

			String info = "&spareOne=" + staID 
					+ "&size=" + QUERY_SIZE
					+ "&offset=" + CURRENT_PAGE_C
					+ "&jsonData=" + jsinfo.toString();
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_MYCUSCOMMON_LISTS, false);
		}
	}
	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			Toast.makeText(MyscusActivity.this, "系统请求错误", Toast.LENGTH_SHORT).show();
			break;
		
		case INDEX_MYCUSPERSON_LISTS:
		case INDEX_MYCUSCOMMON_LISTS:
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
				// 对私列表
				if(connectionIndex == INDEX_MYCUSPERSON_LISTS){
					String group = tmpJsonObject.getString("group");
					List<MycusPerson> mycusPList = JSON.parseArray(group, MycusPerson.class);
					msg.obj = mycusPList;
				}
				// 对公列表
				if(connectionIndex == INDEX_MYCUSCOMMON_LISTS){
					String group = tmpJsonObject.getString("group");
					List<MycusCommon> mycusCList = JSON.parseArray(group, MycusCommon.class);
					msg.obj = mycusCList;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		} else {
			hiddenLoading(true);
			Toast.makeText(MyscusActivity.this, "抱歉，没有数据! 错误:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 清除输入框
	 */
	private void clearInputvalue() {
		CURRENT_PAGE_P = 1;
		CURRENT_PAGE_C = 1; 
		cusnameStr = "";
		custypeCodeStr = "";
		cultivateCodeStr = "";
		statusCodeStr = "";

		etCusname.setText("");
		etAreatype.setText("");
		etCultivatedirct.setText("");
		etCustype.setText("");
	}
	
	/**
	 * 设置ListView客户信息
	 */
	private void setListviewCus(){
		//listview顶部的标题栏
		layListviewTop.removeAllViews();
		View view = LayoutInflater.from(this).inflate(R.layout.myscus_person_item, null);
		LinearLayout llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_myscusitem);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		// 对公客户
		if(!isForperson){
			view = LayoutInflater.from(this).inflate(R.layout.myscus_common_item, null);
			llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_myscusitem);
		}
		layListviewTop.addView(llWorkPlaceLvTopItem, params);
	}
	
	
	/**
	 * 设置客户列表信息
	 */
	private void setListViewValue(){
		if(isForperson){
			// 显示数据
			if(mycusPersonLists != null && mycusPersonLists.size() > 0){
				hiddenLoading(false);
				adapterPerson.setInfolist(mycusPersonLists);
				adapterPerson.notifyDataSetChanged();
				lvList.setAdapter(adapterPerson);
			}else{
				hiddenLoading(true);
			}
		}else{
			// 显示数据
			if(mycusCommonLists != null && mycusCommonLists.size() > 0){
				hiddenLoading(false);
				adapterCommon.setInfolist(mycusCommonLists);
				adapterCommon.notifyDataSetChanged();
				lvList.setAdapter(adapterCommon);
			}else{
				hiddenLoading(true);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 对私客户
		case R.id.rbtn_warnperson:
			isForperson = true;
			tvCusnametitle.setText("客户名称：");
			etCusname.setHint("请输入客户姓名...");
			clearInputvalue();
			setListviewCus();
			/*
			// 注释默认查询
			if(mycusPersonLists.size() == 0){
				isLoadmore = false;
				loading();
				getMycusPersonDatas(CURRENT_PAGE_P);
			}else{
				setListViewValue();
			}
			*/
			setListViewValue();
			layNodata.setVisibility(View.GONE);
			break;

		// 对公客户
		case R.id.rbtn_warncommon:
			isForperson = false;
			tvCusnametitle.setText("企业名称：");
			etCusname.setHint("请输入企业名称...");
			setListviewCus();
			clearInputvalue();
			setListViewValue();
			layNodata.setVisibility(View.GONE);
			break;
			
		// 建档状态
		case R.id.myscus_areatype:
			SpinnerAdapter.showSelectDialog(this,
					NewCatevalue.filingStatus, etAreatype,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							statusCodeStr = spinnerID;
						}
					});
			break;
			
		// 培植方向	
		case R.id.myscus_cultivateDirct:
			SpinnerAdapter.showSelectDialog(this,
					NewCatevalue.cultivate_dire, etCultivatedirct,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							cultivateCodeStr = spinnerID;
						}
					});
			break;
			
		// 客户类型	
		case R.id.myscus_custype:
			String custType = NewCatevalue.custClass;
			if(!isForperson) {
				custType = NewCatevalue.custClassType;
			}
			SpinnerAdapter.showSelectDialog(this,
					custType, etCustype,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							custypeCodeStr = spinnerID;
						}
					});
			break;
		// 查询按钮
		case R.id.myscus_query:
			cusnameStr = etCusname.getText().toString().trim();// 客户姓名

			loading();
			if(isForperson){
				CURRENT_PAGE_P = 1;
				mycusPersonLists.clear(); // 清空集合
				getMycusPersonDatas(CURRENT_PAGE_P);
			}else{
				CURRENT_PAGE_C = 1;
				mycusCommonLists.clear();
				getMycusCommonDatas(CURRENT_PAGE_C);
			}
			break;
			
		// 清空按钮
		case R.id.myscus_clear:
			clearInputvalue();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		// 点击单项页面跳转
		Intent intent = null;
		Object obj = adapterView.getItemAtPosition(position);
		if(obj instanceof MycusPerson){
			// 对象设置
			MycusPerson mycusPerson = (MycusPerson)obj;
			if(!isMangerUser()){
				intent = new Intent(this, CusArchivingStepTwo.class);
				intent.putExtra("ChooseId", 0);
				intent.putExtra("custID", mycusPerson.getCUSTID());
			}else{
				intent = new Intent(this, CMMarketingActivity2.class);
				intent.putExtra("custID", mycusPerson.getCUSTID());// 客户ID
				intent.putExtra("isForperson", true);
				intent.putExtra("isQuery", true);
			}
		}
		if(obj instanceof MycusCommon){
			// 对象设置
			MycusCommon mycusCommon = (MycusCommon)obj;
			if(!isMangerUser()){
				intent = new Intent(this, CusArchivingStepTwo.class);
				intent.putExtra("ChooseId", 1);
				intent.putExtra("custID", mycusCommon.getCUSTID());
			}else{
				intent = new Intent(this, CMMarketingActivity2.class);
				intent.putExtra("custID", mycusCommon.getCUSTID());// 客户ID
				intent.putExtra("isForperson", false);
				intent.putExtra("isQuery", true);
			}
		}
		startActivity(intent);
	}
	

    /**
     * 判断是否为管理岗
     * @return
     */
    private boolean isMangerUser(){
    	String staID = LogoActivity.user.getStaId().toUpperCase();
    	if(staID.equals("ST000111") || staID.equals("ST000301") 
    			|| staID.equals("ST000701") || staID.equals("ST000901") 
    			|| staID.equals("ST001101") || staID.equals("ST000501")){
    		return true;
    	}
    	return false;
    }

}
