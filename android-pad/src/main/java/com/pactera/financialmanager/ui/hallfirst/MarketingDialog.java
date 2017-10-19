package com.pactera.financialmanager.ui.hallfirst;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pactera.financialmanager.R;

public class MarketingDialog extends Dialog implements View.OnClickListener {
	private TextView tvPdcName;
	private TextView tvMessage1;
	private Button imbt_id;
	private int tvPdcNameId, tvMessage1Id;

	public MarketingDialog(Context context, int tvPdcNameId, int tvMessage1Id) {
		super(context);
		this.tvPdcNameId = tvPdcNameId;
		this.tvMessage1Id = tvMessage1Id;

	}

	public MarketingDialog(Context context) {
		super(context);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setCanceledOnTouchOutside(true); // 设置触摸对话框意外的地方取消对话框
		// 用于为该Dialog设置布局文件
		setContentView(R.layout.marketing_clew_surgery_dialog);
		this.getWindow().setBackgroundDrawableResource(
				R.drawable.icon_cust_skdetail);

		findViews();

		setListeners();

		tvPdcName.setText(tvPdcNameId);
		tvMessage1.setText(tvMessage1Id);

	}

	private void setListeners() {
		imbt_id.setOnClickListener(this);
	}

	private void findViews() {
		tvPdcName = (TextView) findViewById(R.id.tv_product_name);
		tvMessage1 = (TextView) findViewById(R.id.tv_message1);
		imbt_id = (Button) findViewById(R.id.imbt_id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbt_id:
			if (isShowing()) {

				dismiss();
			}

			break;

		default:
			break;
		}
	}
}
