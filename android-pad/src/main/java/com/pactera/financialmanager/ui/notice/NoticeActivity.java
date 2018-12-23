package com.pactera.financialmanager.ui.notice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.Notice;

public class NoticeActivity extends ParentActivity {

	private LinearLayout noticeBG;
	private TextView noticeSureBtn;
	private String jsonData;
	private Intent intent;
	private TextView noticeContentTxt;
	private List<Notice> noticeList;
	private String contentNotice = "";

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date d1 = new Date();
	private Date d2 = new Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * Intent intent = new Intent(getApplicationContext(),
		 * NoticeActivity.class); Bundle noticeString=new Bundle();
		 * noticeString.putString("jsonData", jsonData);
		 * intent.putExtras(noticeString);
		 * intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(intent);
		 */

		super.onCreate(savedInstanceState);

		setContentView(R.layout.notice);
		findViews();
		noticeBG.setBackgroundResource(R.drawable.notice_bg);

		intent = getIntent();
		if (intent.getExtras().containsKey("jsonData")) {

			jsonData = intent.getStringExtra("jsonData");

		}

		noticeSureBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				LogoActivity.noticeFlagShow = false;

			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {

				readJson(jsonData);
				myHandler.sendEmptyMessage(0);
			}
		}).start();

	}

	/*
	 * JSONArray tmpJsonArray = null; if (tmpJsonObject.has("group")) { try {
	 * tmpJsonArray = tmpJsonObject.getJSONArray("group"); if
	 * (tmpJsonArray.length() > 0) { // ArrayList<CustomerInfo> listCustomerInfo
	 * = new // ArrayList<CustomerInfo>(); customerInfos01 = new
	 * ArrayList<CustomerInfo>(); customerInfos02 = new
	 * ArrayList<CustomerInfo>(); for (int i = 0; i < tmpJsonArray.length();
	 * i++) { CustomerInfo tmpCustomerInfo = new CustomerInfo(); JSONObject
	 * tmpJsonObject02 = (JSONObject) tmpJsonArray
	 */
	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int tap = msg.arg1;
			switch (tap) {
			case 0:
				noticeContentTxt.setText(contentNotice);
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	private void readJson(String jsonData) {

		int number = 0;

		JSONArray tmpJsonArray = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonData);

			if (jsonObject.has("group")) {
				tmpJsonArray = jsonObject.getJSONArray("group");
				if (tmpJsonArray.length() > 0) {
					noticeList = new ArrayList<Notice>();
					for (int i = 0; i < tmpJsonArray.length(); i++) {
						Notice tempNotice = new Notice();
						JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
								.get(i);
						/*
						 * notId; notUrgency; notDetail; notImport; notTitle;
						 * notDate; notCreDate; notCreUser; totalSize; retCode;
						 */
						if (tmpJsonObject02.has("currentTime")) {
							try {
								d1 = df.parse(tmpJsonObject02
										.getString("currentTime"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (tmpJsonObject02.has("notCreDate")) {
							try {
								d2 = df.parse(tmpJsonObject02
										.getString("notCreDate"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						if (tmpJsonObject02.has("notImport")
								&& tmpJsonObject02.getString("notImport")
										.equals("非常重要")
								&& tmpJsonObject02.getString("notUrgency")
										.equals("高")
								&& (d1.getTime() - d2.getTime()) < (55 * 1000)) {
							number++;

							if (tmpJsonObject02.has("notId"))
								tempNotice.notId = tmpJsonObject02
										.getString("notId");
							// 标题
							if (tmpJsonObject02.has("notTitle"))
								tempNotice.notTitle = tmpJsonObject02
										.getString("notTitle");
							contentNotice += (number)
									+ "."
									+ NoticeActivity.this
											.getString(R.string.notice_notTitle)
									+ ":" + tempNotice.notTitle + "\n";
							// 内容
							if (tmpJsonObject02.has("notDetail"))
								tempNotice.notDetail = tmpJsonObject02
										.getString("notDetail");
							contentNotice += "\t"
									+ NoticeActivity.this
											.getString(R.string.notice_notDetail)
									+ ":" + tempNotice.notDetail + "\n";
							// 紧急程度

							if (tmpJsonObject02.has("notUrgency"))
								tempNotice.notUrgency = tmpJsonObject02
										.getString("notUrgency");
							contentNotice += "\t"
									+ NoticeActivity.this
											.getString(R.string.notice_noturgency)
									+ ":" + tempNotice.notUrgency + "\n";
							// 重要性
							if (tmpJsonObject02.has("notImport"))
								tempNotice.notImport = tmpJsonObject02
										.getString("notImport");
							contentNotice += "\t"
									+ NoticeActivity.this
											.getString(R.string.notice_notImport)
									+ ":" + tempNotice.notImport + "\n";
							/*
							 * 公告日期 if (tmpJsonObject02.has("notDate"))
							 * tempNotice.notDate = tmpJsonObject02
							 * .getString("notDate"); contentNotice += "\t" +
							 * NoticeActivity.this
							 * .getString(R.string.notice_notDate) + ":" +
							 * tempNotice.notDate + "\n";
							 */

							/*
							 * if (tmpJsonObject02.has("notCreDate"))
							 * tempNotice.notCreDate = tmpJsonObject02
							 * .getString("notCreDate"); contentNotice += "\t" +
							 * NoticeActivity.this
							 * .getString(R.string.notice_notCreDate) + ":" +
							 * tempNotice.notCreDate + "\n";
							 * 
							 * if (tmpJsonObject02.has("notCreUser"))
							 * tempNotice.notCreUser = tmpJsonObject02
							 * .getString("notCreUser"); contentNotice += "\t" +
							 * NoticeActivity.this
							 * .getString(R.string.notice_notCreUser) + ":" +
							 * tempNotice.notCreUser + "\n\n";
							 */

							if (tmpJsonObject02.has("totalSize"))
								tempNotice.totalSize = tmpJsonObject02
										.getString("totalSize");

							if (tmpJsonObject02.has("retCode"))
								tempNotice.retCode = tmpJsonObject02
										.getString("retCode");

							noticeList.add(tempNotice);
						}

					}
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findViews() {
		noticeBG = (LinearLayout) findViewById(R.id.noticeBG);
		noticeSureBtn = (TextView) findViewById(R.id.noticeSureBtn);
		noticeContentTxt = (TextView) findViewById(R.id.noticeContentTxt);

	}

}
