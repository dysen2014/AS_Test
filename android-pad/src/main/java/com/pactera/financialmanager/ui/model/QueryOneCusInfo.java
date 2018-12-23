package com.pactera.financialmanager.ui.model;

/**
 * 单一客户查询（地图模块）
 * 
 * @author JAY
 * 
 */
public class QueryOneCusInfo {
	public String CUSTNAME;// 客户名称
	public String phone;// 手机号
	public String CUST_PSN_CARD_NUMBER;// 证件号码
	public String CUSTTYPE;// 对公对私(01:对私，02:对公)

	@Override
	public String toString() {
		return "{\"CUSTNAME\":\"" + CUSTNAME + "\",\"phone\":\"" + phone
				+ "\",\"CUST_PSN_CARD_NUMBER\":\"" + CUST_PSN_CARD_NUMBER
				+ "\",\"CUSTTYPE\":\"" + CUSTTYPE + "\"}";
	}

}
