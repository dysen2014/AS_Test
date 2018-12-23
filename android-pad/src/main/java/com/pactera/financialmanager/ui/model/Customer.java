package com.pactera.financialmanager.ui.model;

/**
 * 客户信息一键查询(T000018)
 * @author JAY
 *
 */
public class Customer {
	
	private String custid;// 客户号
	
	private String idvChnNM;// 客户姓名
	
	private String custValueRate;// 客户价值评级
	
	private String custPsnCardType;// 证件类型
	
	private String custPsnCardNumber;// 证件号码
	
	private String phoneNo;// 手机号码
	
	private String proProAmt;// 金融资产总额
	
	private String dueOnDemandAll;// 活期存款余额
	
	private String fixedTimeDepositAll;// 定期存款余额
	
	private String mmmProAmt;// 理财存款余额

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getIdvChnNM() {
		return idvChnNM;
	}

	public void setIdvChnNM(String idvChnNM) {
		this.idvChnNM = idvChnNM;
	}

	public String getCustValueRate() {
		return custValueRate;
	}

	public void setCustValueRate(String custValueRate) {
		this.custValueRate = custValueRate;
	}

	public String getCustPsnCardType() {
		return custPsnCardType;
	}

	public void setCustPsnCardType(String custPsnCardType) {
		this.custPsnCardType = custPsnCardType;
	}

	public String getCustPsnCardNumber() {
		return custPsnCardNumber;
	}

	public void setCustPsnCardNumber(String custPsnCardNumber) {
		this.custPsnCardNumber = custPsnCardNumber;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getProProAmt() {
		return proProAmt;
	}

	public void setProProAmt(String proProAmt) {
		this.proProAmt = proProAmt;
	}

	public String getDueOnDemandAll() {
		return dueOnDemandAll;
	}

	public void setDueOnDemandAll(String dueOnDemandAll) {
		this.dueOnDemandAll = dueOnDemandAll;
	}

	public String getFixedTimeDepositAll() {
		return fixedTimeDepositAll;
	}

	public void setFixedTimeDepositAll(String fixedTimeDepositAll) {
		this.fixedTimeDepositAll = fixedTimeDepositAll;
	}

	public String getMmmProAmt() {
		return mmmProAmt;
	}

	public void setMmmProAmt(String mmmProAmt) {
		this.mmmProAmt = mmmProAmt;
	}
	
}