package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 机构
 * @author Administrator
 *
 */
public class Organization implements Serializable{

	private static final long serialVersionUID = -8895816726221224739L;

	private String ORGID;	// 机构号
	
	private String DEPS_BAL;	// 存款余额 
	
	private String CITY_DEPS_BAL_RANK;	// 市州级存款余额排名
	
	private String PROV_DEPS_BAL_RANK;	// 省级存款余额排名
	
	private String LOAN_BAL;	// 贷款余额
	
	private String CITY_LOAN_BAL_RANK; 	// 市州级贷款余额排名 
	
	private String PROV_LOAN_BAL_RANK; 	// 省级贷款余额排名
	
	private String FINA_BAL;	// 理财余额 
	
	private String CITY_FINA_BAL_RANK;	// 市州级理财余额排名
	
	private String PROV_FINA_BAL_RANK;	// 省级贷理财余额排名

	public String getORGID() {
		return ORGID;
	}

	public void setORGID(String oRGID) {
		ORGID = oRGID;
	}

	public String getDEPS_BAL() {
		return DEPS_BAL;
	}

	public void setDEPS_BAL(String dEPS_BAL) {
		DEPS_BAL = dEPS_BAL;
	}

	public String getCITY_DEPS_BAL_RANK() {
		return CITY_DEPS_BAL_RANK;
	}

	public void setCITY_DEPS_BAL_RANK(String cITY_DEPS_BAL_RANK) {
		CITY_DEPS_BAL_RANK = cITY_DEPS_BAL_RANK;
	}

	public String getPROV_DEPS_BAL_RANK() {
		return PROV_DEPS_BAL_RANK;
	}

	public void setPROV_DEPS_BAL_RANK(String pROV_DEPS_BAL_RANK) {
		PROV_DEPS_BAL_RANK = pROV_DEPS_BAL_RANK;
	}

	public String getLOAN_BAL() {
		return LOAN_BAL;
	}

	public void setLOAN_BAL(String lOAN_BAL) {
		LOAN_BAL = lOAN_BAL;
	}

	public String getCITY_LOAN_BAL_RANK() {
		return CITY_LOAN_BAL_RANK;
	}

	public void setCITY_LOAN_BAL_RANK(String cITY_LOAN_BAL_RANK) {
		CITY_LOAN_BAL_RANK = cITY_LOAN_BAL_RANK;
	}

	public String getPROV_LOAN_BAL_RANK() {
		return PROV_LOAN_BAL_RANK;
	}

	public void setPROV_LOAN_BAL_RANK(String pROV_LOAN_BAL_RANK) {
		PROV_LOAN_BAL_RANK = pROV_LOAN_BAL_RANK;
	}

	public String getFINA_BAL() {
		return FINA_BAL;
	}

	public void setFINA_BAL(String fINA_BAL) {
		FINA_BAL = fINA_BAL;
	}

	public String getCITY_FINA_BAL_RANK() {
		return CITY_FINA_BAL_RANK;
	}

	public void setCITY_FINA_BAL_RANK(String cITY_FINA_BAL_RANK) {
		CITY_FINA_BAL_RANK = cITY_FINA_BAL_RANK;
	}

	public String getPROV_FINA_BAL_RANK() {
		return PROV_FINA_BAL_RANK;
	}

	public void setPROV_FINA_BAL_RANK(String pROV_FINA_BAL_RANK) {
		PROV_FINA_BAL_RANK = pROV_FINA_BAL_RANK;
	}

	public Organization() {
		super();
	}

	public Organization(String oRGID, String dEPS_BAL,
			String cITY_DEPS_BAL_RANK, String pROV_DEPS_BAL_RANK,
			String lOAN_BAL, String cITY_LOAN_BAL_RANK,
			String pROV_LOAN_BAL_RANK, String fINA_BAL,
			String cITY_FINA_BAL_RANK, String pROV_FINA_BAL_RANK) {
		super();
		ORGID = oRGID;
		DEPS_BAL = dEPS_BAL;
		CITY_DEPS_BAL_RANK = cITY_DEPS_BAL_RANK;
		PROV_DEPS_BAL_RANK = pROV_DEPS_BAL_RANK;
		LOAN_BAL = lOAN_BAL;
		CITY_LOAN_BAL_RANK = cITY_LOAN_BAL_RANK;
		PROV_LOAN_BAL_RANK = pROV_LOAN_BAL_RANK;
		FINA_BAL = fINA_BAL;
		CITY_FINA_BAL_RANK = cITY_FINA_BAL_RANK;
		PROV_FINA_BAL_RANK = pROV_FINA_BAL_RANK;
	}
	
}
