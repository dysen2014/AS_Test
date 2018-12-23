package com.pactera.financialmanager.ui.commontool;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.CalculatorFinancialFormula;
import com.pactera.financialmanager.util.Tool;

import java.text.DecimalFormat;

/**
 * 买房租房净资产比较器
 * 
 * @author JAY
 * 
 */
public class CalculatorCompareActivity extends ParentActivity implements
		OnClickListener {

	private Button resultBtn;// 计算按钮
	private EditText compare_gross;// 房价总值
	private EditText compare_firstmoney;// 首付款
	private EditText compare_deadline;// 贷款期限
	private TextView compare_othermoney;// 其余贷款
	private EditText compare_apr;// 年利率
	private EditText compare_everymonth;// 每月租房
	private EditText compare_compareyear;// 比较年
	private EditText compare_yearadd;// 年增值率
	private EditText compare_yearincome;// 年收益率

	private double gross = 0;// 房价总值
	private double firstmoney = 0;// 首付款
	private double themoney = 0;// 还款金额
	private double deadline;// 贷款期限
	private double othermoney;// 其余贷款
	private double apr;// 年利率
	private double everymonth;// 每月租房
	private double compareyear;// 比较年
	private double yearadd;// 年增值率
	private double yearincome;// 年收益率

	private double[] oNum = new double[8];
	DecimalFormat df = new DecimalFormat("#0.00");
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_compare);
		findViews();
		bindOnClickListener();
		initTitle(this, "常用工具");
	}

	private void bindOnClickListener() {
		resultBtn.setOnClickListener(this);

		compare_gross.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				gross = Tool.DoubleParse(tempInfo);
				themoney = gross - firstmoney;
				if (themoney < 0) {
					showInfo();
				} else {
					compare_othermoney.setText(String.valueOf(df
							.format(themoney)));

				}
			}
		});

		compare_firstmoney.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				firstmoney = Tool.DoubleParse(tempInfo);
				themoney = gross - firstmoney;
				if (themoney < 0) {
					showInfo();
				} else {
					compare_othermoney.setText(String.valueOf(df
							.format(themoney)));

				}
			}
		});

	}

	// 提示错误信息
	public void showInfo() {
		toast = Toast.makeText(CalculatorCompareActivity.this, "请输入正确金额",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	private void findViews() {

		resultBtn = (Button) findViewById(R.id.calculator_compare_btn);// 计算按钮
		compare_gross = (EditText) findViewById(R.id.calculator_compare_gross);// 房价总值
		compare_firstmoney = (EditText) findViewById(R.id.calculator_compare_firstmoney);// 首付款
		compare_deadline = (EditText) findViewById(R.id.calculator_compare_deadline);// 贷款期限
		compare_othermoney = (TextView) findViewById(R.id.calculator_compare_othermoney);// 其余贷款
		compare_apr = (EditText) findViewById(R.id.calculator_compare_apr);// 年利率
		compare_everymonth = (EditText) findViewById(R.id.calculator_compare_everymonth);// 每月租房
		compare_compareyear = (EditText) findViewById(R.id.calculator_compare_compareyear);// 比较年
		compare_yearadd = (EditText) findViewById(R.id.calculator_compare_yearadd);// 年增值率
		compare_yearincome = (EditText) findViewById(R.id.calculator_compare_yearincome);// 年收益率

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.calculator_compare_btn:
			calculator();
			break;

		default:
			break;
		}
	}

	private void calculator() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(compare_gross.getText().toString())) {
			gross = Double.parseDouble(compare_gross.getText().toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_firstmoney.getText().toString())) {
			firstmoney = Double.parseDouble(compare_firstmoney.getText()
					.toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_deadline.getText().toString())) {
			deadline = Double
					.parseDouble(compare_deadline.getText().toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_apr.getText().toString())) {
			apr = Double.parseDouble(compare_apr.getText().toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_everymonth.getText().toString())) {
			everymonth = Double.parseDouble(compare_everymonth.getText()
					.toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_compareyear.getText().toString())) {
			compareyear = Double.parseDouble(compare_compareyear.getText()
					.toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(compare_yearadd.getText().toString())) {
			yearadd = Double.parseDouble(compare_yearadd.getText().toString());
		} else {
			showToast();
			return;
		}
		if (!TextUtils.isEmpty(compare_yearincome.getText().toString())) {
			yearincome = Double.parseDouble(compare_yearincome.getText()
					.toString());
		} else {
			showToast();
			return;
		}

		if (gross >= firstmoney) {
			othermoney = gross - firstmoney;
			compare_othermoney.setText("" + othermoney);
		} else {
			Toast.makeText(CalculatorCompareActivity.this, "首付款不能比总额高！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (compareyear > deadline) {
			Toast.makeText(CalculatorCompareActivity.this, "还款年限应该在贷款期限内",
					Toast.LENGTH_SHORT).show();
			return;
		}

		oNum[0] = CalculatorFinancialFormula.getKNianHouFangWuJiaZhi(gross,
				yearadd / 100, (int) compareyear);

		oNum[1] = CalculatorFinancialFormula.getMaiFangDaiKuanYuE(gross
				- firstmoney, apr / 100, (int) deadline, (int) compareyear);

		oNum[2] = oNum[0] - oNum[1];

		oNum[3] = CalculatorFinancialFormula.getZuFangShouFuKuanJiaZhi(
				firstmoney, yearincome / 100, (int) compareyear);

		oNum[4] = CalculatorFinancialFormula.getZuFangFangZuJiaZhi(everymonth,
				yearincome / 100, compareyear);

		oNum[5] = CalculatorFinancialFormula.getZuFangYueHuanKuanJiaZhi(gross
				- firstmoney, apr / 100, (int) deadline, (int) compareyear);
		oNum[6] = oNum[3] - oNum[4] + oNum[5];
		oNum[7] = compareyear;
		Intent intent = new Intent(this, CalculatorCompareResultActivity.class);

		intent.putExtra("CompareResult", oNum);

		startActivity(intent);

		finish();

	}

	private void showToast() {
		Toast.makeText(CalculatorCompareActivity.this, "数据不能为空！",
				Toast.LENGTH_SHORT).show();
	}

}
