package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/8/4.
 * 贷后提醒参数
 */

public class AfterTheLoanHeader {


	/**
	 * NAME : 借据号
	 * ID : DueBillNO
	 * SORT : 0
	 */

	private String NAME;
	private String ID;
	private String SORT;

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getSORT() {
		return SORT;
	}

	public void setSORT(String SORT) {
		this.SORT = SORT;
	}
}
