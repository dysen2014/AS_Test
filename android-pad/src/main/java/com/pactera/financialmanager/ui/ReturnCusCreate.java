package com.pactera.financialmanager.ui;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Tool;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;

/**
 * 创建客户回访记录
 * 
 * @author YPJ
 * 
 */
public class ReturnCusCreate extends ParentActivity implements OnClickListener {
	RadioGroup RadioGroup_ServeMode;// 联络渠道
	RadioGroup RadioGroup_ServeType;// 客户服务类型
	RadioGroup RadioGroup_CSStatus;// 执行状态
	EditText rc_create_custname;// 客户名称
	EditText rc_create_title;// 服务标题
	TextView rc_create_bgtime;// 开始时间
	TextView rc_create_endtime;// 结束时间
	EditText rc_create_planservecon;// 服务内容
	TextView rc_create_aworkdate;// 提醒时间
	Button rc_create_save, rc_create_cancel;// 保存、取消
	private String[] ServeModes = { "行内到访", "登门拜访", "电话", "短信", "电子邮件", "传真",
			"信函", "relevance_other" };
	private String[] ServeType = { "生日关怀" + "节日问候", "活动开展", "业务交流" };
	private String[] CSStatus = { "已执行", "未执行" };
	
	//选择的时间对应弹出框(开始时间、结束时间、提醒时间)
	private enum SHOWDialog {BG_TIME,END_TIME,AWORK_DATE};
	
	private static String str_ServeMode;// 联络渠道
	private static String str_ServeType;// 客户服务类型
	private static String str_CSStatus;// 执行状态

	private static String str_nowtime;// 当前时间
	SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 时间格式
	private Calendar c = null;
	private static String dataSet, dataTime;// 存放时间缓存

	private HConnection HCon;
	private final int returnIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returncustomer_create);
		findViews();
		bindOnClickListener();
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		str_nowtime = formatter.format(curDate);
		rc_create_aworkdate.setText(str_nowtime);
		rc_create_bgtime.setText(str_nowtime);
		rc_create_endtime.setText(str_nowtime);

		RadioGroup_ServeMode
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						// 获取变更后的选中项的ID
						int radioButtonId = group.getCheckedRadioButtonId();
						// 根据ID获取RadioButton的实例
						RadioButton rb = (RadioButton) ReturnCusCreate.this
								.findViewById(radioButtonId);
						// 更新文本内容，以符合选中项
						str_ServeMode = rb.getText().toString();
					}
				});

		RadioGroup_ServeType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						// 获取变更后的选中项的ID
						int radioButtonId = group.getCheckedRadioButtonId();
						// 根据ID获取RadioButton的实例
						RadioButton rb = (RadioButton) ReturnCusCreate.this
								.findViewById(radioButtonId);
						// 更新文本内容，以符合选中项
						str_ServeType = rb.getText().toString();
					}
				});

		RadioGroup_CSStatus
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						// 获取变更后的选中项的ID
						int radioButtonId = group.getCheckedRadioButtonId();
						// 根据ID获取RadioButton的实例
						RadioButton rb = (RadioButton) ReturnCusCreate.this
								.findViewById(radioButtonId);
						// 更新文本内容，以符合选中项
						str_CSStatus = rb.getText().toString();
					}
				});
	}

	private void bindOnClickListener() {
		rc_create_save.setOnClickListener(this);
		rc_create_bgtime.setOnClickListener(this);
		rc_create_endtime.setOnClickListener(this);
		rc_create_aworkdate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rc_create_bgtime:
			CreateDialog(SHOWDialog.BG_TIME);
			break;
		case R.id.rc_create_endtime:
			CreateDialog(SHOWDialog.END_TIME);
			break;
		case R.id.rc_create_aworkdate:
			CreateDialog(SHOWDialog.AWORK_DATE);
			break;
		case R.id.rc_create_save:
			getdata();
			break;
		}
	}

	// 数据提交
	private void getdata() {
		// TODO Auto-generated method stub
		String str_custname = rc_create_custname.getText().toString();// 客户名称
		String str_title = rc_create_title.getText().toString();// 服务标题
		String str_planservecon = rc_create_planservecon.getText().toString();// 服务内容
		String str_bgtime = rc_create_bgtime.getText().toString();// 开始时间
		String str_endtime = rc_create_endtime.getText().toString();// 结束时间
		String str_aworkdate = rc_create_aworkdate.getText().toString();// 提醒时间
		int theMode = 1, theType = 1, theStatus = 2;// 联络渠道、客户服务类型、执行状态

		for (int i = 0; i < ServeModes.length; i++) {
			if (ServeModes[i].equals(str_ServeMode)) {
				theMode = i + 1;
				break;
			}
		}
		for (int i = 0; i < ServeType.length; i++) {
			if (ServeType[i].equals(str_ServeType)) {
				theType = i + 1;
				break;
			}
		}
		for (int i = 0; i < CSStatus.length; i++) {
			if (CSStatus[i].equals(str_CSStatus)) {
				theStatus = i + 1;
				break;
			}
		}
		
		// 109001 111111
		if (Tool.haveNet(ReturnCusCreate.this)) {
			String pwdStr = Tool.ecodeByMD5(LogoActivity.user.getPassword());
			String loginStr = LogoActivity.user.getUserCode();
			HRequest request = null;
			String requestHttp="T000015?method=getJSON&userCode="
					+ loginStr + "&password=" + pwdStr + "&seriNo="
					+ "DLXJT06FF18P" + "&brCode=" + LogoActivity.user.getBrID()
					+ "&chnlCode=" + "02" + "&transCode=" + "T000015"
					+ "&AWOKEDATE=" +  URLEncoder.encode(str_aworkdate) + "&CSSTATUS=" + theStatus
					+ "&custName=" + str_custname + "&CUSTID=" + 123
					+ "&SERVEMODE=" + theMode + "&SERVETYPE=" + theType
					+ "&SERVETITLE=" + URLEncoder.encode(str_title) + "&PLANBGDATE=" +  URLEncoder.encode(str_bgtime)
					+ "&PLANENDDATE=" + URLEncoder.encode(str_endtime) + "&PLANSERVECON="
					+ URLEncoder.encode(str_planservecon);
			request = new HRequest(requestHttp, "GET");
			Log.i("", request.toString());
			try {
				HConnection.isShowLoadingProcess = false; // 不显示loading
				HCon = new HConnection(ReturnCusCreate.this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				HCon.setIndex(returnIndex);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			Log.i("", tmpJsonObject.toString());
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
		String retCode = "";
		String theCustServeID = "";
		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");

			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}

		if (retCode.equals("0000")) {
			Toast.makeText(ReturnCusCreate.this, "客户回访记录创建成功!",
					Toast.LENGTH_SHORT).show();
			try {
				theCustServeID = tmpJsonObject.getString("retCode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(ReturnCusCreate.this, "客户回访记录创建失败!",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 创建日期及时间选择对话框
	 */
	public void CreateDialog(final SHOWDialog index) {
		Dialog dialog_1 = null;
		Dialog dialog_2 = null;
		dataSet = "";
		dataTime = "";
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ReturnCusCreate.this);

		c = Calendar.getInstance();
		dialog_1 = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						String monthstr="",daystr="";
						month=month+1;
						monthstr=month>10?""+month:"0"+month;
						daystr=dayOfMonth>10?""+dayOfMonth:"0"+dayOfMonth;
				
						dataSet = year + "-" +monthstr+ "-" + daystr;

					}
				}, c.get(Calendar.YEAR), // 传入年份
				c.get(Calendar.MONTH), // 传入月份
				c.get(Calendar.DAY_OF_MONTH) // 传入天数
		);

		dialog_2 = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						String hourstr="",minutestr="";
						hourstr=hourOfDay>10?""+hourOfDay:"0"+hourOfDay;
						minutestr=minute>10?""+minute:"0"+minute;
						dataTime = hourstr + ":" + minutestr + ":"+"00";
						String finalDate=dataSet+" " + dataTime;
						Log.i("", "1111111111111111111111111:+="+finalDate);
						if (index == SHOWDialog.BG_TIME) {
							rc_create_bgtime.setText(finalDate);
						} else if (index == SHOWDialog.END_TIME) {
							rc_create_endtime.setText(finalDate);
						} else if (index == SHOWDialog.AWORK_DATE) {
							rc_create_aworkdate.setText(finalDate);
						}

					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

		dialog_2.show();
		dialog_1.show();

	}

	//初始化
	private void findViews() {
		// TODO Auto-generated method stub
		RadioGroup_ServeMode = (RadioGroup) findViewById(R.id.radioGroup_servemode);
		RadioGroup_ServeType = (RadioGroup) findViewById(R.id.radioGroup_servetype);
		RadioGroup_CSStatus = (RadioGroup) findViewById(R.id.radioGroup_csstatus);
		rc_create_custname = (EditText) findViewById(R.id.rc_create_custname);
		rc_create_title = (EditText) findViewById(R.id.rc_create_title);
		rc_create_bgtime = (TextView) findViewById(R.id.rc_create_bgtime);
		rc_create_endtime = (TextView) findViewById(R.id.rc_create_endtime);
		rc_create_planservecon = (EditText) findViewById(R.id.rc_create_planservecon);
		rc_create_aworkdate = (TextView) findViewById(R.id.rc_create_aworkdate);
		rc_create_save = (Button) findViewById(R.id.rc_create_save);
		rc_create_cancel = (Button) findViewById(R.id.rc_create_cancel);
	}

}
