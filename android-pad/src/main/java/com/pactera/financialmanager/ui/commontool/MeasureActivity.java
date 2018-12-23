package com.pactera.financialmanager.ui.commontool;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.QuerycusActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;

/**
 * 测评
 * 
 * @author JAY
 * 
 */
public class MeasureActivity extends ParentActivity implements OnClickListener {

	FragmentManager fragmentManager;
	FragmentMeasureForPerson measurePerson;
	FragmentMeasureForCompany measureCompany;
	RelativeLayout rl_person;
	ImageView img_person;
	TextView tv_person;
	RelativeLayout rl_common;
	ImageView img_common;
	TextView tv_common;
	EditText measure_username;
	TextView measure_result;
	public static int MeasureResult = 0;// 测评结果
	int MeasureIndex = 0;// 0对私测评、1对公测评
	String cusID;// 客户ID

	String TypeIndex = "";// 结果类型index

	public static final int ResultIndex = 2;

	private HConnection HCon;
	private final int returnIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure);
		initTitle(this, "常用工具");
		// 初始化
		findViews();
		setListener();
		fragmentManager = getSupportFragmentManager();
		setMeasurePerson();
	}

	private void setListener() {
		// TODO Auto-generated method stub
		rl_person.setOnClickListener(this);
		rl_common.setOnClickListener(this);
		measure_username.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		rl_person = (RelativeLayout) findViewById(R.id.measure_rl_person);
		rl_common = (RelativeLayout) findViewById(R.id.measure_rl_common);
		img_person = (ImageView) findViewById(R.id.measure_img_person);
		img_common = (ImageView) findViewById(R.id.measure_img_common);
		tv_person = (TextView) findViewById(R.id.measure_tv_person);
		tv_common = (TextView) findViewById(R.id.measure_tv_common);
		measure_username = (EditText) findViewById(R.id.measure_username);
		measure_result = (TextView) findViewById(R.id.measure_result);
	}

	private void setMeasurePerson() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (measurePerson == null) {
			measurePerson = new FragmentMeasureForPerson();
			transaction.add(R.id.meaasure_framelayout, measurePerson);
		} else {
			transaction.show(measurePerson);
		}
		transaction.commitAllowingStateLoss();
	}

	private void setMeasureCompany() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (measureCompany == null) {
			measureCompany = new FragmentMeasureForCompany();
			transaction.add(R.id.meaasure_framelayout, measureCompany);
		} else {
			transaction.show(measureCompany);
		}
		transaction.commitAllowingStateLoss();
	}

	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (measurePerson != null) {
			transaction.hide(measurePerson);
		}
		if (measureCompany != null) {
			transaction.hide(measureCompany);
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
		case ResultIndex:

			if (TextUtils.isEmpty(cusID)) {
				Toast.makeText(this, "请选择客户！", Toast.LENGTH_SHORT).show();
				return;
			}

			sentData(MeasureResult);
			break;
		}
	}

	/**
	 * 解析返回json
	 * 
	 * @param tmpJsonObject
	 */
	private void readJson(JSONObject tmpJsonObject) {
		// TODO Auto-generated method stub
		String retCode = "";

		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");

			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}

		if (retCode.equals("0000")) {
			Toast.makeText(this, "提交成功！", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "提交失败！", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 把数据传给后台
	 * 
	 * @param Result
	 */
	private void sentData(int Result) {
		// TODO Auto-generated method stub
		if (MeasureIndex == 0) {
			if (Result >= -9 && Result <= 15) {
				TypeIndex = "01";
			} else if (Result >= 16 && Result <= 35) {
				TypeIndex = "02";
			} else if (Result >= 36 && Result <= 60) {
				TypeIndex = "03";
			} else if (Result >= 61 && Result <= 80) {
				TypeIndex = "04";
			} else if (Result >= 81 && Result <= 100) {
				TypeIndex = "05";
			}
		} else if (MeasureIndex == 1) {
			if (Result >= 10 && Result <= 15) {
				TypeIndex = "01";
			} else if (Result >= 16 && Result <= 20) {
				TypeIndex = "02";
			} else if (Result >= 21 && Result <= 30) {
				TypeIndex = "03";
			} else if (Result >= 31 && Result <= 40) {
				TypeIndex = "04";
			} else if (Result >= 41 && Result <= 50) {
				TypeIndex = "05";
			}
		}
		measure_result
				.setText(Result
						+ "分，"
						+ NewCatevalue.getLabel(this, NewCatevalue.riskBear,
								TypeIndex));
		// String cusName = Tool.getTextValue(measure_username);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str_nowtime = formatter.format(curDate);

		String info = "&CUSTID=" + cusID + "&INVSTYLE=" + TypeIndex
				+ "&SCORE	=" + Result + "&CREATE_TIME=" + str_nowtime;

		HCon = RequestInfo(this, requestType.Other, InterfaceInfo.Measure_Save,
				info, returnIndex);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.measure_rl_person:
			setMeasurePerson();
			img_person.setBackgroundResource(R.drawable.itembg4444);
			img_common.setBackgroundColor(getResources().getColor(
					R.color.transparent));
			tv_person.setTextColor(getResources().getColor(R.color.white));
			tv_common.setTextColor(getResources().getColor(
					R.color.nowworkplacewordbg));
			MeasureIndex = 0;
			break;
		case R.id.measure_rl_common:
			setMeasureCompany();
			img_person.setBackgroundColor(getResources().getColor(
					R.color.transparent));
			img_common.setBackgroundResource(R.drawable.itembg5);
			tv_person.setTextColor(getResources().getColor(
					R.color.nowworkplacewordbg));
			tv_common.setTextColor(getResources().getColor(R.color.white));
			MeasureIndex = 1;
			break;
		case R.id.measure_username:
			Intent intent = new Intent();
			intent.putExtra("isMeasure", true);
			intent.setClass(MeasureActivity.this, QuerycusActivity.class);
			startActivityForResult(intent, 1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i("1111111", "requestCode:" + requestCode + ",resultCode:"
				+ resultCode);

		switch (resultCode) {
		case RESULT_OK:
			if (data == null) {
				return;
			}

			Log.i("1111111", "data:" + data.toString());
			boolean isForperson = data.getBooleanExtra("isForperson", true);
			cusID = data.getStringExtra("custID");
			String custName = data.getStringExtra("custName");
			measure_username.setText(custName);

			// 根据查询客户对应选择评测页面
			if (isForperson) {
				setMeasurePerson();
				img_person.setBackgroundResource(R.drawable.itembg4444);
				img_common.setBackgroundColor(getResources().getColor(
						R.color.transparent));
				tv_person.setTextColor(getResources().getColor(R.color.white));
				tv_common.setTextColor(getResources().getColor(
						R.color.nowworkplacewordbg));
				MeasureIndex = 0;
			} else {
				setMeasureCompany();
				img_person.setBackgroundColor(getResources().getColor(
						R.color.transparent));
				img_common.setBackgroundResource(R.drawable.itembg5);
				tv_person.setTextColor(getResources().getColor(
						R.color.nowworkplacewordbg));
				tv_common.setTextColor(getResources().getColor(R.color.white));
				MeasureIndex = 1;
			}

			break;
		default:
			break;
		}
	}
}
