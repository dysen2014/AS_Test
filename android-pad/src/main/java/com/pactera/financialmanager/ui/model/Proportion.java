package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户占比
 * @author Administrator
 *
 */
public class Proportion implements Serializable{

	private static final long serialVersionUID = 7600247295368449977L;

	private String CUSTTYPE; // 评级类型
	
	private String CUST_NUM; // 价值评级数 

	public String getCUSTTYPE() {
		return CUSTTYPE;
	}

	public void setCUSTTYPE(String cUSTTYPE) {
		CUSTTYPE = cUSTTYPE;
	}

	public String getCUST_NUM() {
		return CUST_NUM;
	}

	public void setCUST_NUM(String cUST_NUM) {
		CUST_NUM = cUST_NUM;
	}

	public Proportion(String cUSTTYPE, String cUST_NUM) {
		super();
		CUSTTYPE = cUSTTYPE;
		CUST_NUM = cUST_NUM;
	}

	public Proportion() {
		super();
	}
	
}
