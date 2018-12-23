package com.pactera.financialmanager.ui.notice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.IBinder;
import android.os.Message;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentService;
import com.pactera.financialmanager.ui.model.Notice;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;

public class NoticeService extends ParentService {
	// 定义变量 控制 系统运行步骤 测试
	public static final String[] RUNINDEXVALUES = { "FIRSTINDEX",
			"SECONDINDEX", "THIRDINDEX", "FOURTHINDEX", "FIFTHINDEX" };
	// 声明一个Timer 执行广告播放
	private TimerTask timeTask;
	private Timer timer = new Timer();
	// 设置广告的启动和间隔时间常量
	private final static long STARTTIME = 2 * 1000;
	private final static long DURATION = 50 * 1000;
	private int noticeDialogFlag = 0;

	@SuppressWarnings("unused")
	private String jsonData;
	private HConnection noticeCon;
	private HRequest request;
	private final int noticeIndex = 1;
	private List<Notice> noticeList;
	private String contentAllNotice = "";
	private SimpleDateFormat df;
	private Date d1 = new Date();
	private Date d2 = new Date();

	/*
	 * <!--T000027 通知-->
	 * http://10.170.37.191:9080/services/T000027?method=getJSON
	 * &userCode=120101195104142559
	 * &seriNo=863868010224281&chnlCode=eee&transCode=T000027
	 */@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeTask = new TimerTask() {

			@Override
			public void run() {
				/*
				 * @Miles 启动广告界面
				 * 
				 * *
				 */
				noticeDialogFlag++;
				request = new HRequest(
						"T000027?method=getJSON&userCode=120101195104142559&seriNo=863868010224281&chnlCode=eee&transCode=T000027",
						"GET");
				try {
					noticeCon = new HConnection(NoticeService.this, request,
							LogoActivity.user,
							HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				} catch (Exception e) {
					e.printStackTrace();
				}
				noticeCon.setIndex(noticeIndex);

			}
		};

		timer.schedule(timeTask, STARTTIME, DURATION);
	}

	@Override
	public void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case noticeIndex:
			HResponse res = noticeCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				// Toast.makeText(ProductCenterTestActivity.this, "网不行",
				// Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;

			contentAllNotice = "";

			readJson(tmpJsonObject);

			LogoActivity.noticeAllText = contentAllNotice;
			if (noticeDialogFlag > 1) {

				Intent intent = new Intent(getApplicationContext(),
						NoticeActivity.class);

				intent.putExtra("jsonData", tmpJsonObject.toString());

				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if (LogoActivity.noticeFlagShow == true) {
					startActivity(intent);
				}
			}

			break;
		}
	}

	private void readJson(JSONObject jsonObject) {

		JSONArray tmpJsonArray = null;
		try {

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
								&& (d1.getTime() - d2.getTime()) < (50 * 1000)) {
							LogoActivity.noticeFlagShow = true;
						}

						if (tmpJsonObject02.has("notId"))
							tempNotice.notId = tmpJsonObject02
									.getString("notId");

						// 标题
						if (tmpJsonObject02.has("notTitle"))
							tempNotice.notTitle = tmpJsonObject02
									.getString("notTitle");

						contentAllNotice += (i + 1)
								+ "."
								+ NoticeService.this
										.getString(R.string.notice_notTitle)
								+ ":" + tempNotice.notTitle + "\t";
						// 内容
						if (tmpJsonObject02.has("notDetail"))
							tempNotice.notDetail = tmpJsonObject02
									.getString("notDetail");

						contentAllNotice += "\t"
								+ NoticeService.this
										.getString(R.string.notice_notDetail)
								+ ":" + tempNotice.notDetail + "\t";
						// 紧急程度
						if (tmpJsonObject02.has("notUrgency"))
							tempNotice.notUrgency = tmpJsonObject02
									.getString("notUrgency");

						contentAllNotice += "\t"
								+ NoticeService.this
										.getString(R.string.notice_noturgency)
								+ ":" + tempNotice.notUrgency + "\t";
						// 重要性
						if (tmpJsonObject02.has("notImport"))
							tempNotice.notImport = tmpJsonObject02
									.getString("notImport");

						contentAllNotice += "\t"
								+ NoticeService.this
										.getString(R.string.notice_notImport)
								+ ":" + tempNotice.notImport + "\t\t\t\t\t\t";
						/*
						 * // 公告日期 if (tmpJsonObject02.has("notDate"))
						 * tempNotice.notDate = tmpJsonObject02
						 * .getString("notDate");
						 * 
						 * contentAllNotice += "\t" + NoticeService.this
						 * .getString(R.string.notice_notDate) + ":" +
						 * tempNotice.notDate + "\t\t\t\t\t";
						 */
						/*
						 * if (tmpJsonObject02.has("notCreDate"))
						 * tempNotice.notCreDate = tmpJsonObject02
						 * .getString("notCreDate");
						 * 
						 * contentAllNotice += "\t" + NoticeService.this
						 * .getString(R.string.notice_notCreDate) + ":" +
						 * tempNotice.notCreDate + "\t"; if
						 * (tmpJsonObject02.has("notCreUser"))
						 * tempNotice.notCreUser = tmpJsonObject02
						 * .getString("notCreUser");
						 * 
						 * contentAllNotice += "\t" + NoticeService.this
						 * .getString(R.string.notice_notCreUser) + ":" +
						 * tempNotice.notCreUser + "\t\t\t\t";
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
