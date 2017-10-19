package com.pactera.financialmanager.ui.commontool;

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
import com.pactera.financialmanager.util.CalculatorFinancialFormula;

/**
 * 复利终值计算器
 * 
 * @author JAY
 * 
 */
public class CalculatorCompoundingActivity extends ParentActivity {

	EditText fuli_nowmoney;// 现值
	EditText fuli_installments;// 期数
	EditText fuli_rate;// 利率
	TextView fuli_income;// 收益金额
	Button fuli_btn;
	double nowmoney,installments,rate,income;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_fuli);
		findViews();
		initTitle(this,R.drawable.changyonggongju);
		fuli_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(fuli_nowmoney.getText().toString())) {
					nowmoney = Double.parseDouble(fuli_nowmoney.getText()
							.toString());
				}else{
					showToast();
				return;
				}
				if (!TextUtils.isEmpty(fuli_installments.getText().toString())) {
					installments = Double.parseDouble(fuli_installments.getText()
							.toString());
				}else{
					showToast();
				return;
				}
				if (!TextUtils.isEmpty(fuli_rate.getText().toString())) {
					rate = Double.parseDouble(fuli_rate.getText()
							.toString());
				}else{
					showToast();
				return;
				}
				
				income=CalculatorFinancialFormula.getDuoQiFuLiZhongZhi(nowmoney,rate,installments);
//				income=nowmoney*Math.pow((1+rate/100), installments);
				fuli_income.setText(""+income);
			}
		});
	}

	private void showToast(){
		Toast.makeText(CalculatorCompoundingActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();
	}
	
	private void findViews() {
		// TODO Auto-generated method stub
		fuli_nowmoney = (EditText) findViewById(R.id.calculator_fuli_nowmoney);
		fuli_installments = (EditText) findViewById(R.id.calculator_fuli_installments);
		fuli_rate = (EditText) findViewById(R.id.calculator_fuli_rate);
		fuli_income = (TextView) findViewById(R.id.calculator_fuli_income);
		fuli_btn = (Button) findViewById(R.id.calculator_fuli_btn);
	}

}
