package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 查询联系人信息返回的数据保存的javabean
 */
public class ContantsInfoEntity implements Serializable {
	private String PKID;//	主键
	private String KEY_MEM_NAME;//关键成员姓名
	private String REL_ID;//关联客户号
	private String REL_TYPE;//关系类型
	private String KEY_CERT_TYPE;//关键人证件类型
	private String KEY_CERT_NO;//证件号码
	private String CONTACT_PHONE;//电话号码
	private String DES;//备注
	private String CUSTID;//客户ID
	private String KEY_CONDITION;//关键人情况

	private String EMPLOYTIME;//从业时间
	private String MASTERTIME;//任职时间

	public String getKEY_CONDITION() {
		return KEY_CONDITION;
	}
	public void setKEY_CONDITION(String pOSITION) {
		KEY_CONDITION = pOSITION;
	}
	public String getEMPLOYTIME() {
		return EMPLOYTIME;
	}
	public void setEMPLOYTIME(String para_EMPLOY_TIME) {
        EMPLOYTIME = para_EMPLOY_TIME;
	}
	public String getMASTERTIME() {
		return MASTERTIME;
	}
	public void setMASTERTIME(String para_MASTER_TIME) {
        MASTERTIME = para_MASTER_TIME;
	}
	public String getPKID() {
		return PKID;
	}
	public void setPKID(String pKID) {
		PKID = pKID;
	}
	public String getCONTACT_PHONE() {
		return CONTACT_PHONE;
	}
	public void setCONTACT_PHONE(String cONTACT_PHONE) {
		CONTACT_PHONE = cONTACT_PHONE;
	}
	public String getKEY_MEM_NAME() {
		return KEY_MEM_NAME;
	}
	public void setKEY_MEM_NAME(String kEY_MEM_NAME) {
		KEY_MEM_NAME = kEY_MEM_NAME;
	}
	public String getREL_ID() {
		return REL_ID;
	}
	public void setREL_ID(String rEL_ID) {
		REL_ID = rEL_ID;
	}
	public String getREL_TYPE() {
		return REL_TYPE;
	}
	public void setREL_TYPE(String rEL_TYPE) {
		REL_TYPE = rEL_TYPE;
	}
	public String getKEY_CERT_TYPE() {
		return KEY_CERT_TYPE;
	}
	public void setKEY_CERT_TYPE(String kEY_CERT_TYPE) {
		KEY_CERT_TYPE = kEY_CERT_TYPE;
	}
	public String getKEY_CERT_NO() {
		return KEY_CERT_NO;
	}
	public void setKEY_CERT_NO(String kEY_CERT_NO) {
		KEY_CERT_NO = kEY_CERT_NO;
	}
	public String getDES() {
		return DES;
	}
	public void setDES(String dES) {
		DES = dES;
	}
	public String getCUSTID() {
		return CUSTID;
	}
	public void setCUSTID(String CUSTID) {
		this.CUSTID = CUSTID;
	}
	
	public ContantsInfoEntity(String pKID, String kEY_MEM_NAME, String rEL_ID,
			String rEL_TYPE, String kEY_CERT_TYPE, String kEY_CERT_NO,
			String cONTACT_PHONE, String dES, String custid, String pOSITION,
			String cEMPLOY_TIME, String cMASTER_TIME) {
		super();
		PKID = pKID;
		KEY_MEM_NAME = kEY_MEM_NAME;
		REL_ID = rEL_ID;
		REL_TYPE = rEL_TYPE;
		KEY_CERT_TYPE = kEY_CERT_TYPE;
		KEY_CERT_NO = kEY_CERT_NO;
		CONTACT_PHONE = cONTACT_PHONE;
		DES = dES;
		this.CUSTID = custid;
		KEY_CONDITION = pOSITION;
        EMPLOYTIME = cEMPLOY_TIME;
        MASTERTIME = cMASTER_TIME;
	}
	public ContantsInfoEntity() {
		super();
	}
	@Override
	public String toString() {
		return "ContantsInfoEntity [PKID=" + PKID + ", KEY_MEM_NAME="
				+ KEY_MEM_NAME + ", REL_ID=" + REL_ID + ", REL_TYPE="
				+ REL_TYPE + ", KEY_CERT_TYPE=" + KEY_CERT_TYPE
				+ ", KEY_CERT_NO=" + KEY_CERT_NO + ", CONTACT_PHONE="
				+ CONTACT_PHONE + ", DES=" + DES + ", CUSTID=" + CUSTID
				+ ", KEY_CONDITION=" + KEY_CONDITION + ", EMPLOYTIME=" + EMPLOYTIME
				+ ", MASTERTIME=" + MASTERTIME + "]";
	}
	
}
