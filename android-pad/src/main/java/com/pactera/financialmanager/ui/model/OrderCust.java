package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户排名
 * @author Administrator
 *
 */
public class OrderCust implements Serializable{

	private static final long serialVersionUID = -580429520884113546L;

	private String CUST_NO;		// 客户id 
	
	private String USER_NAME;	// 客户名字
	
	private String BAL;			// 金额  
	
	private String RANK;		// 排名 

	public String getCUST_NO() {
		return CUST_NO;
	}

	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getBAL() {
		return BAL;
	}

	public void setBAL(String bAL) {
		BAL = bAL;
	}

	public String getRANK() {
		return RANK;
	}

	public void setRANK(String rANK) {
		RANK = rANK;
	}

	public OrderCust(String cUST_NO, String uSER_NAME, String bAL, String rANK) {
		super();
		CUST_NO = cUST_NO;
		USER_NAME = uSER_NAME;
		BAL = bAL;
		RANK = rANK;
	}

	public OrderCust() {
		super();
	}
	
}
