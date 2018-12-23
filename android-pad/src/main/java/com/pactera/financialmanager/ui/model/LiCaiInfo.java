package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

public class LiCaiInfo implements Serializable{

	/**
	 * 理财产品信息
	 */
	private static final long serialVersionUID = 4514286824814866672L;
	
	public String prodID;
	public String prodName;
	public String saleDate;
	public String riskType;
	public String incomeRate; //收益率
	public String prodClass; //产品种类
}
