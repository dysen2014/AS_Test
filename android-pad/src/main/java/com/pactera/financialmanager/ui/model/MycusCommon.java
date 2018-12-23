package com.pactera.financialmanager.ui.model;


/**
 * 我的建档客户
 * 
 * @author Administrator
 *
 */
public class MycusCommon {

	/** 企业ID */
	private String CUSTID;

	/** 企业名称 */
	private String ORG_LGL_NM;

	/** 企业规模 */
	private String ORG_SCL_TP_ID;

	/** 企业性质 */
	private String COMPANY_PEROPERTY;

	/** 客户级别 */
	private String CUST_VALUE;

	/** 客户类型 */
	private String CUST_TYPE;

	/** 培植方向 */
	private String CULTIVATE_DIRCT;

	/** 建档时间 */
	private String CREATE_TIME;

	/** 修改时间 */
	private String LAST_UPDATE_TIME;
	private String ARCHIVE_UPDATEDATE;

	public String getARCHIVE_UPDATEDATE() {
		return ARCHIVE_UPDATEDATE;
	}
	public void setARCHIVE_UPDATEDATE(String aRCHIVE_UPDATEDATE) {
		ARCHIVE_UPDATEDATE = aRCHIVE_UPDATEDATE;
	}

	public String getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(String cUSTID) {
		CUSTID = cUSTID;
	}

	public String getORG_LGL_NM() {
		return ORG_LGL_NM;
	}

	public void setORG_LGL_NM(String oRG_LGL_NM) {
		ORG_LGL_NM = oRG_LGL_NM;
	}

	public String getORG_SCL_TP_ID() {
		return ORG_SCL_TP_ID;
	}

	public void setORG_SCL_TP_ID(String oRG_SCL_TP_ID) {
		ORG_SCL_TP_ID = oRG_SCL_TP_ID;
	}

	public String getCOMPANY_PEROPERTY() {
		return COMPANY_PEROPERTY;
	}

	public void setCOMPANY_PEROPERTY(String cOMPANY_PEROPERTY) {
		COMPANY_PEROPERTY = cOMPANY_PEROPERTY;
	}

	public String getCUST_VALUE() {
		return CUST_VALUE;
	}

	public void setCUST_VALUE(String cUST_VALUE) {
		CUST_VALUE = cUST_VALUE;
	}

	public String getCUST_TYPE() {
		return CUST_TYPE;
	}

	public void setCUST_TYPE(String cUST_TYPE) {
		CUST_TYPE = cUST_TYPE;
	}

	public String getCULTIVATE_DIRCT() {
		return CULTIVATE_DIRCT;
	}

	public void setCULTIVATE_DIRCT(String cULTIVATE_DIRCT) {
		CULTIVATE_DIRCT = cULTIVATE_DIRCT;
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public String getLAST_UPDATE_TIME() {
		return LAST_UPDATE_TIME;
	}

	public void setLAST_UPDATE_TIME(String lAST_UPDATE_TIME) {
		LAST_UPDATE_TIME = lAST_UPDATE_TIME;
	}

}
