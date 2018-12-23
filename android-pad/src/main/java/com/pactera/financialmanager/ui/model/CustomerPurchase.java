package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

public class CustomerPurchase implements Serializable{

	/**
	 * 客户购买力
	 */
//	这是之前项目的字段
//	private static final long serialVersionUID = -2332654768468670353L;
//	public String custID;  //客户ID
//	public String prdNo;  //产品代码 
//	public String prdName;    //产品名称
//	public String tranAmt;       //交易金额
//	public String tranDat; //交易日期
//	public String clsDat;  //到期日期
	
	
//	private String deptBal;//活期存款金额      这个单独提出来吧
	private String prdName;//到期产品
	private String prodEndDate;//到期日期
	private String prodAmt;//到期金额
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
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
	
	public CustomerPurchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerPurchase(String prdName, String prodEndDate, String prodAmt) {
		super();
		this.prdName = prdName;
		this.prodEndDate = prodEndDate;
		this.prodAmt = prodAmt;
	}
	
	@Override
	public String toString() {
		return "CustomerPurchase [prdName=" + prdName + ", prodEndDate="
				+ prodEndDate + ", prodAmt=" + prodAmt + "]";
	}
	
}
