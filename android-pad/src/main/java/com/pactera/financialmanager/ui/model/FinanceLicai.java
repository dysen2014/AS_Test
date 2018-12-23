package com.pactera.financialmanager.ui.model;

/**
 * 其他需求
 * 
 * @author JAY
 * 
 */
public class FinanceLicai {
	public String pkid="";// 主键
	public String CUSTID="";// 客户号
	public String STATUS="";// 状态
	public String OCCUR_TIME="";// 预约日期
//	public String REMIND_TIME="";// 提醒日期
	public String DES="";// 内容描述
	public String AMOUNT="";// 金额

	// @Override
	// public String toString() {
	// return "&pkid=" + pkid + "&custid=" + custid + "&prd_name=" + prd_name
	// + "&remind_time=" + remind_time + "&des=" + des
	// + "&occur_time=" + occur_time;
	// }

	@Override
	public String toString() {
		return "{\"PKID\":\"" + pkid + "\",\"CUSTID\":\"" + CUSTID
				+ "\",\"STATUS\":\"" + STATUS + "\",\"OCCUR_TIME\":\""
				+ OCCUR_TIME + "\",\"AMOUNT\":\"" + AMOUNT + "\",\"DES\":\"" + DES + "\"}";
	}

}
