package com.pactera.financialmanager.ui.commontool;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.CalculatorFinancialFormula;

/**
 * 提前还款计算器
 * 
 * @author JAY
 * 
 */
public class CalculatorEarlyCreditActivity extends ParentActivity implements
		OnClickListener {

	TextView earlyback_startdate;// 起贷日期
	EditText earlyback_money;// 贷款金额
	EditText earlyback_deadline;// 贷款期限
	RelativeLayout[] earlyback_cycle_rl = new RelativeLayout[4];// 还款周期对应按钮
																// (月、季、半年、年)
	TextView[] earlyback_cycle_tv = new TextView[4];// 还款周期对应文字(月、季、半年、年)
	ImageView[] earlyback_cycle_img = new ImageView[4];// 还款周期对应背景月、季、半年、年)
	RelativeLayout[] earlyback_backmoney_rl = new RelativeLayout[2];// 等额本息还款，
																	// 等额本金还款
	ImageView[] earlyback_backmoney_img = new ImageView[2];// 等额本息还款， 等额本金还款
	TextView[] earlyback_backmoney_tv = new TextView[2];// 等额本息还款， 等额本金还款
	EditText earlyback_rate;// 贷款利率
	Button earlyback_btn;// 计算按钮
	TextView earlyback_returnmoney;// 基本还款金额
	TextView earlyback_returninterest;// 基本还款利息
	TextView earlyback_returntotal;// 累计还款

	TextView earlyback_earlyday;// 提前还款日期
	EditText earlyback_earlymoney;// 提前还款金额

	// 还款方式（0一次性、1部分）
	TextView[] earlyback_theway_tv = new TextView[2];
	RelativeLayout[] earlyback_theway_rl = new RelativeLayout[2];
	ImageView[] earlyback_theway_img = new ImageView[2];
	// 还款状态（0贷款期限不变、还款金额不变）
	TextView[] earlyback_status_tv = new TextView[2];
	RelativeLayout[] earlyback_status_rl = new RelativeLayout[2];
	ImageView[] earlyback_status_img = new ImageView[2];

	int cycleIndex = 0;// 还款周期（0月、1季、2半年、3年）
	int backmoneyIndex = 0;// 还款方式（0等额本金还款、1等额本息还款）
	int thewayIndex = 0; // 提前还款方式（0一次性、1部分）
	int statusIndex = 0;// 还款状态（0贷款期限不变、还款金额不变）

	private static String str_nowtime;// 当前时间
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 时间格式
	private Calendar c = null;
	private static String dataSet;// 存放时间缓存
	int startDate = 0;// 借贷日期
	int endDate = 1;// 还贷日期

	private long dateNum = 10 * 365;// 还款时间差
	private DecimalFormat numdf = new DecimalFormat("#0.0");
	private double[] iNum = { 120.0f, 200000.0f, 7, 3000.0f }; /*
																 * 用来表示贷款期限0 、
																 * 贷款金额1 、 贷款利率2
																 * , 提前还款金额3
																 */
	private double[] oNum = { 3000, 9, 30 }; /*
											 * 首付款金额0 、 累计付息1 、 累计还款2 ,
											 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator_earlycredit);
		findViews();
		setListener();
		initTitle(this,R.drawable.changyonggongju);
	}

	private void setListener() {
		// TODO Auto-generated method stub

		earlyback_cycle_rl[0].setOnClickListener(this);
		earlyback_cycle_rl[1].setOnClickListener(this);
		earlyback_cycle_rl[2].setOnClickListener(this);
		earlyback_cycle_rl[3].setOnClickListener(this);
		earlyback_backmoney_rl[0].setOnClickListener(this);
		earlyback_backmoney_rl[1].setOnClickListener(this);
		earlyback_theway_rl[0].setOnClickListener(this);
		earlyback_theway_rl[1].setOnClickListener(this);
		earlyback_status_rl[0].setOnClickListener(this);
		earlyback_status_rl[1].setOnClickListener(this);
		earlyback_btn.setOnClickListener(this);
		earlyback_startdate.setOnClickListener(this);
		earlyback_earlyday.setOnClickListener(this);
	}

	private void findViews() {
		// TODO Auto-generated method stub
		earlyback_startdate = (TextView) findViewById(R.id.calculator_earlyback_startdate);// 起贷日期
		earlyback_money = (EditText) findViewById(R.id.calculator_earlyback_money);// 贷款金额
		earlyback_cycle_rl[0] = (RelativeLayout) findViewById(R.id.calculator_earlyback_cycle_m_rl);// 月
		earlyback_cycle_rl[1] = (RelativeLayout) findViewById(R.id.calculator_earlyback_cycle_s_rl);// 季
		earlyback_cycle_rl[2] = (RelativeLayout) findViewById(R.id.calculator_earlyback_cycle_hy_rl);// 半年
		earlyback_cycle_rl[3] = (RelativeLayout) findViewById(R.id.calculator_earlyback_cycle_y_rl);// 年
		earlyback_cycle_tv[0] = (TextView) findViewById(R.id.calculator_earlyback_cycle_m_tv);// 月
		earlyback_cycle_tv[1] = (TextView) findViewById(R.id.calculator_earlyback_cycle_s_tv);
		earlyback_cycle_tv[2] = (TextView) findViewById(R.id.calculator_earlyback_cycle_hy_tv);
		earlyback_cycle_tv[3] = (TextView) findViewById(R.id.calculator_earlyback_cycle_y_tv);
		earlyback_cycle_img[0] = (ImageView) findViewById(R.id.calculator_earlyback_cycle_m_img);// 月
		earlyback_cycle_img[1] = (ImageView) findViewById(R.id.calculator_earlyback_cycle_s_img);
		earlyback_cycle_img[2] = (ImageView) findViewById(R.id.calculator_earlyback_cycle_hy_img);
		earlyback_cycle_img[3] = (ImageView) findViewById(R.id.calculator_earlyback_cycle_y_img);
		earlyback_deadline = (EditText) findViewById(R.id.calculator_earlyback_deadline);// 贷款期限

		earlyback_backmoney_rl[0] = (RelativeLayout) findViewById(R.id.calculator_earlyback_backmoney_1_rl);// 到期还本付息
		earlyback_backmoney_rl[1] = (RelativeLayout) findViewById(R.id.calculator_earlyback_backmoney_2_rl);// 等额本金还款
		earlyback_backmoney_img[0] = (ImageView) findViewById(R.id.calculator_earlyback_backmoney_1_img);// 到期还本付息
		earlyback_backmoney_img[1] = (ImageView) findViewById(R.id.calculator_earlyback_backmoney_2_img);// 等额本金还款
		earlyback_backmoney_tv[0] = (TextView) findViewById(R.id.calculator_earlyback_backmoney_1_tv);// 到期还本付息
		earlyback_backmoney_tv[1] = (TextView) findViewById(R.id.calculator_earlyback_backmoney_2_tv);// 等额本金还款
		earlyback_rate = (EditText) findViewById(R.id.calculator_earlyback_rate);// 贷款利率
		earlyback_btn = (Button) findViewById(R.id.calculator_earlyback_btn);// 计算按钮
		earlyback_returnmoney = (TextView) findViewById(R.id.calculator_early_returnmoney);// 基本还款金额
		earlyback_returninterest = (TextView) findViewById(R.id.calculator_early_returninterest);// 基本还款利息
		earlyback_returntotal = (TextView) findViewById(R.id.calculator_early_returntotal);// 累计还款
		earlyback_earlyday = (TextView) findViewById(R.id.calculator_earlyback_earlyday);// 提前天数
		earlyback_earlymoney = (EditText) findViewById(R.id.calculator_earlyback_earlymoney);// 提前金额
		earlyback_theway_rl[0] = (RelativeLayout) findViewById(R.id.calculator_early_theway_0_rl);
		earlyback_theway_rl[1] = (RelativeLayout) findViewById(R.id.calculator_early_theway_1_rl);
		earlyback_theway_img[0] = (ImageView) findViewById(R.id.calculator_early_theway_0_img);
		earlyback_theway_img[1] = (ImageView) findViewById(R.id.calculator_early_theway_1_img);
		earlyback_theway_tv[0] = (TextView) findViewById(R.id.calculator_early_theway_0_tv);
		earlyback_theway_tv[1] = (TextView) findViewById(R.id.calculator_early_theway_1_tv);
		earlyback_status_rl[0] = (RelativeLayout) findViewById(R.id.calculator_early_status_0_rl);
		earlyback_status_rl[1] = (RelativeLayout) findViewById(R.id.calculator_early_status_1_rl);
		earlyback_status_img[0] = (ImageView) findViewById(R.id.calculator_early_status_0_img);
		earlyback_status_img[1] = (ImageView) findViewById(R.id.calculator_early_status_1_img);
		earlyback_status_tv[0] = (TextView) findViewById(R.id.calculator_early_status_0_tv);
		earlyback_status_tv[1] = (TextView) findViewById(R.id.calculator_early_status_1_tv);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.calculator_earlyback_cycle_m_rl:
			cycleIndex = 1;
			setChoose(earlyback_cycle_tv, earlyback_cycle_img, 0);
			break;
		case R.id.calculator_earlyback_cycle_s_rl:
			cycleIndex = 3;
			setChoose(earlyback_cycle_tv, earlyback_cycle_img, 1);
			break;
		case R.id.calculator_earlyback_cycle_hy_rl:
			cycleIndex = 6;
			setChoose(earlyback_cycle_tv, earlyback_cycle_img, 2);
			break;
		case R.id.calculator_earlyback_cycle_y_rl:
			cycleIndex = 12;
			setChoose(earlyback_cycle_tv, earlyback_cycle_img, 3);
			break;
		case R.id.calculator_earlyback_backmoney_1_rl:
			backmoneyIndex = 0;
			setChoose(earlyback_backmoney_tv, earlyback_backmoney_img,
					backmoneyIndex);
			break;
		case R.id.calculator_earlyback_backmoney_2_rl:
			backmoneyIndex = 1;
			setChoose(earlyback_backmoney_tv, earlyback_backmoney_img,
					backmoneyIndex);
			break;
		case R.id.calculator_early_theway_0_rl:
			thewayIndex = 0;
			setChoose(earlyback_theway_tv, earlyback_theway_img, thewayIndex);
			break;
		case R.id.calculator_early_theway_1_rl:
			thewayIndex = 1;
			setChoose(earlyback_theway_tv, earlyback_theway_img, thewayIndex);
			break;
		case R.id.calculator_early_status_0_rl:
			statusIndex = 0;
			setChoose(earlyback_status_tv, earlyback_status_img, statusIndex);
			break;
		case R.id.calculator_early_status_1_rl:
			statusIndex = 1;
			setChoose(earlyback_status_tv, earlyback_status_img, statusIndex);
			break;
		case R.id.calculator_earlyback_btn:
			calculator();
			break;
		case R.id.calculator_earlyback_startdate:
			CreateDialog(startDate);
			break;
		case R.id.calculator_earlyback_earlyday:
			CreateDialog(endDate);
			break;
		default:
			break;
		}
	}

	/**
	 * 创建日期及时间选择对话框
	 */
	public void CreateDialog(final int index) {
		Dialog dialog_1 = null;
		dataSet = "";
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CalculatorEarlyCreditActivity.this);
		c = Calendar.getInstance();
		dialog_1 = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						String monthstr = "", daystr = "";
						month = month + 1;
						monthstr = month > 10 ? "" + month : "0" + month;
						daystr = dayOfMonth > 10 ? "" + dayOfMonth : "0"
								+ dayOfMonth;

						dataSet = year + "-" + monthstr + "-" + daystr;
						if (index == 0) {
							earlyback_startdate.setText(dataSet);
						} else if (index == 1) {
							earlyback_earlyday.setText(dataSet);
						}
					}
				}, c.get(Calendar.YEAR), // 传入年份
				c.get(Calendar.MONTH), // 传入月份
				c.get(Calendar.DAY_OF_MONTH) // 传入天数
		);

		dialog_1.show();

	}

	// 进行计算
	private void calculator() {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(earlyback_startdate.getText())
				&& !TextUtils.isEmpty(earlyback_earlyday.getText())) {
			try {
				Date d1 = formatter.parse(earlyback_startdate.getText()
						.toString());
				Date d2 = formatter.parse(earlyback_earlyday.getText()
						.toString());
				dateNum = daysBetween(d1, d2);
				if (dateNum < 0) {

					Toast.makeText(CalculatorEarlyCreditActivity.this,
							R.string.auxiliary_huandai2, Toast.LENGTH_SHORT)
							.show();
					return;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!TextUtils.isEmpty(earlyback_deadline.getText())) {
			iNum[0] = Double.parseDouble(earlyback_deadline.getText()
					.toString());
		} else {
			showToast();
			return;
		}
		if (!TextUtils.isEmpty(earlyback_money.getText())) {
			iNum[1] = Double.parseDouble(earlyback_money.getText().toString());
		} else {
			showToast();
			return;
		}
		if (!TextUtils.isEmpty(earlyback_rate.getText())) {
			iNum[2] = Double.parseDouble(earlyback_rate.getText().toString());
		} else {
			showToast();
			return;
		}

		if (!TextUtils.isEmpty(earlyback_earlymoney.getText())) {
			iNum[3] = Double.parseDouble(earlyback_earlymoney.getText()
					.toString());
		} else {
			showToast();
			return;
		}
		if (backmoneyIndex == 1) {
			oNum[0] = CalculatorFinancialFormula
					.getKPeriodRepaymentOfAverageCapital(iNum[1],
							iNum[2] / 1200, (int) (iNum[0] / cycleIndex), 1);
			if (oNum[0] < 200000) {
				earlyback_returnmoney.setText(numdf.format(oNum[0]) + "元");
			} else {
				earlyback_returnmoney.setText(numdf.format(oNum[0] / 10000) + "万元");
			}
			oNum[1] = CalculatorFinancialFormula
					.getDengEBenJinLeiJiHuanKuanHuanXi(iNum[1], iNum[2]
							* cycleIndex / 1200, (int) (iNum[0] / cycleIndex),
							(int) (iNum[0] / cycleIndex));
			if (oNum[1] < 200000) {
				earlyback_returninterest.setText(numdf.format(oNum[1]) + "元");
			} else {
				earlyback_returninterest.setText(numdf.format(oNum[1] / 10000)
						+ "万元");
			}
			oNum[2] = CalculatorFinancialFormula
					.getPeriodCumulativeRepaymentOfAverageCapital(iNum[1],
							iNum[2] * cycleIndex / 1200,
							(int) (iNum[0] / cycleIndex),
							(int) (iNum[0] / cycleIndex));
			if (oNum[2] < 200000) {
				earlyback_returntotal.setText(numdf.format(oNum[2]) + "元");
			} else {
				earlyback_returntotal.setText(numdf.format(oNum[2] / 10000) + "万元");
			}

		} else if (backmoneyIndex == 0) {

			oNum[0] = CalculatorFinancialFormula
					.getKPeriodRepaymentOfPrincipal(iNum[1], iNum[2]
							* cycleIndex / 1200, (int) (iNum[0] / cycleIndex),
							1);
			if (oNum[0] < 200000) {
				earlyback_returnmoney.setText(numdf.format(oNum[0]) + "元");
			} else {
				earlyback_returnmoney.setText(numdf.format(oNum[0] / 10000) + "万元");
			}
			oNum[1] = CalculatorFinancialFormula.getPeriodAccumulatedInterest(
					iNum[1], iNum[2] * cycleIndex / 1200,
					(int) (iNum[0] / cycleIndex), (int) (iNum[0] / cycleIndex));
			if (oNum[1] < 200000) {
				earlyback_returninterest.setText(numdf.format(oNum[1]) + "元");
			} else {
				earlyback_returninterest.setText(numdf.format(oNum[1] / 10000)
						+ "万元");
			}
			oNum[2] = CalculatorFinancialFormula.getPeriodCumulativeRepayment(
					iNum[1], iNum[2] * cycleIndex / 1200,
					(int) (iNum[0] / cycleIndex), (int) (iNum[0] / cycleIndex));
			if (oNum[2] < 200000) {
				earlyback_returntotal.setText(numdf.format(oNum[2]) + "元");
			} else {
				earlyback_returntotal.setText(numdf.format(oNum[2] / 10000) + "万元");
			}

		}

	}

	private void showToast() {
		Toast.makeText(CalculatorEarlyCreditActivity.this, "数据不能为空！",
				Toast.LENGTH_SHORT).show();
	}

	// 计算时间差
	private int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
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
					theBg[i].setBackgroundColor(this.getResources().getColor(
							R.color.nowworkplacewordbg));
				}

				theCheck[i].setTextColor(this.getResources().getColor(
						R.color.white));
			} else {
				theBg[i].setBackgroundColor(getResources().getColor(
						R.color.transparent));
				theCheck[i].setTextColor(this.getResources().getColor(
						R.color.nowworkplacewordbg));
			}
		}
	}

}
