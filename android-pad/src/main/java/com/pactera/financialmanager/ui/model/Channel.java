package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 渠道
 * @author Administrator
 *
 */
public class Channel implements Serializable{

	private static final long serialVersionUID = 8064339788142256897L;

	private String CUST_MAN_NO; // 客户经理编号

	private String ORGID; // 机构号

	private String E_BANK; // 网上银行

	private String MOBIL_BANK; // 手机银行

	private String BUSI_MOBIL_BANK; // 商业手机银行

	private String POS; // POS机

	private String CARL_PAY; // 卡乐付

	private String WECHAT_BANK; // 微信银行 

	private String DEPS_CARD; // 信用卡 
	
	private String NOTE_SERVICE; // 短信银行
	
	private String DF_SALARY;// 代发工资

	public String getCUST_MAN_NO() {
		return CUST_MAN_NO;
	}

	public void setCUST_MAN_NO(String cUST_MAN_NO) {
		CUST_MAN_NO = cUST_MAN_NO;
	}

	public String getORGID() {
		return ORGID;
	}

	public String getDF_SALARY() {
		return DF_SALARY;
	}

	public void setDF_SALARY(String dF_SALARY) {
		DF_SALARY = dF_SALARY;
	}

	public void setORGID(String oRGID) {
		ORGID = oRGID;
	}

	public String getE_BANK() {
		return E_BANK;
	}

	public void setE_BANK(String e_BANK) {
		E_BANK = e_BANK;
	}

	public String getMOBIL_BANK() {
		return MOBIL_BANK;
	}

	public void setMOBIL_BANK(String mOBIL_BANK) {
		MOBIL_BANK = mOBIL_BANK;
	}

	public String getBUSI_MOBIL_BANK() {
		return BUSI_MOBIL_BANK;
	}

	public void setBUSI_MOBIL_BANK(String bUSI_MOBIL_BANK) {
		BUSI_MOBIL_BANK = bUSI_MOBIL_BANK;
	}

	public String getPOS() {
		return POS;
	}

	public void setPOS(String pOS) {
		POS = pOS;
	}

	public String getCARL_PAY() {
		return CARL_PAY;
	}

	public void setCARL_PAY(String cARL_PAY) {
		CARL_PAY = cARL_PAY;
	}

	public String getWECHAT_BANK() {
		return WECHAT_BANK;
	}

	public void setWECHAT_BANK(String wECHAT_BANK) {
		WECHAT_BANK = wECHAT_BANK;
	}

	public String getDEPS_CARD() {
		return DEPS_CARD;
	}

	public void setDEPS_CARD(String dEPS_CARD) {
		DEPS_CARD = dEPS_CARD;
	}

	public String getNOTE_SERVICE() {
		return NOTE_SERVICE;
	}

	public void setNOTE_SERVICE(String nOTE_SERVICE) {
		NOTE_SERVICE = nOTE_SERVICE;
	}
	
}
