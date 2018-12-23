package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 大额资金
 * @author Administrator
 *
 */
public class BigFund implements Serializable{

	private static final long serialVersionUID = 2575224416033968162L;

	public String getTRADE_DT() {
		return TRADE_DT;
	}

	public void setTRADE_DT(String tRADE_DT) {
		TRADE_DT = tRADE_DT;
	}

	private String CST_ID; // 客户号
	
	private String CST_NAME; // 客户名称
	
	private String ACC_NO; // 账号
	
	private String TRANS_TYPE; // 异动类型 01 转出 02 转入  
	 
	private String BAL; // 金额 
	
	private String BRID; // 客户所在机构 
	
	private String TRADE_DT; // 异动时间

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

	public String getTRANS_TYPE() {
		return TRANS_TYPE;
	}

	public void setTRANS_TYPE(String tRANS_TYPE) {
		TRANS_TYPE = tRANS_TYPE;
	}

	public String getBAL() {
		return BAL;
	}

	public void setBAL(String bAL) {
		BAL = bAL;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public BigFund() {
		super();
	}

	public BigFund(String cST_ID, String cST_NAME, String aCC_NO,
			String tRANS_TYPE, String bAL, String bRID, String tRADE_DT) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		ACC_NO = aCC_NO;
		TRANS_TYPE = tRANS_TYPE;
		BAL = bAL;
		BRID = bRID;
		TRADE_DT = tRADE_DT;
	}
}
