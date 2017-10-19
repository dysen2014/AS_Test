package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 未来三十天内贷款产品到期（个人客户） 返回的数据的 javabean
 */
public class ProDQDK implements Serializable {

	private static final long serialVersionUID = 2459456135602873396L;

	private String CST_ID; // 客户号
	private String CST_NAME; // 客户名称
	private String LOAN_ACC; // 贷款账号
	private String PROD_NAME; // 产品名称
	private String LOAN_RATE; // 贷款利率
	private String OFFICER; // 第一责任人
	private String EVENT_BAL; // 到期金额
	private String EXPIRE_DT; // 到期日期
	private String ISSUE_DATE; // 发放日期
	private String BRNAME; // 机构名称
	
	public String getISSUE_DATE() {
		return ISSUE_DATE;
	}

	public void setISSUE_DATE(String iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}

	public String getCST_ID() {
		return CST_ID;
	}

	public void setCST_ID(String cST_ID) {
		CST_ID = cST_ID;
	}

	public String getCST_NAME() {
		return CST_NAME;
	}

	public void setCST_NAME(String cST_NAME) {
		CST_NAME = cST_NAME;
	}

	public String getLOAN_ACC() {
		return LOAN_ACC;
	}

	public void setLOAN_ACC(String lOAN_ACC) {
		LOAN_ACC = lOAN_ACC;
	}

	public String getPROD_NAME() {
		return PROD_NAME;
	}

	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}

	public String getLOAN_RATE() {
		return LOAN_RATE;
	}

	public void setLOAN_RATE(String lOAN_RATE) {
		LOAN_RATE = lOAN_RATE;
	}

	public String getOFFICER() {
		return OFFICER;
	}

	public void setOFFICER(String oFFICER) {
		OFFICER = oFFICER;
	}

	public String getEVENT_BAL() {
		return EVENT_BAL;
	}

	public void setEVENT_BAL(String eVENT_BAL) {
		EVENT_BAL = eVENT_BAL;
	}

	public String getEXPIRE_DT() {
		return EXPIRE_DT;
	}

	public void setEXPIRE_DT(String eXPIRE_DT) {
		EXPIRE_DT = eXPIRE_DT;
	}

	public String getBRNAME() {
		return BRNAME;
	}

	public void setBRNAME(String bRNAME) {
		BRNAME = bRNAME;
	}

	public ProDQDK(String cST_ID, String cST_NAME, String lOAN_ACC,
			String pROD_NAME, String lOAN_RATE, String oFFICER,
			String eVENT_BAL, String eXPIRE_DT, String bRNAME, String iSSUE_DATE) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		LOAN_ACC = lOAN_ACC;
		PROD_NAME = pROD_NAME;
		LOAN_RATE = lOAN_RATE;
		OFFICER = oFFICER;
		EVENT_BAL = eVENT_BAL;
		EXPIRE_DT = eXPIRE_DT;
		BRNAME = bRNAME;
		ISSUE_DATE = iSSUE_DATE;
	}

	public ProDQDK() {
		super();
	}

}
