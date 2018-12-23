package com.pactera.financialmanager.ui.commontool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;

/**
 * 辅助工具之计算器
 * 
 * @author JAY
 * 
 */
public class CalculatorActivity extends ParentActivity implements
		OnClickListener {

	private ImageView calcaulatorBtn_licai;// 理财产品收益计算器
	private ImageView calcaulatorBtn_fuli;// 复利计算器
	private ImageView calcaulatorBtn_tiqianhuandai;// 提前还贷计算器
	private ImageView calcaulatorBtn_daikuan;// 贷款计算器
	private ImageView calcaulatorBtn_bijiao;// 买房租房计算器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		findViews();
		bindOnClickListener();
		initTitle(this,"常用工具");
	}

	private void bindOnClickListener() {
		calcaulatorBtn_licai.setOnClickListener(this);
		calcaulatorBtn_fuli.setOnClickListener(this);
		calcaulatorBtn_tiqianhuandai.setOnClickListener(this);
		calcaulatorBtn_daikuan.setOnClickListener(this);
		calcaulatorBtn_bijiao.setOnClickListener(this);

	}

	private void findViews() {

		calcaulatorBtn_licai = (ImageView) findViewById(R.id.calcaulator_licai);
		calcaulatorBtn_fuli = (ImageView) findViewById(R.id.calcaulator_fuli);
		calcaulatorBtn_tiqianhuandai = (ImageView) findViewById(R.id.calcaulator_tiqianhuandai);
		calcaulatorBtn_daikuan = (ImageView) findViewById(R.id.calcaulator_daikuan);
		calcaulatorBtn_bijiao = (ImageView) findViewById(R.id.calcaulator_bijiaoqi);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {

		case R.id.calcaulator_daikuan:
			intent = new Intent(CalculatorActivity.this,
					CalculatorCreditActivity.class);
			startActivity(intent);

			break;

		case R.id.calcaulator_tiqianhuandai:
			intent = new Intent(CalculatorActivity.this,
					CalculatorEarlyCreditActivity.class);
			startActivity(intent);

			break;
		case R.id.calcaulator_bijiaoqi:

			intent = new Intent(CalculatorActivity.this,
					CalculatorCompareActivity.class);
			startActivity(intent);

			break;
		case R.id.calcaulator_fuli:
			intent = new Intent(CalculatorActivity.this,
					CalculatorCompoundingActivity.class);
			startActivity(intent);

			break;
		case R.id.calcaulator_licai:
			intent = new Intent(CalculatorActivity.this,
					CalculatorIncomeActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}

	}

}
