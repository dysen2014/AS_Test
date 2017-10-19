package com.pactera.financialmanager.util;

/**
 * 
 * @author Miles 理财计算器公式集锦
 */
public class CalculatorFinancialFormula {
	// 复利终值计算方法
	public static double getDuoQiFuLiZhongZhi(double x, double i, double n) {
		double cash = x * Math.pow((1 + i/100), n);

		return cash;
	}

	// 多期现值复利计算
	public static double getCompoundInterestPV(double z, double i, double n) {
		double cash = z / Math.pow((1 + i), n);

		return cash;

	};

	// 多期期数复利计算
	public static double getDuoQiFuLiQiShu(double x, double z, double i) {
		double cash = Math.log(z / x) / Math.log(1 + i);

		return cash;

	};

	// 多期利息复利计算
	public static double getDuoQiLiXiLv(double x, double z, double n) {
		double cash = Math.pow(z / x, 1 / n) - 1;

		return cash;

	};

	// 非整年复利算法
	public static double getCompoundInterestNotYearRoundFVN(double PV,
			double i, int n, int m) {

		double cash = PV * Math.pow((1 + i / m), n * m);

		return cash;
	}

	// 年金终值算法
	public static double getAnnuityFVN(double PMT, double i, double n) {

		double cash = PMT * ((Math.pow((1 + i), n) - 1) / i);

		return cash;
	}

	// 即期年金终值算法
	public static double getImmediateAnnuityFVDN(double PMT, double i, int n) {

		double cash = PMT * ((Math.pow((1 + i), n) - 1) / i) * (i + 1);

		return cash;
	}

	// 年金现值算法
	public static double getPresentAnnuityPV(double PMT, double i, double n) {

		double cash = PMT * (1 / i) * (1 - 1 / (Math.pow(1 + i, n)));

		return cash;
	}

	// 终值 利率 期数 求年金算法
	public static double getZhZhiLiLvQiShuSeekNianJin(double z, double i,
			double n) {

		double cash = z * i / (Math.pow(i + 1, n) - 1);

		return cash;
	}

	// 现值、利率、期数 求年金算法
	public static double getXianZhiLiLvQiShuSeekNianJin(double x, double i,
			double n) {

		double cash = (x * i) / (1 - (1 / Math.pow(i + 1, n)));

		return cash;
	}
	// 现值、年金、利息率 求年金期数
		public static double getXianNianJinLiXiLvSeekQiShu(double x, double pmt,
				double i) {
			if ((pmt - x * i) > 0) {
				double n = Math.log(pmt / (pmt - x * i)) / Math.log(1 + i);

				return n;
			} else {
				
				return -1;
			}

		}

	// 即期年金终值算法
	public static double getImmediatePresentAnnuityPV(double PMT, double i,
			int n) {

		double cash = PMT * (1 / i) * (1 - 1 / (Math.pow(1 + i, n))) * (1 + i);

		return cash;
	}

	// 求年金利息率
	public static double getXianZhiNianJinQiShuSeekLiXiLv(double x, double pmt,
			double n) {
		double i = 0;

		while (Math.abs(i - (Math.pow(pmt / (pmt - x * i/100), 1 / n) - 1)) < 0.001) {
			i += 0.00000001;
		}

		return i;
	}

	// 等额本息还款法-分期还款额
	public static double getInstalmentPayment(double Y, double i, int n) {

		double cash = Y * i * (Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1));

		return cash;
	}

	// 等额本息还款法-贷款余额
	public static double getBalanceLoans(double Y, double i, int n, int k) {

		double cash = Y * (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(1 + i, n) - 1);

		return cash;
	}

	// 等额本息还款法-k期还款额
	public static double getKPeriodRepaymentOfPrincipal(double Y, double i,
			int n, int k) {

		double cash = Y * (Math.pow(1 + i/100, k) - Math.pow(1 + i/100, k - 1))
				/ (Math.pow(1 + i/100, n) - 1);

		return cash;
	}

	// 到期还本付息法
	public static double getDK_dqhbfx(double Y, double i, int n) {

		double cash = Y * Math.pow(1 + i/100, n);

		return cash;
	}

	// 等额本息还款法-k期还息额
	public static double getKPeriodAlsoOnTheAmountOf(double Y, double i, int n,
			int k) {

		double cash = Y * (Math.pow(1 + i, k) - Math.pow(1 + i, k - 1))
				/ (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本息还款法-累计还款
	public static double getPeriodCumulativeRepayment(double Y, double i,
			int n, int k) {

		double cash = Y * i/100 * k * (Math.pow(1 + i/100, n))
				/ (Math.pow(1 + i/100, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本息还款法-累计还息
	public static double getPeriodAccumulatedInterest(double Y, double i,
			int n, int k) {

		double cash = Y
				* ((k * i * Math.pow(i/100 + 1, n)) - (Math.pow(i/100 + 1, k) - 1))
				/ (Math.pow(i/100 + 1, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本金还款法-k期还息额
	public static double getKPeriodAlsoOnTheAmountOfAverageCapital(double Y,
			double i, int n, int k) {

		double cash = (1 - (k - 1) / n) * Y * i;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本金还款法-k期还款额
	public static double getKPeriodRepaymentOfAverageCapital(double Y,
			double i, int n, int k) {

		double cash = Y / n + (1 - (k - 1) / n) * Y * i/100;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本金还款法-贷款余额
	public static double getTheBalanceOfLoansOfAverageCapital(double Y,
			double i, int n, int k) {

		double cash = (1 - k / n) * Y;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本金还款法-累计还款
	public static double getPeriodCumulativeRepaymentOfAverageCapital(double Y,
			double i, int n, int k) {

		double cash = Y * k / n + (k - (k - 1) * k / (2 * n)) * Y * i/100;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 等额本金还款法-累计还息
	public static double getDengEBenJinLeiJiHuanKuanHuanXi(double Y, double i,
			int n, int k) {

		double cash = (k - (k - 1) * k / (2 * n)) * Y * i/100;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 提前还款计算器-提前还款前每期所还金额
	public static double getTiQianHuanKuanQianMeiQiSuoHuanJinE(double Y,
			double i, int n) {

		double cash = Y * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 提前还款计算器-提前还款时已还款总额
	public static double getTiQianHuanKuanShiYiHuanKuanZongE(double Y,
			double i, int n, int k) {

		double cash = Y * k * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 提前还款计算器-为一次提前还清方式-提前还款总金额
	public static double getWeiYiCiHuanQingFangShiTiQianHuanKuanZongJinE(
			double Y, double i, int n, int k) {

		double cash = Y * (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 提前还款计算器-为一次提前还清方式-累计还款总金额
	public static double getWeiYiCiHuanQingFangShiLeiJiHuanKuanZongJinE(
			double Y, double i, int n, int k) {

		double cash = Y * k * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1)
				+ Y * (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 提前还款计算器-为一次提前还清方式-累计付息
	public static double getWeiYiCiHuanQingFangShiLeiJiFuXi(double Y, double i,
			int n, int k) {

		double cash = Y * k * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1)
				+ Y * (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(1 + i, n) - 1) - Y;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-贷款购房每年还款额
	public static double getDaiKuanGouFangMeiNianHuanKuanE(double Y, double i,
			int n, int k) {

		double cash = Y * i * Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-K年后房屋价值
	public static double getKNianHouFangWuJiaZhi(double S, double j, int k) {

		double cash = S * Math.pow(1 + j, k);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-贷款余额
	public static double getMaiFangDaiKuanYuE(double Y, double i, int n, int k) {

		double cash = Y * (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(i + 1, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-资产价值
	public static double getMaiFangZiChanJiaZhi(double S, double Y, double i,
			double j, int n, int k) {

		double cash = S * (1 + j) - Y
				* (Math.pow(1 + i, n) - Math.pow(1 + i, k))
				/ (Math.pow(i + 1, n) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-租房首付款价值
	public static double getZuFangShouFuKuanJiaZhi(double M, double y, int k) {

		double cash = M * Math.pow(1 + y, k);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-房租价值
	public static double getZuFangFangZuJiaZhi(double m, double i, double k) {

		double cash = 12 * m * (Math.pow(1 + i, k) - 1) / i;

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-房租月还款价值
	public static double getZuFangYueHuanKuanJiaZhi(double Y, double i, int n,
			int k) {

		double cash = Y * (Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1))
				* (Math.pow(1 + i, k) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}

	// 买房租房比较器-租房资产价值
	public static double getZuFangZiChanJiaZhi(double M, double m, double Y,
			double y, double i, int n, int k) {

		double cash = M * (1 + y) + 12 * m * (Math.pow(1 + i, k) - 1) / i + Y
				* (Math.pow(1 + i, n) / (Math.pow(1 + i, n) - 1))
				* (Math.pow(1 + i, k) - 1);

		cash = Math.round(cash * 10 / 10.0);

		return cash;
	}
}
