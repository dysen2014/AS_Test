package com.pactera.financialmanager.ui.commontool;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;

/**
 * 理财产品收益计算器
 * 
 * @author JAY //结果 = 投资本金*年化收益率/ 收益基础天数*期限
 */
public class CalculatorIncomeActivity extends ParentActivity {

	EditText licai_yearrate;// 年化收益率
	EditText licai_earningday;// 收益基础天数
	EditText licai_deadline;// 期限
	EditText licai_money;// 本金
	TextView licai_income;// 收益金额
	Button licai_btn;

	double yearrate, earningday, deadline, money, income;

	DecimalFormat df = new DecimalFormat("0.###");


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_licai);
		initTitle(this,R.drawable.changyonggongju);
		findViews();

		licai_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(licai_yearrate.getText().toString())) {
					yearrate = Double.parseDouble(licai_yearrate.getText()
							.toString());
				}else{
					Toast.makeText(CalculatorIncomeActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();;
				return;
				}
				if (!TextUtils.isEmpty(licai_earningday.getText()
						.toString())) {
					earningday = Double.parseDouble(licai_earningday.getText()
							.toString());
				}else{
					Toast.makeText(CalculatorIncomeActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();;
				return;
				}
				if (!TextUtils.isEmpty(licai_deadline.getText()
						.toString())) {
					deadline = Double.parseDouble(licai_deadline.getText()
							.toString());
				}else{
					Toast.makeText(CalculatorIncomeActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();;
				return;
				}
				if (!TextUtils.isEmpty(licai_money.getText().toString())) {
					money = Double.parseDouble(licai_money.getText()
							.toString());
				}else{
					Toast.makeText(CalculatorIncomeActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();;
					return;
				}
				
				income=money*yearrate/earningday*deadline/100/365;
			
				licai_income.setText(""+df.format(income));
			}
		});
	}

	private void findViews() {
		// TODO Auto-generated method stub
		licai_yearrate = (EditText) findViewById(R.id.calculator_licai_yearrate);// 年化收益率
		licai_earningday = (EditText) findViewById(R.id.calculator_licai_earningday);// 收益基础天数
		licai_deadline = (EditText) findViewById(R.id.calculator_licai_deadline);// 期限
		licai_money = (EditText) findViewById(R.id.calculator_licai_money);// 本金
		licai_income = (TextView) findViewById(R.id.calculator_licai_income);// 收益金额
		licai_btn = (Button) findViewById(R.id.calculator_licai_btn);
	}

}
