package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 负债返回的字段
 */
public class FuzaiInfoEntity implements Serializable {
	private String pkid;//主键
	private String CREATE_USER;//创建人
	private String LAST_ANNUAL_INCOME;//上年营业收入
	private String LAST_ANNUAL_OUTPUT;//上年总成本
	private String PROFIT;//上年利润
	private String HAND_TAX;//上年上交税收
	private String FIX_ASSET;//固定资产
	private String HAND_CREDIT;//应收账款
	private String INVENTORY;//存货
	private String PAY_CREDIT;//应付账款
	private String DEBT_RENTAL;//负债总额


	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getCREATE_USER() {
		return CREATE_USER;
	}

	public void setCREATE_USER(String CREATE_USER) {
		this.CREATE_USER = CREATE_USER;
	}

	public String getDEBT_RENTAL() {
		return DEBT_RENTAL;
	}

	public void setDEBT_RENTAL(String DEBT_RENTAL) {
		this.DEBT_RENTAL = DEBT_RENTAL;
	}

	public String getLAST_ANNUAL_INCOME() {
		return LAST_ANNUAL_INCOME;
	}

	public void setLAST_ANNUAL_INCOME(String LAST_ANNUAL_INCOME) {
		this.LAST_ANNUAL_INCOME = LAST_ANNUAL_INCOME;
	}

	public String getLAST_ANNUAL_OUTPUT() {
		return LAST_ANNUAL_OUTPUT;
	}

	public void setLAST_ANNUAL_OUTPUT(String LAST_ANNUAL_OUTPUT) {
		this.LAST_ANNUAL_OUTPUT = LAST_ANNUAL_OUTPUT;
	}

	public String getPROFIT() {
		return PROFIT;
	}

	public void setPROFIT(String PROFIT) {
		this.PROFIT = PROFIT;
	}

	public String getHAND_TAX() {
		return HAND_TAX;
	}

	public void setHAND_TAX(String HAND_TAX) {
		this.HAND_TAX = HAND_TAX;
	}

	public String getHAND_CREDIT() {
		return HAND_CREDIT;
	}

	public void setHAND_CREDIT(String HAND_CREDIT) {
		this.HAND_CREDIT = HAND_CREDIT;
	}

	public String getPAY_CREDIT() {
		return PAY_CREDIT;
	}

	public void setPAY_CREDIT(String PAY_CREDIT) {
		this.PAY_CREDIT = PAY_CREDIT;
	}

	public String getINVENTORY() {
		return INVENTORY;
	}

	public void setINVENTORY(String INVENTORY) {
		this.INVENTORY = INVENTORY;
	}

	public String getFIX_ASSET() {
		return FIX_ASSET;
	}

	public void setFIX_ASSET(String FIX_ASSET) {
		this.FIX_ASSET = FIX_ASSET;
	}

	public FuzaiInfoEntity() {
	}

	@Override
	public String toString() {
		return "FuzaiInfoEntity{" +
				"pkid='" + pkid + '\'' +
				", CREATE_USER='" + CREATE_USER + '\'' +
				", DEBT_RENTAL='" + DEBT_RENTAL + '\'' +
				", LAST_ANNUAL_INCOME='" + LAST_ANNUAL_INCOME + '\'' +
				", LAST_ANNUAL_OUTPUT='" + LAST_ANNUAL_OUTPUT + '\'' +
				", PROFIT='" + PROFIT + '\'' +
				", HAND_TAX='" + HAND_TAX + '\'' +
				", HAND_CREDIT='" + HAND_CREDIT + '\'' +
				", PAY_CREDIT='" + PAY_CREDIT + '\'' +
				", INVENTORY='" + INVENTORY + '\'' +
				", FIX_ASSET='" + FIX_ASSET + '\'' +
				'}';
	}
}
