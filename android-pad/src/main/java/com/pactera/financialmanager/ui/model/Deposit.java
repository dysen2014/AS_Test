package com.pactera.financialmanager.ui.model;

/**
 * 存款
 * @author Administrator
 *
 */
public class Deposit {

	private String TJ_DATE; // 统计日期
	
	private String DEPS_BAL; // 存款余额

	public String getTJ_DATE() {
		return TJ_DATE;
	}

	public void setTJ_DATE(String tJ_DATE) {
		TJ_DATE = tJ_DATE;
	}

	public String getDEPS_BAL() {
		return DEPS_BAL;
	}

	public void setDEPS_BAL(String dEPS_BAL) {
		DEPS_BAL = dEPS_BAL;
	}
	
}
