package com.pactera.financialmanager.ui.commontool;

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;


/**
 * 买房租房净资产比较器结果
 * 
 * @author YPJ
 * 
 */
public class CalculatorCompareResultActivity extends ParentActivity {
	private TextView showTxt1, showTxt2, showTxt3, showTxt4, showTxt5,
			showTxt6, showTxt7, showTxt8;
	private double[] oNum = new double[8];
	DecimalFormat df = new DecimalFormat("#0.00");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_compare_2);
		initTitle(this,"常用工具");
		// 根据数据进行计算
		getShowResultInfor();
		findViews();
		showResultInfor();

	}

	private void showResultInfor() {
		showTxt1.setText(df.format(oNum[0] / 10000) + "万元");
		showTxt2.setText(df.format(oNum[1] / 10000) + "万元");
		showTxt3.setText(df.format(oNum[2] / 10000) + "万元");
		showTxt4.setText(df.format(oNum[3] / 10000) + "万元");
		showTxt5.setText(df.format(oNum[4] / 10000) + "万元");
		showTxt6.setText(df.format(oNum[5] / 10000) + "万元");
		showTxt7.setText(df.format(oNum[6] / 10000) + "万元");
		if (oNum[2] == oNum[6]) {
			showTxt8.setText(oNum[7]
					+ "年后，购房租房情况下资产价值等于租房情况下资产价值，购房和租房在经济上来说没有太大区别！");
		} else if (oNum[2] < oNum[6]) {
			showTxt8.setText(oNum[7]
					+ "年后，购房租房情况下资产价值小于租房情况下资产价值，可以认为租房在经济上来说更合适！");
		} else {
			showTxt8.setText(oNum[7]
					+ "年后，购房租房情况下资产价值大于租房情况下资产价值，可以认为购房在经济上来说更合适！");
		}

	}

	private void getShowResultInfor() {
		// 获取意图intent 值

		Intent intent = getIntent();

		if (intent.getExtras().containsKey("CompareResult")) {

			oNum = intent.getDoubleArrayExtra("CompareResult");
		}
		

	}

	private void findViews() {
		showTxt1 = (TextView) findViewById(R.id.showTxt1);
		showTxt2 = (TextView) findViewById(R.id.showTxt2);
		showTxt3 = (TextView) findViewById(R.id.showTxt3);
		showTxt4 = (TextView) findViewById(R.id.showTxt4);
		showTxt5 = (TextView) findViewById(R.id.showTxt5);
		showTxt6 = (TextView) findViewById(R.id.showTxt6);
		showTxt7 = (TextView) findViewById(R.id.showTxt7);
		showTxt8 = (TextView) findViewById(R.id.showTxt8);

	}

}
