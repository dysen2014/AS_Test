package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户购买力返回信息的javabean
 */
public class CustomerPowerInfo implements Serializable {

	private static final long serialVersionUID = -3419465449239889777L;
	
	private String prd_name;// 到期产品
	
	private String prodEndDate;// 到期日期
	
	private String prodAmt;// 到期金额

	public String getPrd_name() {
		return prd_name;
	}

	public void setPrd_name(String prd_name) {
		this.prd_name = prd_name;
	}

	public String getProdEndDate() {
		return prodEndDate;
	}

	public void setProdEndDate(String prodEndDate) {
		this.prodEndDate = prodEndDate;
	}

	public String getProdAmt() {
		return prodAmt;
	}

	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}

	public CustomerPowerInfo(String prd_name, String prodEndDate, String prodAmt) {
		super();
		this.prd_name = prd_name;
		this.prodEndDate = prodEndDate;
		this.prodAmt = prodAmt;
	}

	public CustomerPowerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CustomerPowerInfo [prd_name=" + prd_name + ", prodEndDate="
				+ prodEndDate + ", prodAmt=" + prodAmt + "]";
	}
}
