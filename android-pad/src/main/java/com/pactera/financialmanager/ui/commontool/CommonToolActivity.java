package com.pactera.financialmanager.ui.commontool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.LimitsUtil;

public class CommonToolActivity extends ParentActivity implements
		OnClickListener {

//	ImageView setpwd;// 修改密码
	ImageView calculator;// 计算器
	ImageView riskAssessment;// 风险测评
	ImageView branchMap;// 网点分布
	ImageView cusMap;// 客户分布

	ImageView deviceSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commontool);
		// 初始化
		findViews();
		// 绑定监听器
		bindOnClickListener();
		initTitle(this,R.drawable.changyonggongju);
	}

	private void bindOnClickListener() {
		// TODO Auto-generated method stub
//		 setpwd.setOnClickListener(this);
		 calculator.setOnClickListener(this);
		 riskAssessment.setOnClickListener(this);
		 branchMap.setOnClickListener(this);
		 cusMap.setOnClickListener(this);
		 deviceSetting.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
//		setpwd = (ImageView) findViewById(R.id.comtool_setpwd);
		calculator = (ImageView) findViewById(R.id.comtool_calculator);
		riskAssessment = (ImageView) findViewById(R.id.comtool_risk);
		branchMap = (ImageView) findViewById(R.id.comtool_map);
		cusMap = (ImageView) findViewById(R.id.comtool_cus);

		deviceSetting = (ImageView) findViewById(R.id.comtool_device);
	}

	@Override
	public void onClick(View v) {
		boolean isRight = false;
		Intent intent = new Intent();
		switch (v.getId()) {
//			// 修改密码
//			case R.id.comtool_setpwd:
//				intent.setClass(CommonToolActivity.this, SetPassWordActivity.class);
//				startActivity(intent);
//				break;
			// 计算器
			case R.id.comtool_calculator:
				isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0603);
				if (isRight) {
					intent.setClass(CommonToolActivity.this, CalculatorActivity.class);
					startActivity(intent);
				}
				break;
			// 风险评估
			case R.id.comtool_risk:
				isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0604);
				if (isRight) {
					intent.setClass(CommonToolActivity.this, MeasureActivity.class);
					startActivity(intent);
				}
				break;
			// 网点地图
			case R.id.comtool_map:
				isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0602);
				if (isRight) {
					intent.setClass(CommonToolActivity.this, BranchMapActivity.class);
					startActivity(intent);
				}
				break;
			// 客户分布
			case R.id.comtool_cus:
				isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0601);
				if (isRight) {
					intent.setClass(CommonToolActivity.this, CustomerMapActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.comtool_device:

				break;
			default:
				break;
		}
	}
}
