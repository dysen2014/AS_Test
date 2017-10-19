package com.pactera.financialmanager.ui.model;

/**
 * 存款预约
 * 
 * @author JAY
 * 
 */
public class FinanceDeposit {
	public String pkid="";// 主键
	public String deposit_amount="";// 存款金额
	public String ap_time="";// 预约时间
//	public String remind_time="";// 提醒日期
	public String des="";// 备注
	public String custid="";// 客户号
	public String RECORD_TIME="";// 记录时间
	public String RECORD_USER="";// 记录人

	// @Override
	// public String toString() {
	// return "&pkid=" + pkid + "&custid=" + custid + "&ap_time=" + ap_time
	// + "&remind_time=" + remind_time + "&des=" + des
	// + "&deposit_amount=" + deposit_amount;
	// }

//	@Override
//	public String toString() {
//		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
//				+ "\",\"ap_time\":\"" + ap_time + "\",\"remind_time\":\""
//				+ remind_time + "\",\"des\":\"" + des
//				+ "\",\"deposit_amount\":\"" + deposit_amount + "\"}";
//	}


	@Override
	public String toString() {
		return "{" +
				"pkid='" + pkid + '\'' +
				", deposit_amount='" + deposit_amount + '\'' +
				", ap_time='" + ap_time + '\'' +
				", des='" + des + '\'' +
				", custid='" + custid + '\'' +
				'}';
	}
}
