package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 未来三十天内承兑汇票（对公客户） 返回的数据的 javabean
 * @author Administrator
 *
 */
public class ProCHDP implements Serializable {

	private static final long serialVersionUID = 8775616023090570870L;
	
	private String CST_ID;  //客户号
	private String CST_NAME;  //客户名称
	private String PAGE_NUMBER;  //票据号码
	private String DRAWER_DT;  //出票日期
	private String EVENT_BAL;  //到期金额
	private String EXPIRE_DT;  //到期时间
	private String DEPOSIT_AMOUNT;  //保证金金额
	private String DEPOSIT_SCALE;  //保证金比例
	private String PAYEE_NAME;  //收款人姓名
	private String BRID; //客户所在机构
	
	
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

	public String getPAGE_NUMBER() {
		return PAGE_NUMBER;
	}

	public void setPAGE_NUMBER(String pAGE_NUMBER) {
		PAGE_NUMBER = pAGE_NUMBER;
	}

	public String getDRAWER_DT() {
		return DRAWER_DT;
	}

	public void setDRAWER_DT(String dRAWER_DT) {
		DRAWER_DT = dRAWER_DT;
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

	public String getDEPOSIT_AMOUNT() {
		return DEPOSIT_AMOUNT;
	}

	public void setDEPOSIT_AMOUNT(String dEPOSIT_AMOUNT) {
		DEPOSIT_AMOUNT = dEPOSIT_AMOUNT;
	}

	public String getDEPOSIT_SCALE() {
		return DEPOSIT_SCALE;
	}

	public void setDEPOSIT_SCALE(String dEPOSIT_SCALE) {
		DEPOSIT_SCALE = dEPOSIT_SCALE;
	}

	public String getPAYEE_NAME() {
		return PAYEE_NAME;
	}

	public void setPAYEE_NAME(String pAYEE_NAME) {
		PAYEE_NAME = pAYEE_NAME;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	
	public ProCHDP() {
		super();
	}
	
	public ProCHDP(String cST_ID, String cST_NAME,
			String pAGE_NUMBER, String dRAWER_DT, String eVENT_BAL,
			String eXPIRE_DT, String dEPOSIT_AMOUNT, String dEPOSIT_SCALE,
			String pAYEE_NAME, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		PAGE_NUMBER = pAGE_NUMBER;
		DRAWER_DT = dRAWER_DT;
		EVENT_BAL = eVENT_BAL;
		EXPIRE_DT = eXPIRE_DT;
		DEPOSIT_AMOUNT = dEPOSIT_AMOUNT;
		DEPOSIT_SCALE = dEPOSIT_SCALE;
		PAYEE_NAME = pAYEE_NAME;
		BRID = bRID;
	}

	@Override
	public String toString() {
		return "ProDingdaikuandaoqi [CST_ID=" + CST_ID 
				+ ", CST_NAME=" + CST_NAME 
				+ ", PAGE_NUMBER=" + PAGE_NUMBER 
				+ ", DRAWER_DT=" + DRAWER_DT 
				+ ", EVENT_BAL=" + EVENT_BAL 
				+ ", EXPIRE_DT=" + EXPIRE_DT 
				+ ", DEPOSIT_AMOUNT=" + DEPOSIT_AMOUNT
				+ ", DEPOSIT_SCALE=" + DEPOSIT_SCALE 
				+ ", PAYEE_NAME=" + PAYEE_NAME 
				+ ", BRID=" + BRID 
				+ "]";
	}
}
