package com.pactera.financialmanager.ui.model;

/**
 * 其他需求
 * 
 * @author JAY
 * 
 */
public class FinanceOther {
	public String pkid="";// 主键
	public String prd_name="";// 产品名称
	public String occur_time="";// 预约时间
//	public String remind_time="";// 提醒日期
	public String des="";// 备注
	public String custid="";// 客户号
	public String  RECORD_TIME="";//记录时间
	public String  RECORD_USER="";//记录人ID
	public String  RECORD_USERNAME="";//记录人姓名
	
//	@Override
//	public String toString() {
//		return "&pkid=" + pkid + "&custid=" + custid + "&prd_name=" + prd_name
//				+ "&remind_time=" + remind_time + "&des=" + des
//				+ "&occur_time=" + occur_time;
//	}
	
	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"prd_name\":\"" + prd_name
				+ "\",\"des\":\"" + des
				+ "\",\"occur_time\":\"" + occur_time+
				"\"}";
	}

}
