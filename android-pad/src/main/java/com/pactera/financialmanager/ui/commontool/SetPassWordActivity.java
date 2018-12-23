package com.pactera.financialmanager.ui.commontool;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 修改密码
 * 
 * @author YPJ
 * 
 */
public class SetPassWordActivity extends ParentActivity implements
		OnClickListener {

	private EditText setpassword_oldpwd, setpassword_newpwd1,
			setpassword_newpwd2;// 旧密码、新密码、第二次输入新密码
	private Button setpassword_btn;// 确认按钮
	private Button setpassword_cancle;// 取消按钮
	private TextView setpassword_username;// 用户名
	private TextView setpassword_time;
	private static String oldpwd, newpwd1, newpwd2;
	private HConnection setpwdCon;
	private final int setpwdIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpassword);
		findViews();
		setInfo();
		setListener();
		initTitle(this,"常用工具");

	}

	private void setListener() {
		// TODO Auto-generated method stub
		setpassword_btn.setOnClickListener(this);
		setpassword_cancle.setOnClickListener(this);
	}

	private void setInfo() {
		// TODO Auto-generated method stub
		// 设置时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str_nowtime = formatter.format(curDate);
		setpassword_time.setText(str_nowtime);
		// Log.i("1111111", LogoActivity.user.toString());
		// 设置用户名
		if (LogoActivity.user.getUsername() != null) {
			setpassword_username.setText(LogoActivity.user.getUsername()
					.toString());
		}

	}

	// 密码修改
	private void setData() {
		// 109001 111111
		String pwdStr = "&password=" + Tool.ecodeByMD5(newpwd1);

		setpwdCon = RequestInfo(this, requestType.Other,
				InterfaceInfo.PassWord_Save, pwdStr, setpwdIndex);

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			// System.out.println("不在服务网点！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			break;
		case setpwdIndex:
			HResponse res = setpwdCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				// Toast.makeText(ProductCenterTestActivity.this, "网不行",
				// Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			Log.i("JSONObject", tmpJsonObject.toString());
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
//		JSONArray stationJArray = null;
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			Toast.makeText(SetPassWordActivity.this, "密码修改成功!",
					Toast.LENGTH_SHORT).show();
			LogoActivity.user.setPassword(newpwd1.toString().trim());
		} else {
			Toast.makeText(SetPassWordActivity.this, "密码修改失败!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void findViews() {
		setpassword_oldpwd = (EditText) findViewById(R.id.setpassword_oldpwd);
		setpassword_newpwd1 = (EditText) findViewById(R.id.setpassword_newpwd1);
		setpassword_newpwd2 = (EditText) findViewById(R.id.setpassword_newpwd2);
		setpassword_btn = (Button) findViewById(R.id.setpassword_btn);
		setpassword_cancle = (Button) findViewById(R.id.setpassword_cancel);
		setpassword_username = (TextView) findViewById(R.id.setpassword_username);
		setpassword_time = (TextView) findViewById(R.id.setpassword_time);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setpassword_btn:

			// TODO Auto-generated method stub
			oldpwd = setpassword_oldpwd.getText().toString().trim();
			newpwd1 = setpassword_newpwd1.getText().toString().trim();
			newpwd2 = setpassword_newpwd2.getText().toString().trim();
			Log.i("", oldpwd + "," + newpwd1 + "," + newpwd2);
			if (TextUtils.isEmpty(oldpwd) || TextUtils.isEmpty(newpwd1)
					|| TextUtils.isEmpty(newpwd2)) {
				Toast.makeText(SetPassWordActivity.this, "密码不能为空!",
						Toast.LENGTH_SHORT).show();
				return;

			} else if (!newpwd1.equals(newpwd2)) {
				Toast.makeText(SetPassWordActivity.this, "两次密码输入不一致!",
						Toast.LENGTH_SHORT).show();

				return;
			} else if (!oldpwd.equals(LogoActivity.user.getPassword())) {
				Toast.makeText(SetPassWordActivity.this, "密码错误!",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				setData();
			}

			break;
		case R.id.setpassword_cancel:
			finish();
			break;
		default:
			break;
		}
	}

}
