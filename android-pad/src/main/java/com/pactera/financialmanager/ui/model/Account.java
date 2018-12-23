package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户冻结
 * @author Administrator
 *
 */
public class Account implements Serializable{

	private static final long serialVersionUID = -1752620417227798601L;

	private String CST_ID; // 客户号
	
	private String CST_NAME; // 客户名称
	
	private String ACC_NO; // 账号
	 
	private String ACC_STATE; // 冻结状态
	
	private String FREEZE_DT; // 冻结日期
	
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

	public String getACC_NO() {
		return ACC_NO;
	}

	public void setACC_NO(String aCC_NO) {
		ACC_NO = aCC_NO;
	}

	public String getACC_STATE() {
		return ACC_STATE;
	}

	public void setACC_STATE(String aCC_STATE) {
		ACC_STATE = aCC_STATE;
	}

	public String getFREEZE_DT() {
		return FREEZE_DT;
	}

	public void setFREEZE_DT(String fREEZE_DT) {
		FREEZE_DT = fREEZE_DT;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public Account() {
		super();
	}

	public Account(String cST_ID, String cST_NAME, String aCC_NO,
			String aCC_STATE, String fREEZE_DT, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		ACC_NO = aCC_NO;
		ACC_STATE = aCC_STATE;
		FREEZE_DT = fREEZE_DT;
		BRID = bRID;
	}
	
}
