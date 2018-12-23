package com.pactera.financialmanager.credit.common.bean.http;

/**
 * 到期提醒
 * Created by dysen on 2017/8/7.
 */

public class ExpirationReminder {

	/**
	 * InterestBalance2 : 0.00
	 * ClassifyResult : 正常
	 * CustomerName : 张飞
	 * BusinessName : 个人经营性综合贷款
	 * BusinessSum : 160,000.00
	 * ActualBusinessRate : 9.35%
	 * OrgName : 秭归农村商业银行营业部
	 * BalanceSum : 0.00
	 * UserName : 郭桂元
	 * ActualPutOutDate : 2017/01/23
	 * Balance : 160,000.00
	 * OverDueBalance : 0.00
	 * InterestBalance1 : 0.00
	 * ActualMaturity : 2017/12/23
	 * OfficeTel : 13997677058
	 */

	private String InterestBalance2;
	private String ClassifyResult;
	private String CustomerName;
	private String BusinessName;
	private String BusinessSum;
	private String ActualBusinessRate;
	private String OrgName;
	private String BalanceSum;
	private String UserName;
	private String ActualPutOutDate;
	private String Balance;
	private String OverDueBalance;
	private String InterestBalance1;
	private String ActualMaturity;
	private String OfficeTel;

	public ExpirationReminder(String interestBalance2, String classifyResult, String customerName, String businessName, String businessSum, String actualBusinessRate, String orgName, String balanceSum, String userName, String actualPutOutDate, String balance, String overDueBalance, String interestBalance1, String actualMaturity, String officeTel) {
		InterestBalance2 = interestBalance2;
		ClassifyResult = classifyResult;
		CustomerName = customerName;
		BusinessName = businessName;
		BusinessSum = businessSum;
		ActualBusinessRate = actualBusinessRate;
		OrgName = orgName;
		BalanceSum = balanceSum;
		UserName = userName;
		ActualPutOutDate = actualPutOutDate;
		Balance = balance;
		OverDueBalance = overDueBalance;
		InterestBalance1 = interestBalance1;
		ActualMaturity = actualMaturity;
		OfficeTel = officeTel;
	}

	public String getInterestBalance2() {
		return InterestBalance2;
	}

	public void setInterestBalance2(String InterestBalance2) {
		this.InterestBalance2 = InterestBalance2;
	}

	public String getClassifyResult() {
		return ClassifyResult;
	}

	public void setClassifyResult(String ClassifyResult) {
		this.ClassifyResult = ClassifyResult;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String CustomerName) {
		this.CustomerName = CustomerName;
	}

	public String getBusinessName() {
		return BusinessName;
	}

	public void setBusinessName(String BusinessName) {
		this.BusinessName = BusinessName;
	}

	public String getBusinessSum() {
		return BusinessSum;
	}

	public void setBusinessSum(String BusinessSum) {
		this.BusinessSum = BusinessSum;
	}

	public String getActualBusinessRate() {
		return ActualBusinessRate;
	}

	public void setActualBusinessRate(String ActualBusinessRate) {
		this.ActualBusinessRate = ActualBusinessRate;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String OrgName) {
		this.OrgName = OrgName;
	}

	public String getBalanceSum() {
		return BalanceSum;
	}

	public void setBalanceSum(String BalanceSum) {
		this.BalanceSum = BalanceSum;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getActualPutOutDate() {
		return ActualPutOutDate;
	}

	public void setActualPutOutDate(String ActualPutOutDate) {
		this.ActualPutOutDate = ActualPutOutDate;
	}

	public String getBalance() {
		return Balance;
	}

	public void setBalance(String Balance) {
		this.Balance = Balance;
	}

	public String getOverDueBalance() {
		return OverDueBalance;
	}

	public void setOverDueBalance(String OverDueBalance) {
		this.OverDueBalance = OverDueBalance;
	}

	public String getInterestBalance1() {
		return InterestBalance1;
	}

	public void setInterestBalance1(String InterestBalance1) {
		this.InterestBalance1 = InterestBalance1;
	}

	public String getActualMaturity() {
		return ActualMaturity;
	}

	public void setActualMaturity(String ActualMaturity) {
		this.ActualMaturity = ActualMaturity;
	}

	public String getOfficeTel() {
		return OfficeTel;
	}

	public void setOfficeTel(String OfficeTel) {
		this.OfficeTel = OfficeTel;
	}
}
