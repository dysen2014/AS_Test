package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.model.SerachCustomerInfo;
import com.pactera.financialmanager.util.Constants;

/**
 * 这是厅堂管理中的查询客户显示信息的dialog
 */
public class DialogFragmentQueryCustomerInfo extends DialogFragment implements
		OnClickListener {
	private TextView tvQueryCustomerInfo1, tvQueryCustomerInfo2,
			tvQueryCustomerInfo3, tvQueryCustomerInfo4, tvQueryCustomerInfo5,
			tvQueryCustomerInfo6;
	private LinearLayout layLoading;
	private WebView hallchartshow_wb;
	private Button btnClose;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_showquerycustomerinfo, null);
		setupView(view);
		setListener();
		Bundle bundle = getArguments();
		SerachCustomerInfo customerinfo = (SerachCustomerInfo) bundle.getSerializable("customerinfo");
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.i(".....  sendinfo  ......", "..............:"+customerinfo.toString());
		setData(customerinfo);
		
		int w = Constants.SCREEN_WIDTH * 949 / 1448;
		int h = w * 936 / 1509;
		Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		// 我想隐藏dialog弹出之后也会弹出的软键盘，但现在还没有解决这个问题。
		// View view2 = dialog.getCurrentFocus();
		// if(view2!=null){
		// InputMethodManager im= (InputMethodManager)
		// getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// im.hideSoftInputFromWindow(view2.getWindowToken(), 0);
		// }

		return dialog;
	}

	private void setData(SerachCustomerInfo customerinfo) {
		tvQueryCustomerInfo1.setText(customerinfo.getCUSTID());//客户号
		tvQueryCustomerInfo2.setText(customerinfo.getCUST_PSN_CARD_NUMBER());//证件号
		tvQueryCustomerInfo3.setText(customerinfo.getPHONE_NO());//手机号
		tvQueryCustomerInfo4.setText("");//电话号
		tvQueryCustomerInfo5.setText(customerinfo.getCUST_PSN_CARD_TYPE());//证件类型
		tvQueryCustomerInfo6.setText(customerinfo.getCUST_VALUE_RATE());//价值评级
	}

	private void setListener() {
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void setupView(View view) {
		tvQueryCustomerInfo1 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo1);
		tvQueryCustomerInfo2 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo2);
		tvQueryCustomerInfo3 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo3);
		tvQueryCustomerInfo4 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo4);
		tvQueryCustomerInfo5 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo5);
		tvQueryCustomerInfo6 = (TextView) view
				.findViewById(R.id.tv_querycustomerinfo6);
		btnClose=(Button)view.findViewById(R.id.btnclose);

		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		hallchartshow_wb = (WebView) view.findViewById(R.id.hall_chartshow_wb);

		// 开启本地文件读取（默认为true，不设置也可以）
		hallchartshow_wb.getSettings().setAllowFileAccess(true);
		hallchartshow_wb.getSettings().setJavaScriptEnabled(true);// 开启脚本支持
		hallchartshow_wb.loadUrl("file:///android_asset/echart/myechart3.html");
		// 延迟加载
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				layLoading.setVisibility(View.GONE);
				hallchartshow_wb.loadUrl("javascript:createChart('pie', null, [10,20,30,20,20]);");
			}
		}, 500);
	}

	/**
	 * 
	 * @param Flag
	 * @return
	 */
	public static DialogFragmentQueryCustomerInfo getNewInstance(SerachCustomerInfo customerinfo ) {
		DialogFragmentQueryCustomerInfo fragment = new DialogFragmentQueryCustomerInfo();
//		Intent intent=new Intent();
//		intent.putExtra("customerinfo", customerinfo);
		Bundle bundle=new Bundle();
		bundle.putSerializable("customerinfo", customerinfo);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onClick(View v) {

	}

}
