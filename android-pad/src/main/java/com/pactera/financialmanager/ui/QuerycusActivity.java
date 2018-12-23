package com.pactera.financialmanager.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.ocr_new.IDCardInfoReaderActivity;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.hallfirst.CMMarketingActivity2;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.model.CustmerQuery;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户查询
 * 
 * @author DG
 * 
 */
public class QuerycusActivity extends ParentActivity implements
		OnClickListener, OnItemClickListener {

	private RadioButton rbtnPerson, rbtnCommon;
	private EditText customerName,etPapersnum,cardNumber,etCusAccountNum;
	private EditText etPhonenum;
	private View view4;
	private TextView tvTitle1, tvTitle3;;
	private TextView tvValue1, tvValue2, tvValue5;
	private Button btnQuery, btnClear;
	private LinearLayout layLoading;
	private LinearLayout layNodatas;
//	private ListView lvResult;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private CustmerAdapter adapter;

	private int offset = 1;
	private boolean isLoadmore = false;			// 是否上拉加载更多

	private String nameStr = ""; // 客户名称
	private String cardNoStr = ""; // 证件号码
	private String phoneNumStr = ""; // 手机号码
	private String cusCardNumStr = ""; // 客户卡号
	private String cusAccountNumStr = ""; // 客户帐号

	private HConnection HCon;
	private static final int INDEX_CUSTOMER_FORPERSON = 1;
	private static final int INDEX_CUSTOMER_FORCOMMON = 2;
	private List<CustmerQuery> forPersonResults = new ArrayList<CustmerQuery>();
	private List<CustmerQuery> forCommonResults = new ArrayList<CustmerQuery>();

	private boolean isForperson = true;

	private boolean isMeasure = false;
	private boolean isQuery=true;
	private ImageView ocrView;
	//处理照片的数据
	
	private EditText querycusEt5;
	private IDCardResult idCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_querycus);
		Intent intent = getIntent();
		String Name = intent.getStringExtra("Name");
		String NameInfo = intent.getStringExtra("NameInfo");
		initTitle(this, Name, true,NameInfo);
		findView();
		bindOnListener();
		hiddentLoading(true);
		layNodatas.setVisibility(View.GONE);
	}

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
			/* 获取我的客户列表 */
			case INDEX_CUSTOMER_FORPERSON:
			case INDEX_CUSTOMER_FORCOMMON:
				// 结果集
				List<CustmerQuery> results = (ArrayList<CustmerQuery>) msg.obj;
				if (results != null && results.size() > 0) {
					if(results.size()==1){
						CustmerQuery custmerQuery =results.get(0);
						goToCMMarketingActivity(custmerQuery);
					}
					if (isForperson) {
						forPersonResults.addAll(results);
						adapter.setInfolist(forPersonResults);
					} else {
						forCommonResults.addAll(results);
						adapter.setInfolist(forCommonResults);
					}
					adapter.notifyDataSetChanged();
					hiddentLoading(false);
				} else {
					hiddentLoading(true);
				}
				break;

			default:
				break;
			}
		}
	};

	private void findView() {
		ocrView = (ImageView) findViewById(R.id.imv_ocr);
		rbtnPerson = (RadioButton) findViewById(R.id.rbtn_warnperson);
		rbtnCommon = (RadioButton) findViewById(R.id.rbtn_warncommon);
		customerName = (EditText) findViewById(R.id.querycus_cusid);
		etPhonenum = (EditText) findViewById(R.id.querycus_phonenum);
		etPapersnum = (EditText) findViewById(R.id.querycus_papersnumber);
		cardNumber = (EditText) findViewById(R.id.edittxt_querycus_card_num);
		querycusEt5 = (EditText) findViewById(R.id.querycus_et5);
		etCusAccountNum = (EditText) findViewById(R.id.edittxt_querycus_account_num);
		view4 = (View) findViewById(R.id.view4);
		tvTitle1 = (TextView) findViewById(R.id.querycus_text1);
		tvTitle3 = (TextView) findViewById(R.id.querycus_text3);
		tvValue1 = (TextView) findViewById(R.id.lvitem_info1);
		tvValue2 = (TextView) findViewById(R.id.lvitem_info2);
		tvValue5 = (TextView) findViewById(R.id.lvitem_info5);
		btnQuery = (Button) findViewById(R.id.querycus_query);
		btnClear = (Button) findViewById(R.id.querycus_clear);
//		lvResult = (ListView) findViewById(R.id.lv_resultlist);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		lvList = (PullableListView) findViewById(R.id.lv_pulllist);
		lvList.setPullstatus(false, true);
		layLoading = (LinearLayout) findViewById(R.id.lay_loading);
		layNodatas = (LinearLayout) findViewById(R.id.lay_nodata);
		layNodatas.setVisibility(View.GONE);
		Intent intent = getIntent(); // 获取客户类型
		isMeasure = intent.getBooleanExtra("isMeasure", false);
		isQuery = intent.getBooleanExtra("isQuery", true);
	}

	private void bindOnListener() {
		rbtnPerson.setOnClickListener(this);
		rbtnCommon.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnClear.setOnClickListener(this);
//		lvResult.setOnItemClickListener(this);
		lvList.setOnItemClickListener(this);
		adapter = new CustmerAdapter();
		lvList.setAdapter(adapter);
		ocrView.setOnClickListener(this);
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
				
				if (checkQueryValue()) {
					getCusDatas();
				}
			}
		});
	}

	private void loading() {
		btnQuery.setEnabled(false);
		layLoading.setVisibility(View.VISIBLE);
		layNodatas.setVisibility(View.GONE);
//		lvResult.setVisibility(View.GONE);
		if(offset == 1){
			ptrl.setVisibility(View.GONE);
		}
	}

	private void hiddentLoading(boolean nodata) {
		btnQuery.setEnabled(true);
		layLoading.setVisibility(View.GONE);
        if (nodata && offset == 1) {
            layNodatas.setVisibility(View.VISIBLE);
//			lvResult.setVisibility(View.GONE);
            ptrl.setVisibility(View.GONE);
        } else {
            layNodatas.setVisibility(View.GONE);
//			lvResult.setVisibility(View.VISIBLE);
            ptrl.setVisibility(View.VISIBLE);
        }
    }

	/**
	 * 查询List列表信息
	 */
	private void getCusDatas() {
		// 109001 111111
		if (Tool.haveNet(this)) {
			String staID = LogoActivity.user.getStaId();
			int index = INDEX_CUSTOMER_FORPERSON;
			String requestType = InterfaceInfo.QUERYCUS_FORPERSON_MYCUS;
			// 对公
			if (!isForperson) {
				index = INDEX_CUSTOMER_FORCOMMON;
				requestType = InterfaceInfo.QUERYCUS_FORCOMMON_MYCUS;
			}

			JSONObject jsinfo = new JSONObject();
			try {
				String chineseNameStr = Tool.getChineseEncode(nameStr);
				jsinfo.put("CUSTID", "");
				jsinfo.put("CUSTNAME", chineseNameStr);
				jsinfo.put("USERNAME", "");
				jsinfo.put("PHONE_NO", phoneNumStr);
				jsinfo.put("BRNAME", "");
				jsinfo.put("CARDNUMBER", cardNoStr);
				jsinfo.put("CARD_NUM", cusCardNumStr);
				jsinfo.put("ACCOUNT_NUM", cusAccountNumStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String info = "&offset="+offset+"&size=20"+"&spareOne=" + staID + "&jsonData="
					+ jsinfo.toString();
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
			hiddentLoading(true);
			Toast.makeText(this, "系统请求错误", Toast.LENGTH_SHORT).show();
			break;

		// 解析返回结果列表
		case INDEX_CUSTOMER_FORPERSON:
		case INDEX_CUSTOMER_FORCOMMON:
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
			List<CustmerQuery> resultLists = null;
			// 获取列表
			try {
				// 结果集合
				String group = tmpJsonObject.getString("group");
				resultLists = JSON.parseArray(group, CustmerQuery.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			msg.obj = resultLists;
			handler.sendMessage(msg);
		} else {
			hiddentLoading(true);
			Toast.makeText(this, "操作失败! 错误:" + retCode, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private static class HolderView {
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextView lvItemInfo5;
		private View line1;
		private View line2;
		private View line3;
		private View line4;
	}

	/**
	 * 账户余额适配器
	 * 
	 * @author Administrator
	 */
	private class CustmerAdapter extends BaseAdapter {

		private List<CustmerQuery> infolist;

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
			if (infolist != null && infolist.size() > 0) {
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
				convertView = View.inflate(QuerycusActivity.this,
						R.layout.cus_query_item, null);
				holder = new HolderView();
				holder.lvItemInfo1 = (TextView) convertView
						.findViewById(R.id.lvitem_info1);
				holder.lvItemInfo2 = (TextView) convertView
						.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView
						.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView
						.findViewById(R.id.lvitem_info4);
				holder.lvItemInfo5 = (TextView) convertView
						.findViewById(R.id.lvitem_info5);
				holder.line1 = (View) convertView.findViewById(R.id.view1);
				holder.line2 = (View) convertView.findViewById(R.id.view2);
				holder.line3 = (View) convertView.findViewById(R.id.view3);
				holder.line4 = (View) convertView.findViewById(R.id.view4);
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
			// 将获取到的数据进行展示
			CustmerQuery overdue = infolist.get(position);
			holder.lvItemInfo2.setText(overdue.getCUSTNAME());
			holder.lvItemInfo3.setText(overdue.getCUST_PSN_CARD_NUMBER());
			String typeNo = overdue.getCUST_PSN_CARD_TYPE();
			if (typeNo == null) {
				typeNo = "";
			}
			
			if (isForperson) {
				typeNo = NewCatevalue.getLabel(QuerycusActivity.this,
						NewCatevalue.CERTTP, typeNo);
				// 客户编号
				holder.lvItemInfo1.setText(overdue.getCUST_CODE());//**********************************************************CUST_CODE核心客户号
				holder.lvItemInfo4.setText(typeNo);
				holder.lvItemInfo5.setText(overdue.getPHONE_NO());
				holder.lvItemInfo5.setVisibility(View.VISIBLE);
			} else {
				typeNo = NewCatevalue.getLabel(QuerycusActivity.this,
						NewCatevalue.C_CERTTP, typeNo);
				// 核心客户号
				holder.lvItemInfo1.setText(overdue.getCUST_CODE());
				holder.lvItemInfo4.setText(typeNo);
				holder.lvItemInfo5.setText(overdue.getPHONE_NO());
				holder.lvItemInfo5.setVisibility(View.GONE);
			}
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);
			holder.line4.setVisibility(View.GONE);

			return convertView;
		}

		public void setInfolist(List<CustmerQuery> infolist) {
			this.infolist = infolist;
		}
	}

	/**
	 * 清空输入框内容
	 */
	private void clearInputValue() {
		nameStr = "";
		phoneNumStr = "";
		cardNoStr = "";

		customerName.setText("");
		etPhonenum.setText("");
		etPapersnum.setText("");
		cardNumber.setText("");
		etCusAccountNum.setText("");
	}

	/**
	 * 切换对公/对个人状态
	 */
	private void setPersonstatus() {
		// 对个人
		tvTitle1.setText("客户姓名：");
		tvTitle3.setTextColor(getResources().getColor(R.color.black));
		tvValue1.setText("核心客户号");//这里如果不改过来，在每次进行切换之后会显示会回显为客户代码
		tvValue2.setText("客户姓名");
		customerName.setHint("请输入客户姓名...");
		cardNumber.setHint("请输入客户卡号...");
		etCusAccountNum.setHint("请输入客户帐号...");
		etPhonenum.setHint("请输入客户手机号码...");
		view4.setVisibility(View.VISIBLE);
		tvValue5.setVisibility(View.VISIBLE);
		tvTitle3.setVisibility(View.VISIBLE);
		etPhonenum.setVisibility(View.VISIBLE);
		etPhonenum.setBackgroundResource(R.drawable.qy_edit_bg);
		etPhonenum.setEnabled(true);
		// 对公展示数据
		if (!isForperson) {
			tvTitle1.setText("客户名称：");
			customerName.setHint("请输入客户名称");
			tvTitle3.setTextColor(getResources().getColor(R.color.gray));
			tvValue1.setText("核心客户号");
			tvValue2.setText("企业名称");
			etPhonenum.setHint("企业用户不支持手机号查询");
			tvTitle3.setVisibility(View.INVISIBLE);
			etPhonenum.setVisibility(View.INVISIBLE);
			view4.setVisibility(View.GONE);
			tvValue5.setVisibility(View.GONE);
			etPhonenum.setBackgroundResource(R.drawable.qy_edit_bg2);
			etPhonenum.setEnabled(false);
			ocrView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 检查查询条件
	 * 
	 * @return
	 */
	private boolean checkQueryValue() {
		nameStr = customerName.getText().toString().trim();// 客户姓名
		phoneNumStr = etPhonenum.getText().toString().trim();// 电话号码
		cardNoStr = etPapersnum.getText().toString().trim();// 证件号码
		cusCardNumStr = cardNumber.getText().toString().trim();// 客户卡号
		cusAccountNumStr = etCusAccountNum.getText().toString().trim();// 客户帐号

//		// 判断是否为空
//		if (!TextUtils.isEmpty(nameStr) || !TextUtils.isEmpty(phoneNumStr) ||
//		!TextUtils.isEmpty(cusCardNumStr) || !TextUtils.isEmpty(cusAccountNumStr)
//				|| !TextUtils.isEmpty(cardNoStr)) {
		// 判断是否为空
		if (!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(cardNoStr)) {
			return true;
		} else {
			DialogAlert dialog = new DialogAlert(this);
			dialog.show();
			dialog.setMsg("抱歉，查询条件不能为空！");
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		offset = 1;
    	forPersonResults.removeAll(forPersonResults);
    	forCommonResults.removeAll(forCommonResults);
    	
		switch (v.getId()) {
		// 对个人
		case R.id.rbtn_warnperson:
			isForperson = true;
			setPersonstatus();
			if (forPersonResults.size() == 0) {
				hiddentLoading(true);
			} else {
				hiddentLoading(false);
				adapter.setInfolist(forPersonResults);
				adapter.notifyDataSetChanged();
			}
			layNodatas.setVisibility(View.GONE);
			break;
			case R.id.imv_ocr:
				openOCR();
				break;
		// 对公
		case R.id.rbtn_warncommon:
			etPhonenum.setText("");
			isForperson = false;
			phoneNumStr = "";
			setPersonstatus();
			if (forCommonResults.size() == 0) {
				hiddentLoading(true);
			} else {
				hiddentLoading(false);
				adapter.setInfolist(forCommonResults);
				adapter.notifyDataSetChanged();
			}
			layNodatas.setVisibility(View.GONE);
			break;

		// 查询
		case R.id.querycus_query:
			if (checkQueryValue()) {
				loading();
				getCusDatas();
			}
			break;

		// 清空查询条件
		case R.id.querycus_clear:
			clearInputValue();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		onAtyResult(requestCode, resultCode, data, customerName, cardNumber);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		switch (requestCode){
			case RESULT_CANCELED:
				toast("用户拒绝2！");
				break;
			case 1:
				openOCR();
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position,
			long arg3) {
		// 进入客户信息页面
		CustmerQuery custmer = (CustmerQuery) adapter
				.getItemAtPosition(position);
		//判断是否从测评过来的
		if (isMeasure) {
			Intent intent = getIntent();
			intent.putExtra("custID", custmer.getCUSTID());
			intent.putExtra("custName", custmer.getCUSTNAME());
			intent.putExtra("isForperson", isForperson);
			QuerycusActivity.this.setResult(RESULT_OK, intent); 
			finish();
		} else {
			goToCMMarketingActivity(custmer);
		}

	}

	private void goToCMMarketingActivity(CustmerQuery custmer) {
		if (custmer != null) {
            Intent intent = new Intent(this, CMMarketingActivity2.class);
            intent.putExtra("custID", custmer.getCUSTID());// 客户ID
            intent.putExtra("isForperson", isForperson);
            intent.putExtra("isQuery", isQuery);
            startActivity(intent);
        }
	}
}
//"CUSTID":"PN0000080061531557"