package com.pactera.financialmanager.credit.common.bean.http;

/**
 * 到期提醒
 * Created by dysen on 2017/8/7.
 */

public class ExpirationReminder {

	/**
	 * InterestBalance : 0
	 * ConTractNo : BC2016111500000743
	 * BusinessName1 : null
	 * CustomerName : 李昌群
	 * CustomerID : 20120320000256
	 * BusinessSum : 200000
	 * Balance : 100000
	 * DueBillNO : 31010000023657795
	 * OfficeTel : 13872577789
	 * ActualMaturity : 2017/11/15
	 */

	private int InterestBalance;
	private String ConTractNo;
	private Object BusinessName1;
	private String CustomerName;
	private String CustomerID;
	private int BusinessSum;
	private int Balance;
	private String DueBillNO;
	private String OfficeTel;
	private String ActualMaturity;

	public int getInterestBalance() {
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

	public Object getBusinessName1() {
		return BusinessName1;
	}

	public void setBusinessName1(Object BusinessName1) {
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

	public int getBusinessSum() {
		return BusinessSum;
	}

	public void setBusinessSum(int BusinessSum) {
		this.BusinessSum = BusinessSum;
	}

	public int getBalance() {
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
