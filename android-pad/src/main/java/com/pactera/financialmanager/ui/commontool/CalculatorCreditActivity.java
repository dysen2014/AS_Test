package com.pactera.financialmanager.ui.commontool;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;

/**
 * 贷款计算器
 * 
 * @author JAY
 * 
 */
public class CalculatorCreditActivity extends ParentActivity implements OnClickListener {

	// TextView credit_startdate;// 起贷日期
	EditText credit_money;// 贷款金额
	EditText credit_deadline;// 贷款期限
	EditText credit_rate;// 贷款利率
	Button credit_btn;// 计算按钮
	TextView credit_returnmoney;// 基本还款金额
	TextView credit_returninterest;// 基本还款利息
	TextView credit_returntotal;// 累计还款

	RelativeLayout[] credit_cycle_rl = new RelativeLayout[4];// 还款周期对应按钮
																// (月、季、半年、年)
	TextView[] credit_cycle_tv = new TextView[4];// 还款周期对应文字(月、季、半年、年)
	ImageView[] credit_cycle_img = new ImageView[4];// 还款周期对应背景月、季、半年、年)

	RelativeLayout[] credit_backmoney_rl = new RelativeLayout[3];// 到期还本付息，
																	// 等额本金还款，等额本息还款
	ImageView[] credit_backmoney_img = new ImageView[3];// 到期还本付息， 等额本金还款，等额本息还款
	TextView[] credit_backmoney_tv = new TextView[3];// 到期还本付息， 等额本金还款，等额本息还款

	int cycleIndex = 1;// 还款周期(月、季、半年、年)
	int backmoneyIndex = 0;// 还款方式(0到期还本付息、1等额本金还款、2等额本息还款)

	// private double dn = 0, di = 0;/* 贷款期数和贷款利率 */
	DecimalFormat df = new DecimalFormat("#0.0");
	private double[] creditInfo = { 120.0f, 200000.0f,
			6.3 }; /*
					 * 用来表示贷款期限0、贷款金额1 、贷款利率2
					 */
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 时间格式
	// private Calendar c = null;
	// private static String dataSet;// 存放时间缓存

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_credit);
		findViews();
		setListener();
		initTitle(this, R.drawable.changyonggongju);
	}

	private void setListener() {
		// TODO Auto-generated method stub

		credit_cycle_rl[0].setOnClickListener(this);
		credit_cycle_rl[1].setOnClickListener(this);
		credit_cycle_rl[2].setOnClickListener(this);
		credit_cycle_rl[3].setOnClickListener(this);
		credit_backmoney_rl[0].setOnClickListener(this);
		credit_backmoney_rl[1].setOnClickListener(this);
		credit_backmoney_rl[2].setOnClickListener(this);
		credit_btn.setOnClickListener(this);
		// credit_startdate.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		// credit_startdate = (TextView)
		// findViewById(R.id.calculator_credit_startdate);// 起贷日期
		credit_money = (EditText) findViewById(R.id.calculator_credit_money);// 贷款金额
		credit_cycle_rl[0] = (RelativeLayout) findViewById(R.id.calculator_credit_cycle_m_rl);// 月
		credit_cycle_rl[1] = (RelativeLayout) findViewById(R.id.calculator_credit_cycle_s_rl);// 季
		credit_cycle_rl[2] = (RelativeLayout) findViewById(R.id.calculator_credit_cycle_hy_rl);// 半年
		credit_cycle_rl[3] = (RelativeLayout) findViewById(R.id.calculator_credit_cycle_y_rl);// 年
		credit_cycle_tv[0] = (TextView) findViewById(R.id.calculator_credit_cycle_m_tv);// 月
		credit_cycle_tv[1] = (TextView) findViewById(R.id.calculator_credit_cycle_s_tv);
		credit_cycle_tv[2] = (TextView) findViewById(R.id.calculator_credit_cycle_hy_tv);
		credit_cycle_tv[3] = (TextView) findViewById(R.id.calculator_credit_cycle_y_tv);
		credit_cycle_img[0] = (ImageView) findViewById(R.id.calculator_credit_cycle_m_img);// 月
		credit_cycle_img[1] = (ImageView) findViewById(R.id.calculator_credit_cycle_s_img);
		credit_cycle_img[2] = (ImageView) findViewById(R.id.calculator_credit_cycle_hy_img);
		credit_cycle_img[3] = (ImageView) findViewById(R.id.calculator_credit_cycle_y_img);
		credit_deadline = (EditText) findViewById(R.id.calculator_credit_deadline);// 贷款期限

		credit_backmoney_rl[0] = (RelativeLayout) findViewById(R.id.calculator_credit_backmoney_1_rl);// 到期还本付息
		credit_backmoney_rl[1] = (RelativeLayout) findViewById(R.id.calculator_credit_backmoney_2_rl);// 等额本金还款
		credit_backmoney_rl[2] = (RelativeLayout) findViewById(R.id.calculator_credit_backmoney_3_rl);// 等额本息还款
		credit_backmoney_img[0] = (ImageView) findViewById(R.id.calculator_credit_backmoney_1_img);// 到期还本付息
		credit_backmoney_img[1] = (ImageView) findViewById(R.id.calculator_credit_backmoney_2_img);// 等额本金还款
		credit_backmoney_img[2] = (ImageView) findViewById(R.id.calculator_credit_backmoney_3_img);// 等额本息还款
		credit_backmoney_tv[0] = (TextView) findViewById(R.id.calculator_credit_backmoney_1_tv);// 到期还本付息
		credit_backmoney_tv[1] = (TextView) findViewById(R.id.calculator_credit_backmoney_2_tv);// 等额本金还款
		credit_backmoney_tv[2] = (TextView) findViewById(R.id.calculator_credit_backmoney_3_tv);// 等额本息还款

		credit_rate = (EditText) findViewById(R.id.calculator_credit_rate);// 贷款利率
		credit_btn = (Button) findViewById(R.id.calculator_credit_btn);// 计算按钮
		credit_returnmoney = (TextView) findViewById(R.id.calculator_credit_returnmoney);// 基本还款金额
		credit_returninterest = (TextView) findViewById(R.id.calculator_credit_returninterest);// 基本还款利息
		credit_returntotal = (TextView) findViewById(R.id.calculator_credit_returntotal);// 累计还款
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.calculator_credit_cycle_m_rl:
			cycleIndex = 1;
			setChoose(credit_cycle_tv, credit_cycle_img, 0);
			break;
		case R.id.calculator_credit_cycle_s_rl:
			cycleIndex = 3;
			setChoose(credit_cycle_tv, credit_cycle_img, 1);
			break;
		case R.id.calculator_credit_cycle_hy_rl:
			cycleIndex = 6;
			setChoose(credit_cycle_tv, credit_cycle_img, 2);
			break;
		case R.id.calculator_credit_cycle_y_rl:
			cycleIndex = 12;
			setChoose(credit_cycle_tv, credit_cycle_img, 3);
			break;
		case R.id.calculator_credit_backmoney_1_rl:
			backmoneyIndex = 0;
			setChoose(credit_backmoney_tv, credit_backmoney_img, backmoneyIndex);
			break;
		case R.id.calculator_credit_backmoney_2_rl:
			backmoneyIndex = 1;
			setChoose(credit_backmoney_tv, credit_backmoney_img, backmoneyIndex);
			break;
		case R.id.calculator_credit_backmoney_3_rl:
			backmoneyIndex = 2;
			setChoose(credit_backmoney_tv, credit_backmoney_img, backmoneyIndex);
			break;
		case R.id.calculator_credit_btn:
			calculator();
			break;
		// case R.id.calculator_credit_startdate:
		// DatePickerShow(new OnDateSetListener() {
		//
		// @Override
		// public void onDateSet(int year, int month, int day) {
		// // TODO Auto-generated method stub
		// credit_startdate.setText(year+"-"+month+"-"+day);
		// }
		// });
		// break;
		default:
			break;
		}
	}

	// 进行计算
	private void calculator() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(credit_money.getText().toString())) {
			creditInfo[1] = Double.parseDouble(credit_money.getText().toString());
		} else {
			showToast();
			return;
		}
		if (!TextUtils.isEmpty(credit_deadline.getText().toString())) {
			creditInfo[0] = Double.parseDouble(credit_deadline.getText().toString());
		} else {
			showToast();
			return;
		}
		if (!TextUtils.isEmpty(credit_rate.getText().toString())) {
			creditInfo[2] = Double.parseDouble(credit_rate.getText().toString());
		} else {
			showToast();
			return;
		}

		if (backmoneyIndex == 0) {

			credit_returnmoney
					.setText(df.format((creditInfo[1] + creditInfo[1] * creditInfo[2] / 12) / cycleIndex) + "元");
			credit_returninterest.setText(df.format(creditInfo[1] * creditInfo[2] * cycleIndex / 12) + "元");
			credit_returntotal
					.setText(df.format(creditInfo[1] * creditInfo[2] * cycleIndex / 12 + creditInfo[1]) + "元");

			// if (creditInfo[1] < 200000) {
			// credit_returnmoney.setText(df.format(creditInfo[1]) +
			//
			// "元");
			// } else {
			//
			// credit_returnmoney.setText(df.format(creditInfo[1] / 10000)
			//
			// + "万元");
			// }
			// if ((CalculatorFinancialFormula.getDK_dqhbfx(creditInfo[1],
			// creditInfo[2] / 1200, (int) creditInfo[0]) -
			//
			// creditInfo[1]) < 200000) {
			//
			// credit_returninterest.setText(df.format
			//
			// ((CalculatorFinancialFormula.getDK_dqhbfx(creditInfo[1],
			// creditInfo[2]
			//
			// / 1200, (int) creditInfo[0]) -
			//
			// creditInfo[1])) + "元");
			// } else {
			// credit_returninterest.setText(df.format
			//
			// ((CalculatorFinancialFormula.getDK_dqhbfx(creditInfo
			//
			// [1],
			//
			// creditInfo[2] / 1200, (int)
			//
			// creditInfo[0]) - creditInfo[1]) / 10000) + "万元");
			// }
			//
			// if (CalculatorFinancialFormula.getDK_dqhbfx(creditInfo[1],
			// creditInfo[2] / 1200, (int) creditInfo[0]) <
			//
			// 200000) {
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula.getDK_dqhbfx(creditInfo[1],
			// creditInfo[2]
			//
			// / 1200, (int) creditInfo[0])) + "元");
			// } else {
			//
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula.getDK_dqhbfx(creditInfo[1],
			// creditInfo[2]
			//
			// / 1200, (int) creditInfo[0]) /
			//
			// 10000) + "万元");
			// }

		} else if (backmoneyIndex == 1) {

			credit_returnmoney
					.setText(df.format(creditInfo[1] / cycleIndex + creditInfo[1] * creditInfo[2] / 12) + "元");

			double rtnRest = (creditInfo[0] + 1) * creditInfo[1] * creditInfo[2] / 12 / 2;
			credit_returninterest.setText(df.format(rtnRest) + "元");

			credit_returntotal.setText(df.format(rtnRest + creditInfo[1]) + "元");

			// if
			//
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfAverageCapital(creditInfo[1],
			// creditInfo[2] / 1200,
			// (int) (creditInfo[0] / cycleIndex), 1) < 200000) {
			// credit_returnmoney.setText(df.format
			//
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfAverageCapital
			//
			// (creditInfo[1], creditInfo[2] / 1200, (int) (creditInfo[0] /
			//
			// cycleIndex), 1)) + "元");
			// } else {
			// credit_returnmoney.setText(df.format
			//
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfAverageCapital
			//
			// (creditInfo[1], creditInfo[2] / 1200, (int) (creditInfo[0] /
			//
			// cycleIndex), 1) / 10000) + "万元");
			// }
			// if
			// (CalculatorFinancialFormula.getDengEBenJinLeiJiHuanKuanHuanXi(creditInfo[1],
			// creditInfo[2] * cycleIndex / 1200, (int) (creditInfo[0] /
			// cycleIndex),
			// (int) (creditInfo[0] / cycleIndex)) < 200000) {
			// credit_returninterest.setText(df.format
			//
			// (CalculatorFinancialFormula.getDengEBenJinLeiJiHuanKuanHuanXi
			//
			// (creditInfo[1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex)))
			// + "元");
			// } else {
			// credit_returninterest.setText(df.format
			//
			// (CalculatorFinancialFormula.getDengEBenJinLeiJiHuanKuanHuanXi
			//
			// (creditInfo[1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex))
			// / 10000) + "万元");
			// }
			//
			// if
			// (CalculatorFinancialFormula.getPeriodCumulativeRepaymentOfAverageCapital(creditInfo[1],
			// creditInfo[2] *
			//
			// cycleIndex / 1200, (int) (creditInfo[0] / cycleIndex), (int)
			// (creditInfo[0] / cycleIndex)) <
			//
			// 200000) {
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula
			//
			// .getPeriodCumulativeRepaymentOfAverageCapital(creditInfo[1],
			// creditInfo
			//
			// [2] * cycleIndex / 1200, (int) (creditInfo[0] /
			//
			// cycleIndex), (int) (creditInfo[0] /
			//
			// cycleIndex)))
			// + "元");
			// } else {
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula
			//
			// .getPeriodCumulativeRepaymentOfAverageCapital(creditInfo[1],
			// creditInfo
			//
			// [2] * cycleIndex / 1200, (int) (creditInfo[0] /
			//
			// cycleIndex), (int) (creditInfo[0] /
			//
			// cycleIndex))
			// / 10000) + "万元");
			// }

		} else if (backmoneyIndex == 2) {

			double rtnmoney = (creditInfo[1] * creditInfo[2] / 12 * Math.pow(1 + creditInfo[2] / 12, creditInfo[0]))
					/ (Math.pow(1 + creditInfo[2] / 12, creditInfo[0]) - 1);
			double rtntotal = (creditInfo[1] * creditInfo[0] * creditInfo[2] / 12
					/ Math.pow(1 + creditInfo[2] / 12, creditInfo[0])
					/ (Math.pow(1 + creditInfo[2] / 12, creditInfo[0]) - 1));

			credit_returntotal.setText(df.format(rtntotal) + "元");
			credit_returnmoney.setText(df.format(rtnmoney) + "元");
			credit_returninterest.setText(df.format(rtntotal - rtnmoney) + "元");

			// if
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfPrincipal(creditInfo[1],
			// creditInfo[2] * cycleIndex / 1200, (int) (creditInfo[0] /
			// cycleIndex), 1) < 200000) {
			// credit_returnmoney.setText(df.format
			//
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfPrincipal
			//
			// (creditInfo[1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// 1)) + "元");
			// } else {
			// credit_returnmoney.setText(df.format
			//
			// (CalculatorFinancialFormula.getKPeriodRepaymentOfPrincipal
			//
			// (creditInfo[1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// 1) / 10000) + "万元");
			// }
			//
			// if
			// (CalculatorFinancialFormula.getPeriodAccumulatedInterest(creditInfo[1],
			// creditInfo[2] * cycleIndex / 1200, (int) (creditInfo[0] /
			// cycleIndex),
			// (int) (creditInfo[0] / cycleIndex)) < 200000) {
			// credit_returninterest.setText(df.format
			//
			// (CalculatorFinancialFormula.getPeriodAccumulatedInterest(creditInfo
			//
			// [1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex)))
			// + "元");
			// } else {
			// credit_returninterest.setText(df.format
			//
			// (CalculatorFinancialFormula.getPeriodAccumulatedInterest(creditInfo
			//
			// [1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex))
			// / 10000) + "万元");
			// }
			//
			// if
			// (CalculatorFinancialFormula.getPeriodCumulativeRepayment(creditInfo[1],
			// creditInfo[2] * cycleIndex / 1200, (int) (creditInfo[0] /
			// cycleIndex),
			// (int) (creditInfo[0] / cycleIndex)) < 200000) {
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula.getPeriodCumulativeRepayment(creditInfo
			//
			// [1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex)))
			// + "元");
			// } else {
			// credit_returntotal.setText(df.format
			//
			// (CalculatorFinancialFormula.getPeriodCumulativeRepayment(creditInfo
			//
			// [1], creditInfo[2] * cycleIndex /
			//
			// 1200, (int) (creditInfo[0] /
			//
			// cycleIndex),
			// (int) (creditInfo[0] /
			//
			// cycleIndex))
			// / 10000) + "万元");
			// }

		} else {
			Toast.makeText(CalculatorCreditActivity.this, "控制错误",

					Toast.LENGTH_SHORT).show();
		}

	}

	private void showToast() {
		Toast.makeText(CalculatorCreditActivity.this, "数据不能为空！", Toast.LENGTH_SHORT).show();
	}

	// 选择设置
	public void setChoose(TextView[] theCheck, ImageView[] theBg, int index) {

		for (int i = 0; i < theCheck.length; i++) {
			if (i == index) {
				if (index == theCheck.length - 1) {
					theBg[i].setBackgroundResource(R.drawable.itembg5);
				} else if (i == 0) {
					theBg[i].setBackgroundResource(R.drawable.itembg4444);
				} else {
					theBg[i].setBackgroundColor(this.getResources().getColor(R.color.nowworkplacewordbg));
				}

				theCheck[i].setTextColor(this.getResources().getColor(R.color.white));
			} else {
				theBg[i].setBackgroundColor(getResources().getColor(R.color.transparent));
				theCheck[i].setTextColor(this.getResources().getColor(R.color.nowworkplacewordbg));
			}
		}
	}

	// /**
	// * 创建日期及时间选择对话框
	// */
	// public void CreateDialog() {
	// Dialog dialog_1 = null;
	// dataSet = "";
	// AlertDialog.Builder builder = new AlertDialog.Builder(
	// CalculatorCreditActivity.this);
	// c = Calendar.getInstance();
	// dialog_1 = new DatePickerDialog(this,
	// new DatePickerDialog.OnDateSetListener() {
	// public void onDateSet(DatePicker dp, int year, int month,
	// int dayOfMonth) {
	// String monthstr = "", daystr = "";
	// month = month + 1;
	// monthstr = month > 10 ? "" + month : "0" + month;
	// daystr = dayOfMonth > 10 ? "" + dayOfMonth : "0"
	// + dayOfMonth;
	//
	// dataSet = year + "-" + monthstr + "-" + daystr;
	// credit_startdate.setText(dataSet);
	//
	// }
	// }, c.get(Calendar.YEAR), // 传入年份
	// c.get(Calendar.MONTH), // 传入月份
	// c.get(Calendar.DAY_OF_MONTH) // 传入天数
	// );
	//
	// dialog_1.show();
	//
	// }
}
