package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户生日
 * @author Administrator
 *
 */
public class CustomerBirthday implements Serializable{

	private static final long serialVersionUID = -117832221612474294L;

	private String CST_ID; // 客户号
	 
	private String CST_NAME; // 客户名称
	
	private String BIRTHDAY; // 出生年月
	
	private String CUST_LEVEL; // 客户价值评级
	
	private String DEPSOIT_BAL; // 客户金融资产余额 
	
	private String BRID; // 客户所在机构
	
	private String LEGALNAME; // 法人姓名

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

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}

	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}

	public String getDEPSOIT_BAL() {
		return DEPSOIT_BAL;
	}

	public void setDEPSOIT_BAL(String dEPSOIT_BAL) {
		DEPSOIT_BAL = dEPSOIT_BAL;
	}

	public String getBRID() {
		return BRID;
	}

	public String getLEGALNAME() {
		return LEGALNAME;
	}

	public void setLEGALNAME(String lEGALNAME) {
		LEGALNAME = lEGALNAME;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public CustomerBirthday() {
		super();
	}

	public CustomerBirthday(String cST_ID, String cST_NAME, String bIRTHDAY,
			String cUST_LEVEL, String dEPSOIT_BAL, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		BIRTHDAY = bIRTHDAY;
		CUST_LEVEL = cUST_LEVEL;
		DEPSOIT_BAL = dEPSOIT_BAL;
		BRID = bRID;
	}
	
}
