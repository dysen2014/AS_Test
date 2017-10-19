package com.pactera.financialmanager.credit.common.bean.http;

/**
 * 到期提醒
 * Created by dysen on 2017/8/7.
 */

public class ExpirationReminder {


	/**
	 * InterestBalance : 0
	 * ConTractNo : BC2016032300001127
	 * BusinessName1 : 一般流动资金贷款
	 * CustomerName : 测试用户2014031400000802
	 * CustomerID : 2014031400000802
	 * BusinessType : 1010
	 * BusinessSum : 16000000
	 * Balance : 16000000
	 * DueBillNO : 35010000001898610
	 * OfficeTel : 13872146366
	 * ActualMaturity : 2017/03/24
	 */

	private double InterestBalance;
	private String ConTractNo;
	private String BusinessName1;
	private String CustomerName;
	private String CustomerID;
	private String BusinessType;
	private double BusinessSum;
	private double Balance;
	private String DueBillNO;
	private String OfficeTel;
	private String ActualMaturity;

	public double getInterestBalance() {
		return InterestBalance;
	}

	public void setInterestBalance(int InterestBalance) {
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

	public String getBusinessType() {
		return BusinessType;
	}

	public void setBusinessType(String BusinessType) {
		this.BusinessType = BusinessType;
	}

	public double getBusinessSum() {
		return BusinessSum;
	}

	public void setBusinessSum(int BusinessSum) {
		this.BusinessSum = BusinessSum;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(int Balance) {
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
