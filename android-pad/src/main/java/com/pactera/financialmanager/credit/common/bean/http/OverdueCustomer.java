package com.pactera.financialmanager.credit.common.bean.http;

/**
 * 逾期详情
 * Created by dysen on 2017/8/4.
 */

public class OverdueCustomer {


	/**
	 * InterestBalance : 16145.05
	 * ConTractNo : BC2015072100000708
	 * BusinessName1 : 商户联保贷款
	 * OverDueDays : 517
	 * CustomerName : 危先容
	 * CustomerID : 2014121600000276
	 * BusinessSum : 100000.0
	 * Balance : 100000.0
	 * OverDueBalance : 116145.05
	 * DueBillNO : 31010000020583427
	 * OfficeTel : 13997670288
	 * ActualMaturity : 2016/07/22
	 */

	private double InterestBalance;
	private String ConTractNo;
	private String BusinessName1;
	private String OverDueDays;
	private String CustomerName;
	private String CustomerID;
	private double BusinessSum;
	private double Balance;
	private String OverDueBalance;
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

	public String getOverDueDays() {
		return OverDueDays;
	}

	public void setOverDueDays(String OverDueDays) {
		this.OverDueDays = OverDueDays;
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

	public String getOverDueBalance() {
		return OverDueBalance;
	}

	public void setOverDueBalance(String OverDueBalance) {
		this.OverDueBalance = OverDueBalance;
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
