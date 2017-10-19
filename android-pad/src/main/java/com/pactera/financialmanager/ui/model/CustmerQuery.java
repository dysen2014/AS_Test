package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户查询
 * @author Administrator
 *
 */
public class CustmerQuery implements Serializable{

	private static final long serialVersionUID = -8618311326216373734L;

	private String BR;

	private String CUSTID; // 客户号
	
	private String CUST_CODE;// 核心客户号
	
	private String CUSTNAME; // 客户姓名
	
	private String CUST_PSN_CARD_TYPE; // 证件类型-个人
	
	private String CUST_PSN_CARD_NUMBER; // 证件号-个人
	
	private String PHONE_NO; // 手机号码
	
	private String CUST_CARD_NUMBER;	// 证件号码-对公
	
	private String CUST_CARD_TYPE; // 证件类型-对公

	public String getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(String cUSTID) {
		CUSTID = cUSTID;
	}

	public String getCUSTNAME() {
		return CUSTNAME;
	}

	public void setCUSTNAME(String cUSTNAME) {
		CUSTNAME = cUSTNAME;
	}

	public String getCUST_PSN_CARD_TYPE() {
		return CUST_PSN_CARD_TYPE;
	}

	public String getCUST_CODE() {
		return CUST_CODE;
	}

	public void setCUST_CODE(String cUST_CODE) {
		CUST_CODE = cUST_CODE;
	}

	public void setCUST_PSN_CARD_TYPE(String cUST_PSN_CARD_TYPE) {
		CUST_PSN_CARD_TYPE = cUST_PSN_CARD_TYPE;
	}

	public String getCUST_PSN_CARD_NUMBER() {
		return CUST_PSN_CARD_NUMBER;
	}

	public void setCUST_PSN_CARD_NUMBER(String cUST_PSN_CARD_NUMBER) {
		CUST_PSN_CARD_NUMBER = cUST_PSN_CARD_NUMBER;
	}

	public String getPHONE_NO() {
		return PHONE_NO;
	}

	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}

	public String getBR() {
		return BR;
	}

	public void setBR(String bR) {
		BR = bR;
	}

	public String getCUST_CARD_NUMBER() {
		return CUST_CARD_NUMBER;
	}

	public void setCUST_CARD_NUMBER(String cUST_CARD_NUMBER) {
		CUST_CARD_NUMBER = cUST_CARD_NUMBER;
	}

	public String getCUST_CARD_TYPE() {
		return CUST_CARD_TYPE;
	}

	public void setCUST_CARD_TYPE(String cUST_CARD_TYPE) {
		CUST_CARD_TYPE = cUST_CARD_TYPE;
	}

	public CustmerQuery(String bR, String cUSTID, String cUSTNAME,
			String cUST_PSN_CARD_TYPE, String cUST_PSN_CARD_NUMBER,
			String pHONE_NO, String cUST_CARD_NUMBER, String cUST_CARD_TYPE) {
		super();
		BR = bR;
		CUSTID = cUSTID;
		CUSTNAME = cUSTNAME;
		CUST_PSN_CARD_TYPE = cUST_PSN_CARD_TYPE;
		CUST_PSN_CARD_NUMBER = cUST_PSN_CARD_NUMBER;
		PHONE_NO = pHONE_NO;
		CUST_CARD_NUMBER = cUST_CARD_NUMBER;
		CUST_CARD_TYPE = cUST_CARD_TYPE;
	}

	public CustmerQuery() {
		super();
	}

}
