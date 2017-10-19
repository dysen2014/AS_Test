package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 未来十天内理财产品到期（个人客户） 返回的数据的 javabean
 */
public class ProDQLC implements Serializable {

	private static final long serialVersionUID = -4723588924837517314L;
	
	private String CST_ID;  //客户号
	private String CST_NAME;  //客户名称
	private String PROD_NAME ;  //产品名称
	private String EXPECTED_YIELD ;  //预期收益率
	private String EVENT_BAL;  //到期金额
	private String EXPIRE_DT;  //到期日期 
	private String BRID;  //客户所在机构
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
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getEXPECTED_YIELD() {
		return EXPECTED_YIELD;
	}
	public void setEXPECTED_YIELD(String eXPECTED_YIELD) {
		EXPECTED_YIELD = eXPECTED_YIELD;
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
	public String getBRID() {
		return BRID;
	}
	public void setBRID(String bRID) {
		BRID = bRID;
	}
	
	public ProDQLC(String cST_ID, String cST_NAME,
			String pROD_NAME, String eXPECTED_YIELD, String eVENT_BAL,
			String eXPIRE_DT, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		PROD_NAME = pROD_NAME;
		EXPECTED_YIELD = eXPECTED_YIELD;
		EVENT_BAL = eVENT_BAL;
		EXPIRE_DT = eXPIRE_DT;
		BRID = bRID;
	}
	
	public ProDQLC() {
		super();
	}
	
	@Override
	public String toString() {
		return "ProDingqilicaidaoqi [CST_ID=" + CST_ID + ", CST_NAME="
				+ CST_NAME + ", PROD_NAME=" + PROD_NAME + ", EXPECTED_YIELD="
				+ EXPECTED_YIELD + ", EVENT_BAL=" + EVENT_BAL + ", EXPIRE_DT="
				+ EXPIRE_DT + ", BRID=" + BRID + "]";
	}
}
