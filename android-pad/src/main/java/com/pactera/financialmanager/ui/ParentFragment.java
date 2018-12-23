package com.pactera.financialmanager.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.model.IDCardResult;
import com.dysen.common_res.common.utils.FormatUtil;
import com.dysen.common_res.common.utils.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.pactera.financialmanager.credit.common.ocr_new.IDCardInfoReaderActivity;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.CitySelectWindow.CallBackCity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import q.rorbin.badgeview.QBadgeView;

import static com.pactera.financialmanager.ui.ParentActivity.idCard;

/**
 * Fragment模板框架
 * 
 * @author JAY
 * 
 */
public class ParentFragment extends Fragment {

	public Handler handler;
	public int connectionIndex;
	public static final int DIALOG_ID = 10001;
	public static final int isTrue = 1;
	public static final int isFalse = 0;
	private CitySelectWindow thisCity;
	private LinearLayoutManager mLayoutManager;

	public ParentFragment() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onConnected(msg);
			}
		};
	}
	public static void backActivity(final Activity activity, LinearLayout layBack) {
		if (layBack != null) {
			layBack.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					activity.finish();
				}
			});
		}
	}
	public void CityPicker(View view, CitySelect.CallBackCitySelect callBackInf) {
		CitySelect citySelect = new CitySelect(getActivity(), view);
		citySelect.showCity(callBackInf);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());


		StatusBarUtil.setColor(getActivity(), Color.parseColor("#ea452f"), 0);
		return textView;
	}

	public static class MyUtils{

		public static void setSelItemColor(CardView view, @ColorInt int colorId) {

//		LogUtils.d("colorId:"+colorId);
			if (oldView == null) {
				//第一次点击
				oldView = view;
				oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
				view.setCardBackgroundColor(colorId);

			} else {
				//非第一次点击
				//把上一次点击的 变化
				oldView.setBackgroundResource(oldColor);
				view.setCardBackgroundColor(Color.TRANSPARENT);

				oldView = view;
			}
		}
	}

	public static class OCRUtils{

	}
	protected void openOCR() {

		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager
				.PERMISSION_GRANTED) {
			//未授权
			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
		} else {
			Intent intent = new Intent(getActivity(), IDCardInfoReaderActivity.class);
			startActivityForResult(intent, IDCardInfoReaderActivity.REQUEST_CODE_CAMERA);
		}
	}

	protected void onAtyResult(int requestCode, int resultCode, Intent data, EditText customerName, EditText cardNumber) {

		LogUtils.d("requestCode=" + requestCode + "\tresultCode=" + resultCode+"\tdata="+data);
		if (requestCode == IDCardInfoReaderActivity.REQUEST_CODE_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
//                    IDCardResult idCard = (IDCardResult) data.getSerializableExtra(IDCardInfoReaderActivity
//                            .KEY_ID_CARD);
					if (idCard != null) {
						LogUtils.d("idCard=" + idCard + "\tidCard.=" + idCard.getName());
						setValue(idCard, customerName, cardNumber);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				if (data != null) {
					String str = data.getStringExtra(IDCardInfoReaderActivity
							.KEY_NO_ID_CARD);
					toast(str);
//					ShowDialog(getActivity(), str);
				}
			}
		}
	}

	private void setValue(IDCardResult idCard, EditText customerName, EditText cardNumber) {
		if (idCard != null) {
			// 设置页面信息
			String name = idCard.getName().toString();
			String cardNo = idCard.getIdNumber().toString();
			if (!TextUtils.isEmpty(name)) {
				customerName.setText(name);
			}
			if (!TextUtils.isEmpty(cardNo)) {
				cardNumber.setText(cardNo);
			}
		} else {
			toast("身份证信息识别失败");
		}
	}

	@SuppressWarnings("deprecation")
	public void onConnected(Message msg) {
		try {
			switch (msg.what) {
			case HConnection.RESPONSE_ERROR:
				Bundle data = msg.getData();
				// String error = data.getString("value");
				// showError(this, error);
				connectionIndex = HConnection.RESPONSE_ERROR;
				break;

			default:
				data = msg.getData();
				connectionIndex = Integer.parseInt(data.getString("value"));
				break;
			}
			getActivity().removeDialog(DIALOG_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean checkObjValid(Object obj) {
		if (obj != null)
			return true;
		else
			return false;
	}

	protected void ShowDialog(Context context, String str){
		DialogAlert dialog = new DialogAlert(context);
		dialog.show();
		dialog.setMsg(str);
//		dialog.setMsg("抱歉，查询条件不能为空！");
	}

	/**
	 * 日期选择器
	 *
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener) {
		Calendar calendar = Calendar.getInstance();

		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"datepicker");

	}
	/**
	 * 日期选择器
	 *
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener,String strDate) {

		Calendar calendar = convertCaledarByStr(strDate);
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"datepicker");

	}
	public Calendar convertCaledarByStr(String strDate){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			if(strDate != null && !"".equals(strDate)) {
				date = sdf.parse(strDate);
				calendar.setTime(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * 日期选择器
	 * 
	 * @param OnTimeSetListener
	 */
	public void TimePickerShow(OnTimeSetListener OnTimeSetListener) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				OnTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, false);
		timePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"timepicker");
	}

	/**
	 * 城市选择器
	 * 
	 * @param callBackInfo
	 */
	public void CityPickerShow(View view,CallBackCity callBackInfo) {
		thisCity = new CitySelectWindow(getActivity(),view);
		thisCity.showCity(callBackInfo);
	}
	//城市选择器，层级判断
	public void CityPickerShow(View view,int showAddressLevel ,CallBackCity callBackInfo) {

		thisCity = new CitySelectWindow(getActivity(),view);
		if(showAddressLevel==1){
			thisCity.setShowcity(false);
		}else if(showAddressLevel ==2 ){
			thisCity.setShowArea(false);
		}else if(showAddressLevel == 3){
			thisCity.setShowCounty(false);
		}else if(showAddressLevel == 4){
			thisCity.setShowStreet(false);
		}

		thisCity.showCity(callBackInfo);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (thisCity != null) {
			thisCity.destory();
		}
	}

	/**
	 * 请求同样方式
	 * 
	 * @param context
	 *            请求的Fragment
	 * @param requestType
	 *            请求类型
	 * @param requestCode
	 *            接口
	 * @param info
	 *            请求实体内容
	 * @param Index
	 *            返回index
	 */
	public HConnection RequestInfo(ParentFragment context,
			Constants.requestType requestType, String requestCode, String info,
			int Index,boolean isShow) {
		HConnection HCon = null;
		if (Tool.haveNet(getActivity())) {
			String requestHttp = Tool.requestHttp(requestCode, "getJSON");
			switch (requestType) {
			case Search:
			case Delete:
				requestHttp = requestHttp + "&spareOne=" + info;
				break;
			case Insert:
			case Update:
			case JsonData:
				requestHttp = requestHttp + "&jsonData=" + info;
				break;
			case Paging:
				requestHttp = requestHttp + "&size=30&offset=1"+info;
				break;
			case Other:
				requestHttp = requestHttp + info;
				break;
			default:
				break;
			}
			HRequest request = new HRequest(requestHttp, "GET");
			try {
				HConnection.isShowLoadingProcess = isShow; // 不显示loading
				HCon = new HConnection(context, request,
						LogoActivity.user);
				HCon.setIndex(Index);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return HCon;
	}
	
	public HConnection RequestInfo(ParentFragment context,
			Constants.requestType requestType, String requestCode, String info,
			int Index) {
		return RequestInfo(context, requestType, requestCode, info, Index, true);
		
	}

	/**
	 * 绑定 View
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(View view, int id) {
		return (T) view.findViewById(id);
	}

	private static View oldView;
	static int oldColor;

	/**
	 * 设置item 被选中时的效果
	 */
	public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null) {
			//第一次点击
			oldView = view;
			oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
		} else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
			oldView = view;
		}
	}

	public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId, @ColorRes @DrawableRes int oldColorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null) {
			//第一次点击
			oldView = view;
			oldColor = oldColorId;//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
		} else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
			oldView = view;
		}
	}

	/**
	 * Toast消息提示
	 *
	 * @param text
	 */
	public void toast(CharSequence text) {
		// 2s内只提示一次
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime > 2000) {
			lastTime = currentTime;
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
		}
	}

	protected RecyclerView setRecyclerView(RecyclerView recyclerView) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(getActivity());
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}

	protected RecyclerView setRecyclerView(RecyclerView recyclerView, int orientation) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(getActivity());
		//垂直方向 OrientationHelper.HORIZONTAL 0 OrientationHelper.VERTICAL 1
		mLayoutManager.setOrientation(orientation);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}

	/**
	 * 显示/隐藏 View 设值
	 *
	 * @param view
	 * @param text
	 * @param bl
	 */
	public void setTextHide(View view, String text, boolean bl) {
		TextView mTextView = null;
		Button btn = null;
		if (bl)
			view.setVisibility(View.INVISIBLE);
		else {
			view.setVisibility(View.VISIBLE);
			if (view instanceof TextView)
				mTextView.setText(text);
			else if (view instanceof Button)
				btn.setText(text);
		}
	}

	public void setBadgeView(View view, String text) {

		LogUtils.d("view==="+view);
		QBadgeView badge = new QBadgeView(getActivity());
		badge.bindTarget(view);
		badge.setBadgeTextSize(16, true);
		if (FormatUtil.isNumeric(text))
			badge.setBadgeNumber(Integer.parseInt(text));
		else
			badge.setBadgeText(text);
	}

	protected static String getTypeName(String customerType) {

		String name = "";
		if (customerType.equals("010"))
			name = "公";
		if (customerType.equals("030"))
			name = "个";
		if (customerType.equals("040"))
			name = "农";

		return name;
	}

	private long lastTime = 0;

	/**
	 * Toast消息提示
	 *
	 * @param text
	 */
	public void toast(String text) {
		// 2s内只提示一次
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime > 2000) {
			lastTime = currentTime;
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
		}
	}

	public void setBadgeView(View view, int count) {

		QBadgeView badge = new QBadgeView(getContext());
		badge.bindTarget(view);

		badge.setBadgeNumber(count);
	}


	protected void syncScroll(final RecyclerView leftList,  final RecyclerView rightList) {
		leftList.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
					// note: scrollBy() not trigger OnScrollListener
					// This is a known issue. It is caused by the fact that RecyclerView does not know how LayoutManager will handle the scroll or if it will handle it at all.
					rightList.scrollBy(dx, dy);
				}
			}
		});

		rightList.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
					leftList.scrollBy(dx, dy);
				}
			}
		});
	}
}
