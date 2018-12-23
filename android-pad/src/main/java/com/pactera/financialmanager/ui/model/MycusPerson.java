package com.pactera.financialmanager.ui.model;

/**
 * 我的建档客户
 * 
 * @author Administrator
 *
 */
public class MycusPerson {
	/** 客户ID */
	private String CUSTID;

	/** 客户中文名称 */
	private String IDV_CHN_NM;

	/** 性别 */
	private String IDV_GND_ID;

	/** 客户级别 */
	private String CUST_VALUE_RATE;

	/** 培植方向 */
	private String CULTIVATE_DIRCT;

	/** 客户类型 */
	private String CUST_CLASS;

	/** 建档时间 */
	private String FOUND_DT;
    private String ARCHIVE_UPDATEDATE;
	/** 修改时间 */
	private String LAST_UPDATE_TIME;

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

	public String getIDV_GND_ID() {
		return IDV_GND_ID;
	}

	public void setIDV_GND_ID(String iDV_GND_ID) {
		IDV_GND_ID = iDV_GND_ID;
	}

	public String getCUST_VALUE_RATE() {
		return CUST_VALUE_RATE;
	}

	public void setCUST_VALUE_RATE(String cUST_VALUE_RATE) {
		CUST_VALUE_RATE = cUST_VALUE_RATE;
	}

	public String getCULTIVATE_DIRCT() {
		return CULTIVATE_DIRCT;
	}

	public void setCULTIVATE_DIRCT(String cULTIVATE_DIRCT) {
		CULTIVATE_DIRCT = cULTIVATE_DIRCT;
	}

	public String getCUST_CLASS() {
		return CUST_CLASS;
	}

	public void setCUST_CLASS(String cUST_CLASS) {
		CUST_CLASS = cUST_CLASS;
	}

	public String getFOUND_DT() {
		return FOUND_DT;
	}

	public void setFOUND_DT(String fOUND_DT) {
		FOUND_DT = fOUND_DT;
	}
	public String getARCHIVE_UPDATEDATE() {
		return ARCHIVE_UPDATEDATE;
	}

	public void setARCHIVE_UPDATEDATE(String aRCHIVE_UPDATEDATE) {
		FOUND_DT = aRCHIVE_UPDATEDATE;
	}

	public String getLAST_UPDATE_TIME() {
		return LAST_UPDATE_TIME;
	}

	public void setLAST_UPDATE_TIME(String lAST_UPDATE_TIME) {
		LAST_UPDATE_TIME = lAST_UPDATE_TIME;
	}

}
