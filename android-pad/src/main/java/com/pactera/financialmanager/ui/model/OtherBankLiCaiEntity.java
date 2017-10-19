package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 他行理财信息查询结果的javabean
 */
public class OtherBankLiCaiEntity implements Serializable {
	private String pkid;//主键
	private String PRD_NAME;//产品名称
	private String EXP_AMOUNT;//认购金额
	private String EXP_DATE;//认购日期
	private String REMIND_DATE;//提醒日期
	private String END_DATE;//到期日
	private String EXP_PROFIT;//预期收益率
	private String CREATE_USER;//创建人
	private String CREATE_TIME;//创建时间
	
	public String getCREATE_USER() {
		return CREATE_USER;
	}
	public void setCREATE_USER(String cREATE_USER) {
		CREATE_USER = cREATE_USER;
	}
	public String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public String getPkid() {
		return pkid;
	}
	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public String getPRD_NAME() {
		return PRD_NAME;
	}
	public void setPRD_NAME(String pRD_NAME) {
		PRD_NAME = pRD_NAME;
	}
	public String getEXP_AMOUNT() {
		return EXP_AMOUNT;
	}
	public void setEXP_AMOUNT(String eXP_AMOUNT) {
		EXP_AMOUNT = eXP_AMOUNT;
	}
	public String getEXP_DATE() {
		return EXP_DATE;
	}
	public void setEXP_DATE(String eXP_DATE) {
		EXP_DATE = eXP_DATE;
	}
	public String getREMIND_DATE() {
		return REMIND_DATE;
	}
	public void setREMIND_DATE(String rEMIND_DATE) {
		REMIND_DATE = rEMIND_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getEXP_PROFIT() {
		return EXP_PROFIT;
	}
	public void setEXP_PROFIT(String eXP_PROFIT) {
		EXP_PROFIT = eXP_PROFIT;
	}
	public OtherBankLiCaiEntity(String pkid, String pRD_NAME,
			String eXP_AMOUNT, String eXP_DATE, String rEMIND_DATE,
			String eND_DATE, String eXP_PROFIT,String CREATE_USER,String CREATE_TIME) {
		super();
		this.pkid = pkid;
		this.PRD_NAME = pRD_NAME;
		this.EXP_AMOUNT = eXP_AMOUNT;
		this.EXP_DATE = eXP_DATE;
		this.REMIND_DATE = rEMIND_DATE;
		this.END_DATE = eND_DATE;
		this.EXP_PROFIT = eXP_PROFIT;
		this.CREATE_USER=CREATE_USER;
		this.CREATE_TIME=CREATE_TIME;
	}
	
	public OtherBankLiCaiEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "OtherBankLiCaiEntity [pkid=" + pkid + ", PRD_NAME=" + PRD_NAME
				+ ", EXP_AMOUNT=" + EXP_AMOUNT + ", EXP_DATE=" + EXP_DATE+ ", CREATE_USER=" + CREATE_USER+ ", CREATE_TIME=" + CREATE_TIME
				+ ", REMIND_DATE=" + REMIND_DATE + ", END_DATE=" + END_DATE
				+ ", EXP_PROFIT=" + EXP_PROFIT + "]";
	}
}
