package com.pactera.financialmanager.credit.common.bean.http;

/**
 * 逾期详情
 * Created by dysen on 2017/8/4.
 */

public class OverdueCustomer {

	/**
	 * InterestBalance : 8002.05
	 * ConTractNo : BC2016012100000190
	 * BusinessName1 : 农村经营大户综合贷款
	 * CustomerName : 测试用户F2200601164026
	 * CustomerID : F2200601164026
	 * BusinessType : 308040
	 * BusinessSum : 500000
	 * Balance : 500000
	 * OverDueBalance : 508002.05
	 * DueBillNO : 31010000021518050
	 * OfficeTel : null
	 * ActualMaturity : 2017/01/21
	 */

	private double InterestBalance;
	private String ConTractNo;
	private String BusinessName1;
	private String CustomerName;
	private String CustomerID;
	private String BusinessType;
	private double BusinessSum;
	private double Balance;
	private String OverDueBalance;
	private String DueBillNO;
	private Object OfficeTel;
	private String ActualMaturity;

	public OverdueCustomer(double interestBalance, String conTractNo, String businessName1, String customerName, String customerID, String businessType, double businessSum, double balance, String overDueBalance, String dueBillNO, Object officeTel, String actualMaturity) {
		InterestBalance = interestBalance;
		ConTractNo = conTractNo;
		BusinessName1 = businessName1;
		CustomerName = customerName;
		CustomerID = customerID;
		BusinessType = businessType;
		BusinessSum = businessSum;
		Balance = balance;
		OverDueBalance = overDueBalance;
		DueBillNO = dueBillNO;
		OfficeTel = officeTel;
		ActualMaturity = actualMaturity;
	}

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

	public Object getOfficeTel() {
		return OfficeTel;
	}

	public void setOfficeTel(Object OfficeTel) {
		this.OfficeTel = OfficeTel;
	}

	public String getActualMaturity() {
		return ActualMaturity;
	}

	public void setActualMaturity(String ActualMaturity) {
		this.ActualMaturity = ActualMaturity;
	}
}
