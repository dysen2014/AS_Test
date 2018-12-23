package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 本行存款返回的数据保存的javabean
 */
public class OwnBankMoneyEntity implements Serializable {
	private String PKID;//主键
	private String DEPOSITTYPE;//存款类型
	private String DEPOSIT_AMOUNT;//存款金额
	private String DEPOSIT_PERIOD;//存款期限
	private String CREATE_USER;//创建人
	private String CREATE_DATE;//创建日期
	public String getPKID() {
		return PKID;
	}
	public void setPKID(String pKID) {
		PKID = pKID;
	}
	public String getDEPOSITTYPE() {
		return DEPOSITTYPE;
	}
	public void setDEPOSITTYPE(String dEPOSITTYPE) {
		DEPOSITTYPE = dEPOSITTYPE;
	}
	public String getDEPOSIT_AMOUNT() {
		return DEPOSIT_AMOUNT;
	}
	public void setDEPOSIT_AMOUNT(String dEPOSIT_AMOUNT) {
		DEPOSIT_AMOUNT = dEPOSIT_AMOUNT;
	}
	public String getDEPOSIT_PERIOD() {
		return DEPOSIT_PERIOD;
	}
	public void setDEPOSIT_PERIOD(String dEPOSIT_PERIOD) {
		DEPOSIT_PERIOD = dEPOSIT_PERIOD;
	}
	public String getCREATE_USER() {
		return CREATE_USER;
	}
	public void setCREATE_USER(String cREATE_USER) {
		CREATE_USER = cREATE_USER;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	
	public OwnBankMoneyEntity(String pKID, String dEPOSITTYPE,
			String dEPOSIT_AMOUNT, String dEPOSIT_PERIOD, String cREATE_USER,
			String cREATE_DATE) {
		super();
		PKID = pKID;
		DEPOSITTYPE = dEPOSITTYPE;
		DEPOSIT_AMOUNT = dEPOSIT_AMOUNT;
		DEPOSIT_PERIOD = dEPOSIT_PERIOD;
		CREATE_USER = cREATE_USER;
		CREATE_DATE = cREATE_DATE;
	}
	
	public OwnBankMoneyEntity() {
		super();
	}
	
	@Override
	public String toString() {
		return "OwnBankMoneyEntity [PKID=" + PKID + ", DEPOSITTYPE="
				+ DEPOSITTYPE + ", DEPOSIT_AMOUNT=" + DEPOSIT_AMOUNT
				+ ", DEPOSIT_PERIOD=" + DEPOSIT_PERIOD + ", CREATE_USER="
				+ CREATE_USER + ", CREATE_DATE=" + CREATE_DATE + "]";
	}

}
