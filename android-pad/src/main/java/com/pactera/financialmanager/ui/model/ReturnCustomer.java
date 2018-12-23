package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户回访
 * 
 * @author YPJ
 *
 */
public class ReturnCustomer implements Serializable {

	private static final long serialVersionUID = 7372286956639502954L;

	public String CUSTSERVEID; 	// 主键id
	
	public String CUSTID; 		// 客户姓名
	
	public String CUSTNAME; 	// 客户姓名
	
	public String SERVEMODE; 	// 服务方式
	
	public String SERVETITLE; 	// 服务标题
	
	public String PLANBGDATE; 	// 开始时间
	
	public String PLANENDDATE; 	// 结束时间
	
	public String PLANSERVECON; // 服务内容
	
	public String AWOKEDATE;	// 提醒日期
	
	public String CSSTATUS;		// 执行状态
	
	public String BRID;			// 所属网点
	
	public String SUBBANKID;	// 所属分行
	
	public String CREATORID;	// 创建人
	
	public String CREATEDATE;	// 创建时间
	
	public String MODIFYDATE;	// 最后更新日期
	
	public String SERVETYPE;	// 客户服务类型
	
	public String SUBBRID;		// 所属支行

	public String getCUSTSERVEID() {
		return CUSTSERVEID;
	}

	public void setCUSTSERVEID(String cUSTSERVEID) {
		CUSTSERVEID = cUSTSERVEID;
	}

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

	public String getSERVEMODE() {
		return SERVEMODE;
	}

	public void setSERVEMODE(String sERVEMODE) {
		SERVEMODE = sERVEMODE;
	}

	public String getSERVETITLE() {
		return SERVETITLE;
	}

	public void setSERVETITLE(String sERVETITLE) {
		SERVETITLE = sERVETITLE;
	}

	public String getPLANBGDATE() {
		return PLANBGDATE;
	}

	public void setPLANBGDATE(String pLANBGDATE) {
		PLANBGDATE = pLANBGDATE;
	}

	public String getPLANENDDATE() {
		return PLANENDDATE;
	}

	public void setPLANENDDATE(String pLANENDDATE) {
		PLANENDDATE = pLANENDDATE;
	}

	public String getPLANSERVECON() {
		return PLANSERVECON;
	}

	public void setPLANSERVECON(String pLANSERVECON) {
		PLANSERVECON = pLANSERVECON;
	}

	public String getAWOKEDATE() {
		return AWOKEDATE;
	}

	public void setAWOKEDATE(String aWOKEDATE) {
		AWOKEDATE = aWOKEDATE;
	}

	public String getCSSTATUS() {
		return CSSTATUS;
	}

	public void setCSSTATUS(String cSSTATUS) {
		CSSTATUS = cSSTATUS;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public String getSUBBANKID() {
		return SUBBANKID;
	}

	public void setSUBBANKID(String sUBBANKID) {
		SUBBANKID = sUBBANKID;
	}

	public String getCREATORID() {
		return CREATORID;
	}

	public void setCREATORID(String cREATORID) {
		CREATORID = cREATORID;
	}

	public String getCREATEDATE() {
		return CREATEDATE;
	}

	public void setCREATEDATE(String cREATEDATE) {
		CREATEDATE = cREATEDATE;
	}

	public String getMODIFYDATE() {
		return MODIFYDATE;
	}

	public void setMODIFYDATE(String mODIFYDATE) {
		MODIFYDATE = mODIFYDATE;
	}

	public String getSERVETYPE() {
		return SERVETYPE;
	}

	public void setSERVETYPE(String sERVETYPE) {
		SERVETYPE = sERVETYPE;
	}

	public String getSUBBRID() {
		return SUBBRID;
	}

	public void setSUBBRID(String sUBBRID) {
		SUBBRID = sUBBRID;
	}

	public ReturnCustomer(String cUSTSERVEID, String cUSTID, String cUSTNAME,
			String sERVEMODE, String sERVETITLE, String pLANBGDATE,
			String pLANENDDATE, String pLANSERVECON, String aWOKEDATE,
			String cSSTATUS, String bRID, String sUBBANKID, String cREATORID,
			String cREATEDATE, String mODIFYDATE, String sERVETYPE,
			String sUBBRID) {
		super();
		CUSTSERVEID = cUSTSERVEID;
		CUSTID = cUSTID;
		CUSTNAME = cUSTNAME;
		SERVEMODE = sERVEMODE;
		SERVETITLE = sERVETITLE;
		PLANBGDATE = pLANBGDATE;
		PLANENDDATE = pLANENDDATE;
		PLANSERVECON = pLANSERVECON;
		AWOKEDATE = aWOKEDATE;
		CSSTATUS = cSSTATUS;
		BRID = bRID;
		SUBBANKID = sUBBANKID;
		CREATORID = cREATORID;
		CREATEDATE = cREATEDATE;
		MODIFYDATE = mODIFYDATE;
		SERVETYPE = sERVETYPE;
		SUBBRID = sUBBRID;
	}

	public ReturnCustomer() {
		super();
	}

}
