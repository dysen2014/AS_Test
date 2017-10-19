package com.pactera.financialmanager.ui.model;

/**
 * 电子产品预约
 * 
 * @author JAY
 * 
 */
public class FinanceProduct {
	public String pkid="";// 主键
	public String occur_time="";// 发生时间
//	public String remind_time="";// 提醒日期
	public String des="";// 备注
	public String custid="";// 客户号
	public String kind="";// 种类
	public String RECORD_TIME="";// 记录时间
	public String RECORD_USER="";// 记录人
	public String  RECORD_USERNAME="";//记录人姓名
	public String status="";// 状态
	
//	@Override
//	public String toString() {
//		return "&pkid=" + pkid + "&custid=" + custid + "&remind_time="
//				+ remind_time + "&occur_time=" + occur_time + "&des=" + des
//				+ "&kind=" + kind;
//	}

	
	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"kind\":\"" + kind
				+ "\",\"des\":\"" + des
				+ "\",\"status\":\"" + status
				+ "\",\"occur_time\":\"" + occur_time+
				"\"}";
	}
	
	
}
