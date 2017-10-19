package com.pactera.financialmanager.credit.common.bean.search;

/**
 * Created by dysen on 2017/8/29.
 */

public class LoanAfterBean {

	String SerialNo;//合同流水号,
	String BusinessType;//业务品种编号,
	String ArtificialNo;//合同编号,
	String OccurTypeName;//发生类型,
	String BusinessTypeName;//业务品种名,
	String Currency;//币种,
	String BusinessSum;//合同金额,
	String ActualPutOutSum;//实际发放金额,
	String BailRatio;//保证金比例(%),
	String Balance;//余额,
	String OverdueBalance;//逾期金额,
	String PutOutDate;//起始日期,
	String Maturity;//到期日期,
	String OperateOrgName;//经办机构

	public String getSerialNo() {
		return SerialNo;
	}

	public LoanAfterBean setSerialNo(String serialNo) {
		SerialNo = serialNo;
		return this;
	}

	public String getBusinessType() {
		return BusinessType;
	}

	public LoanAfterBean setBusinessType(String businessType) {
		BusinessType = businessType;
		return this;
	}

	public String getArtificialNo() {
		return ArtificialNo;
	}

	public LoanAfterBean setArtificialNo(String artificialNo) {
		ArtificialNo = artificialNo;
		return this;
	}

	public String getOccurTypeName() {
		return OccurTypeName;
	}

	public LoanAfterBean setOccurTypeName(String occurTypeName) {
		OccurTypeName = occurTypeName;
		return this;
	}

	public String getBusinessTypeName() {
		return BusinessTypeName;
	}

	public LoanAfterBean setBusinessTypeName(String businessTypeName) {
		BusinessTypeName = businessTypeName;
		return this;
	}

	public String getCurrency() {
		return Currency;
	}

	public LoanAfterBean setCurrency(String currency) {
		Currency = currency;
		return this;
	}

	public String getBusinessSum() {
		return BusinessSum;
	}

	public LoanAfterBean setBusinessSum(String businessSum) {
		BusinessSum = businessSum;
		return this;
	}

	public String getActualPutOutSum() {
		return ActualPutOutSum;
	}

	public LoanAfterBean setActualPutOutSum(String actualPutOutSum) {
		ActualPutOutSum = actualPutOutSum;
		return this;
	}

	public String getBailRatio() {
		return BailRatio;
	}

	public LoanAfterBean setBailRatio(String bailRatio) {
		BailRatio = bailRatio;
		return this;
	}

	public String getBalance() {
		return Balance;
	}

	public LoanAfterBean setBalance(String balance) {
		Balance = balance;
		return this;
	}

	public String getOverdueBalance() {
		return OverdueBalance;
	}

	public LoanAfterBean setOverdueBalance(String overdueBalance) {
		OverdueBalance = overdueBalance;
		return this;
	}

	public String getPutOutDate() {
		return PutOutDate;
	}

	public LoanAfterBean setPutOutDate(String putOutDate) {
		PutOutDate = putOutDate;
		return this;
	}

	public String getMaturity() {
		return Maturity;
	}

	public LoanAfterBean setMaturity(String maturity) {
		Maturity = maturity;
		return this;
	}

	public String getOperateOrgName() {
		return OperateOrgName;
	}

	public LoanAfterBean setOperateOrgName(String operateOrgName) {
		OperateOrgName = operateOrgName;
		return this;
	}
}
