package com.pactera.financialmanager.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.credit.CreditCardApplyListActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 贷记卡查询
 * 
 * @author JAY
 * 
 */
public class DiscoverCardActivity extends ParentActivity implements
		OnClickListener {

	// 选择按钮（1开卡进度,2激活情况,3违约情况）
	RelativeLayout[] discover_select_rl = new RelativeLayout[3];
	ImageView[] discover_select_img = new ImageView[3];
	// 身份证号码
	EditText discover_idcard;
	// 内容
	TextView[] discover_info = new TextView[8];
	// 结果
	TextView[] discover_result = new TextView[8];
	// 查询按钮
	Button discover_btn;

	Button card_apply_btn;

	// 开卡进度
	String[] cardOpenStr = { "客户号：", "客户姓名：", "客户联系方式：", "信用卡卡号：", "申请时间：",
			"审批进度：", "待审批人员：", "主办客户经理：" };
	// 违约情况
	String[] cardViolationsStr = { "客户号：", "客户姓名：", "客户联系方式：", "信用卡卡号：",
			"激活时间：", "违约天数：", "违约金额：", "主办客户经理：" };
	// 激活情况
	String[] cardActivationStr = { "客户号：", "客户姓名：", "客户联系方式：", "信用卡卡号：",
			"激活时间：", "", "首刷时间：", "主办客户经理：" };
	private String certcode;

	// 开卡进度、激活情况、违约情况、
	public static enum SelectIndex {
		CARDOPEN, // 开卡进度
		CARDACTIVATION, // 激活情况
		CARDVIOLATIONS;// 违约情况
	};

	SelectIndex theIndex=SelectIndex.CARDOPEN;

	private HConnection HCon;
	private final int returnIndex = 1;

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discovercard);
		// 初始化
		findViews();
		setListener();
		Intent intent = getIntent();
		String Name = intent.getStringExtra("Name");
		String NameInfo = intent.getStringExtra("NameInfo");
		initTitle(this, Name, true,NameInfo);
		LogoActivity.user.getUserID();
		LogoActivity.user.getUserid();
		LogoActivity.user.getStaId();
		certcode = intent.getStringExtra("certcode");//certcode2017.3.3更改为获取身份证
	}

	private void setListener() {
		discover_select_rl[0].setOnClickListener(this);
		discover_select_rl[1].setOnClickListener(this);
		discover_select_rl[2].setOnClickListener(this);
		discover_btn.setOnClickListener(this);
		card_apply_btn.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		discover_select_rl[0] = (RelativeLayout) findViewById(R.id.discovercard_select_1);
		discover_select_rl[1] = (RelativeLayout) findViewById(R.id.discovercard_select_2);
		discover_select_rl[2] = (RelativeLayout) findViewById(R.id.discovercard_select_3);
		discover_select_img[0] = (ImageView) findViewById(R.id.discovercard_select_img_1);
		discover_select_img[1] = (ImageView) findViewById(R.id.discovercard_select_img_2);
		discover_select_img[2] = (ImageView) findViewById(R.id.discovercard_select_img_3);
		discover_idcard = (EditText) findViewById(R.id.discovercard_idcard);
		discover_btn = (Button) findViewById(R.id.discovercard_btn);
		discover_info[0] = (TextView) findViewById(R.id.discovercard_info_1);
		discover_info[1] = (TextView) findViewById(R.id.discovercard_info_2);
		discover_info[2] = (TextView) findViewById(R.id.discovercard_info_3);
		discover_info[3] = (TextView) findViewById(R.id.discovercard_info_4);
		discover_info[4] = (TextView) findViewById(R.id.discovercard_info_5);
		discover_info[5] = (TextView) findViewById(R.id.discovercard_info_6);
		discover_info[6] = (TextView) findViewById(R.id.discovercard_info_7);
		discover_info[7] = (TextView) findViewById(R.id.discovercard_info_8);
		discover_result[0] = (TextView) findViewById(R.id.discovercard_result_1);
		discover_result[1] = (TextView) findViewById(R.id.discovercard_result_2);
		discover_result[2] = (TextView) findViewById(R.id.discovercard_result_3);
		discover_result[3] = (TextView) findViewById(R.id.discovercard_result_4);
		discover_result[4] = (TextView) findViewById(R.id.discovercard_result_5);
		discover_result[5] = (TextView) findViewById(R.id.discovercard_result_6);
		discover_result[6] = (TextView) findViewById(R.id.discovercard_result_7);
		discover_result[7] = (TextView) findViewById(R.id.discovercard_result_8);

		// 设置为默认开卡情况
		for (int i = 0; i < discover_info.length; i++) {
			discover_info[i].setText(cardOpenStr[i]);
		}

		card_apply_btn = (Button) findViewById(R.id.card_apply_btn);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 开卡情况
		case R.id.discovercard_select_1:
			theIndex = SelectIndex.CARDOPEN;
			setSelect();
			break;
		// 激活情况
		case R.id.discovercard_select_2:

			theIndex = SelectIndex.CARDACTIVATION;
			setSelect();
			break;
		// 违约情况
		case R.id.discovercard_select_3:
			theIndex = SelectIndex.CARDVIOLATIONS;
			setSelect();
			break;
		case R.id.discovercard_btn:
			getData();
			break;
		case R.id.card_apply_btn:
			Intent intent = new Intent();
			intent.setClass(DiscoverCardActivity.this, CreditCardApplyListActivity.class);
			intent.putExtra("certcode",certcode);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
	}

	private void getData() {

		String requestInfo;
		switch (theIndex) {
		case CARDOPEN:
			requestInfo = InterfaceInfo.DiscoverCard_CardOpen;
			break;
		case CARDACTIVATION:
			requestInfo = InterfaceInfo.DiscoverCard_CardActivation;
			break;
		case CARDVIOLATIONS:
			requestInfo = InterfaceInfo.DiscoverCard_CardViolations;
			break;
		default:
			requestInfo = InterfaceInfo.DiscoverCard_CardOpen;
			break;
		}

		if (!TextUtils.isEmpty(discover_idcard.getText().toString())) {
			String requestStr = "&spareOne="
					+ Tool.getTextValue(discover_idcard);

			// String sentInfo="&size=10&offset=1";
			HCon = RequestInfo(this, requestType.Other, requestInfo,
					requestStr, returnIndex);

		} else {
			Toast.makeText(this, "身份证信息不能为空!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case returnIndex:
			HResponse res = HCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
		// TODO Auto-generated meth
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			Toast.makeText(this, "获取成功！", Toast.LENGTH_SHORT).show();
			String str = "";
			try {
				JSONArray theInfo = tmpJsonObject.getJSONArray("group");
				if (theInfo.length() < 0) {
					Toast.makeText(this, "没有数据!", Toast.LENGTH_SHORT).show();
					return;
				} else {
					JSONObject temps = (JSONObject) theInfo.opt(0);
					// 开卡情况
					if (theIndex == SelectIndex.CARDOPEN) {

						discover_result[0].setText(Tool.getJsonValue(temps,
								"CUSTID"));// 核心客户号

						discover_result[1].setText(Tool.getJsonValue(temps,
								"CUSTNAME"));// 客户姓名

						discover_result[2].setText(Tool.getJsonValue(temps,
								"CUST_PHONE"));// 客户联系方式

						discover_result[3].setText(Tool.getJsonValue(temps,
								"CARD_NUM"));// 贷记卡卡号

						discover_result[4].setText(Tool.getJsonValue(temps,
								"APPLY_TIME"));// 申请时间(yyyy-mm-dd
						// HH:mm:dd)

						discover_result[6].setText(Tool.getJsonValue(temps,
								"CHECKNAME"));// 待审批人员

						
						String approve_progressStr=NewCatevalue.getLabel(this, NewCatevalue.credit_audit_pro, Tool.getJsonValue(temps,
								"APPROVE_PROGRESS"));
						discover_result[5].setText(approve_progressStr);// 审批进度

						discover_result[7].setText(Tool.getJsonValue(temps,
								"OWNERNAME"));// 客户经理

						// 激活情况
					} else if (theIndex == SelectIndex.CARDACTIVATION) {

						discover_result[0].setText(Tool.getJsonValue(temps,
								"CUSTID"));// 核心客户号

						discover_result[1].setText(Tool.getJsonValue(temps,
								"CUSTNAME"));// 客户姓名

						discover_result[2].setText(Tool.getJsonValue(temps,
								"CUST_PHONE"));// 客户联系方式

						discover_result[3].setText(Tool.getJsonValue(temps,
								"CARD_NUM"));// 贷记卡卡号

						discover_result[4].setText(Tool.getJsonValue(temps,
								"ACTIVATE_TIME"));// 激活时间(yyyy-mm-dd
						// HH:mm:dd)

//						discover_result[5].setText(Tool.getJsonValue(temps,
//								"ACTIVATE_CHANNEL"));// 激活渠道

						discover_result[6].setText(Tool.getJsonValue(temps,
								"FIRST_SWIPING"));// 首刷时间

						discover_result[7].setText(Tool.getJsonValue(temps,
								"OWNERNAME"));// 客户经理

						// 违约情况
					} else if (theIndex == SelectIndex.CARDVIOLATIONS) {

						discover_result[0].setText(Tool.getJsonValue(temps,
								"CUSTID"));// 核心客户号

						discover_result[1].setText(Tool.getJsonValue(temps,
								"CUSTNAME"));// 客户姓名

						discover_result[2].setText(Tool.getJsonValue(temps,
								"CUST_PHONE"));// 客户联系方式

						discover_result[3].setText(Tool.getJsonValue(temps,
								"CARD_NUM"));// 贷记卡卡号

						discover_result[4].setText(Tool.getJsonValue(temps,
								"ACTIVATE_TIME"));// 激活时间(yyyy-mm-dd
						// HH:mm:dd)

						discover_result[5].setText(Tool.getJsonValue(temps,
								"BREAK_DAY")+"天");// 违约天数

						discover_result[6].setText(Tool.getJsonValue(temps,
								"BREAK_MONEY")+"元");// 违约金额

						discover_result[7].setText(Tool.getJsonValue(temps,
								"OWNERNAME"));// 客户经理

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(retCode.equals("2001")){
			Toast.makeText(this, "查询不到该数据!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "查询失败!", Toast.LENGTH_SHORT).show();
		}
	}

	private void setSelect() {
		String[] infoStr = null;

		for (int i = 0; i < discover_select_img.length; i++) {
			// if (Index == i) {
			// discover_select_img[i]
			// .setImageResource(R.drawable.com_select_on);
			// } else {
			discover_select_img[i].setImageResource(R.drawable.com_select_off);
			// }
		}
		
		
		for (int i = 0; i < discover_result.length; i++) {
			discover_result[i].setText("");
		}
		
		switch (theIndex) {
		case CARDOPEN:
			discover_select_img[0].setImageResource(R.drawable.com_select_on);
			infoStr = cardOpenStr;
			break;
		case CARDACTIVATION:
			discover_select_img[1].setImageResource(R.drawable.com_select_on);
			infoStr = cardActivationStr;
			break;
		case CARDVIOLATIONS:
			discover_select_img[2].setImageResource(R.drawable.com_select_on);
			infoStr = cardViolationsStr;
			break;
		default:
			break;
		}

		//更新显示数据
		for (int i = 0; i < discover_info.length; i++) {
			discover_info[i].setText(infoStr[i]);
		}

		// 清空选择
		for (int i = 0; i < discover_result.length; i++) {
			discover_result[0].setText("");
		}
	}
}
