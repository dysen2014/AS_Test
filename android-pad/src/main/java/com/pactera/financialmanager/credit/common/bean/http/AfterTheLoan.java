package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/8/4.
 * 贷后提醒参数
 */

public class AfterTheLoan {


	/**
	 * InterestBalance : 0.0
	 * ConTractNo : BC2017102500001376
	 * BusinessName1 : 个人经营性综合贷款
	 * CustomerName : 向永斌
	 * CustomerID : 20130326001280
	 * BusinessSum : 150000.0
	 * Balance : 130000.0
	 * DueBillNO : 31010000026994895
	 * OfficeTel : 15872542366
	 * ActualMaturity : 2018/04/07
	 */

	private double InterestBalance;
	private String ConTractNo;
	private String BusinessName1;
	private String CustomerName;
	private String CustomerID;
	private double BusinessSum;
	private double Balance;
	private String DueBillNO;
	private String OfficeTel;
	private String ActualMaturity;

	public double getInterestBalance() {
		return InterestBalance;
	}

	public void setInterestBalance(double InterestBalance) {
		this.InterestBalance = InterestBalance;
	}

	public String getConTractNo() {
		return ConTractNo;
	}

	public void setConTractNo(String ConTractNo) {
		this.ConTractNo = ConTractNo;
	}

	public String getBusinessName1() {
		return BusinessName1;
	}

	public void setBusinessName1(String BusinessName1) {
		this.BusinessName1 = BusinessName1;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String CustomerName) {
		this.CustomerName = CustomerName;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String CustomerID) {
		this.CustomerID = CustomerID;
	}

	public double getBusinessSum() {
		return BusinessSum;
	}

	public void setBusinessSum(double BusinessSum) {
		this.BusinessSum = BusinessSum;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double Balance) {
		this.Balance = Balance;
	}

	public String getDueBillNO() {
		return DueBillNO;
	}

	public void setDueBillNO(String DueBillNO) {
		this.DueBillNO = DueBillNO;
	}

	public String getOfficeTel() {
		return OfficeTel;
	}

	public void setOfficeTel(String OfficeTel) {
		this.OfficeTel = OfficeTel;
	}

	public String getActualMaturity() {
		return ActualMaturity;
	}

	public void setActualMaturity(String ActualMaturity) {
		this.ActualMaturity = ActualMaturity;
	}
}
