package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 未来十天内定期存款到期(个人客户) 返回的数据的javabean
 */
public class ProDQCK implements Serializable {

	private static final long serialVersionUID = -1645173670931815021L;

	private String CST_ID; // 客户号
	
	private String CST_NAME; // 客户名称
	
	private String ACC_DEP_ACC; // 存款账号
	
	private String ACC_DEP_ACC_NAME; // 存款名称
	
	private String EVENT_BAL; // 到期金额
	
	private String EXPIRE_DT; // 到期日期
	
	private String BRID; // 客户所在机构

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

	public String getACC_DEP_ACC() {
		return ACC_DEP_ACC;
	}

	public void setACC_DEP_ACC(String aCC_DEP_ACC) {
		ACC_DEP_ACC = aCC_DEP_ACC;
	}

	public String getACC_DEP_ACC_NAME() {
		return ACC_DEP_ACC_NAME;
	}

	public void setACC_DEP_ACC_NAME(String aCC_DEP_ACC_NAME) {
		ACC_DEP_ACC_NAME = aCC_DEP_ACC_NAME;
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

	public ProDQCK(String cST_ID, String cST_NAME, String aCC_DEP_ACC,
			String aCC_DEP_ACC_NAME, String eVENT_BAL, String eXPIRE_DT,
			String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		ACC_DEP_ACC = aCC_DEP_ACC;
		ACC_DEP_ACC_NAME = aCC_DEP_ACC_NAME;
		EVENT_BAL = eVENT_BAL;
		EXPIRE_DT = eXPIRE_DT;
		BRID = bRID;
	}

	public ProDQCK() {
		super();
	}

}
