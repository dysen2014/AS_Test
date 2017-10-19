package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 点击搜索按钮之后，查询客户信息返回后保存的javabean
 */
public class SerachCustomerInfo implements Serializable {

	private static final long serialVersionUID = -1917972378605928358L;

	private String CUSTID;// 客户号
	
	private String IDV_CHN_NM;// 客户姓名
	
	private String CUST_VALUE_RATE;// 客户价值评级
	
	private String CUST_PSN_CARD_TYPE;// 证件类型
	
	private String CUST_PSN_CARD_NUMBER;// 证件号码
	
	private String PHONE_NO;// 手机号码
	
	private String PRO_PRO_AMT;// 金融资产总额
	
	private String DUE_ON_DEMAND_ALL;// 活期存款余额
	
	private String FIXED_TIME_DEPOSIT_ALL;// 定期存款余额
	
	private String MMM_PRO_AMT;// 理财存款余额

	public String getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(String cUSTID) {
		CUSTID = cUSTID;
	}

	public String getIDV_CHN_NM() {
		return IDV_CHN_NM;
	}

	public void setIDV_CHN_NM(String iDV_CHN_NM) {
		IDV_CHN_NM = iDV_CHN_NM;
	}

	public String getCUST_VALUE_RATE() {
		return CUST_VALUE_RATE;
	}

	public void setCUST_VALUE_RATE(String cUST_VALUE_RATE) {
		CUST_VALUE_RATE = cUST_VALUE_RATE;
	}

	public String getCUST_PSN_CARD_TYPE() {
		return CUST_PSN_CARD_TYPE;
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

	public String getPRO_PRO_AMT() {
		return PRO_PRO_AMT;
	}

	public void setPRO_PRO_AMT(String pRO_PRO_AMT) {
		PRO_PRO_AMT = pRO_PRO_AMT;
	}

	public String getDUE_ON_DEMAND_ALL() {
		return DUE_ON_DEMAND_ALL;
	}

	public void setDUE_ON_DEMAND_ALL(String dUE_ON_DEMAND_ALL) {
		DUE_ON_DEMAND_ALL = dUE_ON_DEMAND_ALL;
	}

	public String getFIXED_TIME_DEPOSIT_ALL() {
		return FIXED_TIME_DEPOSIT_ALL;
	}

	public void setFIXED_TIME_DEPOSIT_ALL(String fIXED_TIME_DEPOSIT_ALL) {
		FIXED_TIME_DEPOSIT_ALL = fIXED_TIME_DEPOSIT_ALL;
	}

	public String getMMM_PRO_AMT() {
		return MMM_PRO_AMT;
	}

	public void setMMM_PRO_AMT(String mMM_PRO_AMT) {
		MMM_PRO_AMT = mMM_PRO_AMT;
	}

	public SerachCustomerInfo(String cUSTID, String iDV_CHN_NM,
			String cUST_VALUE_RATE, String cUST_PSN_CARD_TYPE,
			String cUST_PSN_CARD_NUMBER, String pHONE_NO, String pRO_PRO_AMT,
			String dUE_ON_DEMAND_ALL, String fIXED_TIME_DEPOSIT_ALL,
			String mMM_PRO_AMT) {
		super();
		CUSTID = cUSTID;
		IDV_CHN_NM = iDV_CHN_NM;
		CUST_VALUE_RATE = cUST_VALUE_RATE;
		CUST_PSN_CARD_TYPE = cUST_PSN_CARD_TYPE;
		CUST_PSN_CARD_NUMBER = cUST_PSN_CARD_NUMBER;
		PHONE_NO = pHONE_NO;
		PRO_PRO_AMT = pRO_PRO_AMT;
		DUE_ON_DEMAND_ALL = dUE_ON_DEMAND_ALL;
		FIXED_TIME_DEPOSIT_ALL = fIXED_TIME_DEPOSIT_ALL;
		MMM_PRO_AMT = mMM_PRO_AMT;
	}

	public SerachCustomerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SerachCustomerInfoHallFirstActivity [CUSTID=" + CUSTID
				+ ", IDV_CHN_NM=" + IDV_CHN_NM + ", CUST_VALUE_RATE="
				+ CUST_VALUE_RATE + ", CUST_PSN_CARD_TYPE="
				+ CUST_PSN_CARD_TYPE + ", CUST_PSN_CARD_NUMBER="
				+ CUST_PSN_CARD_NUMBER + ", PHONE_NO=" + PHONE_NO
				+ ", PRO_PRO_AMT=" + PRO_PRO_AMT + ", DUE_ON_DEMAND_ALL="
				+ DUE_ON_DEMAND_ALL + ", FIXED_TIME_DEPOSIT_ALL="
				+ FIXED_TIME_DEPOSIT_ALL + ", MMM_PRO_AMT=" + MMM_PRO_AMT + "]";
	}

}
