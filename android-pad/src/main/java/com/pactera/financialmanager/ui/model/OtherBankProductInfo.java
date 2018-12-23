package com.pactera.financialmanager.ui.model;

/**
 * 他行签约产品
 * 
 * @author JAY
 * 
 */
public class OtherBankProductInfo {

	public String CUSTID;// 客户号
	public String pkid;// 主键
	public String SIGN_NAME;// 签约名称
	public String SIGN_PRD;// 签约类型
	public String SIGN_ORG;// 签约银行
	public String SIGN_TIME;// 签约日期
	public String SIGN_PHONE;// 签约电话
	


	@Override
	public String toString() {
		return "{\"PKID\":\"" + pkid + "\",\"CUSTID\":\"" + CUSTID
				+ "\",\"SIGN_NAME\":\"" + SIGN_NAME + "\",\"SIGN_PRD\":\""
				+ SIGN_PRD + "\",\"SIGN_ORG\":\"" + SIGN_ORG
				+ "\",\"SIGN_PHONE\":\"" + SIGN_PHONE
				+ "\",\"SIGN_TIME\":\"" + SIGN_TIME + "\"}";
	}

}
