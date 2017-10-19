package com.pactera.financialmanager.ui.customermanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ReturnCusActivity;
import com.pactera.financialmanager.util.LimitsUtil;

/**
 * 客户建档首页
 * @author JAY
 *
 */
public class CusArchivingActivity extends ParentActivity implements OnClickListener {

	private TextView myCus, cusArchiving, returnCus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customermanager);
		initTitle(this,R.drawable.customermanagercon);
		
		// 初始化
		findViews();
		// 绑定监听器
		bindOnClickListener();
	}

	private void bindOnClickListener() {
		myCus.setOnClickListener(this);
		cusArchiving.setOnClickListener(this);
		returnCus.setOnClickListener(this);
	}

	private void findViews() {
		myCus = (TextView) findViewById(R.id.cusarc_btn_myscus);
		cusArchiving = (TextView) findViewById(R.id.cusarc_btn_cusarchiving);
		returnCus = (TextView) findViewById(R.id.cusarc_btn_returncus);
	}

	@Override
	public void onClick(View v) {
		boolean isRight = false;
		Intent intent = new Intent();
		switch (v.getId()) {
		// 我的建档客户
		case R.id.cusarc_btn_myscus:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0301);
			if(isRight){
				intent.setClass(CusArchivingActivity.this, MyscusActivity.class);
				startActivity(intent);
			}
			break;
		// 客户建档
		case R.id.cusarc_btn_cusarchiving:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0302);
			if(isRight){
				intent.setClass(CusArchivingActivity.this, CusArchivingStepOne.class);
				startActivity(intent);
			}
			break;
		// 客户服务记录
		case R.id.cusarc_btn_returncus:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T04);
			if(isRight){
				intent.setClass(CusArchivingActivity.this, ReturnCusActivity.class);
				startActivity(intent);
			}
			break;
		}
	}
}
