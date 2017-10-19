package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 逾期实体类
 * @author Administrator
 *
 */
public class Overdue implements Serializable{

	private static final long serialVersionUID = -7255089935694178259L;

	private String CST_ID;  // 客户号

	private String CST_NAME; // 客户名称

	private String LOAN_ACC; // 贷款账户  

	private String PROD_NAME; // 产品名称 

	private String OVERDUE_AMOUNT; // 逾期金额

	private String OVERDUE_DT; // 逾期时间 

	private String BRID; // 客户所在机构

	private String CREDIT_CARD; // 信用卡卡号
	
	private String BRNAME;	 // 主办机构

	public String getCST_ID() {
		return CST_ID;
	}

	public void setCST_ID(String cST_ID) {
		CST_ID = cST_ID;
	}

	public String getCST_NAME() {
		return CST_NAME;
	}

	public String getBRNAME() {
		return BRNAME;
	}

	public void setBRNAME(String bRNAME) {
		BRNAME = bRNAME;
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

	public String getOVERDUE_AMOUNT() {
		return OVERDUE_AMOUNT;
	}

	public void setOVERDUE_AMOUNT(String oVERDUE_AMOUNT) {
		OVERDUE_AMOUNT = oVERDUE_AMOUNT;
	}

	public String getOVERDUE_DT() {
		return OVERDUE_DT;
	}

	public void setOVERDUE_DT(String oVERDUE_DT) {
		OVERDUE_DT = oVERDUE_DT;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public String getCREDIT_CARD() {
		return CREDIT_CARD;
	}

	public void setCREDIT_CARD(String cREDIT_CARD) {
		CREDIT_CARD = cREDIT_CARD;
	}

	public Overdue() {
		super();
	}

	public Overdue(String cST_ID, String cST_NAME, String lOAN_ACC,
			String pROD_NAME, String oVERDUE_AMOUNT, String oVERDUE_DT,
			String bRID, String cREDIT_CARD, String bRNAME) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		LOAN_ACC = lOAN_ACC;
		PROD_NAME = pROD_NAME;
		OVERDUE_AMOUNT = oVERDUE_AMOUNT;
		OVERDUE_DT = oVERDUE_DT;
		BRID = bRID;
		CREDIT_CARD = cREDIT_CARD;
		BRNAME = bRNAME;
	}
	
	
}
