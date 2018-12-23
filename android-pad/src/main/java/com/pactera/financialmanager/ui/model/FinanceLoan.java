package com.pactera.financialmanager.ui.model;

/**
 * 贷款需求
 * 
 * @author JAY
 * 
 */
public class FinanceLoan {
	public String pkid="";// 主键
	public String occur_time="";// 预约时间
//	public String remind_time="";// 提醒日期
	public String des="";// 备注
	public String custid="";// 客户号
	// public String kind;// 种类
	public String amount="";// 金额
	public String purpose="";// 用途
	public String warrant="";// 可提供的担保方式
	public String turn_over="";// 周转期限
	public String status="";// 状态

	// public String busi_cate ;//业务品种

	// @Override
	// public String toString() {
	// return "&pkid=" + pkid + "&custid=" + custid + "&remind_time="
	// + remind_time + "&occur_time=" + occur_time + "&des=" + des
	// + "&kind=" + kind + "&amount=" + amount + "&purpose="
	// + purpose + "&warrant=" + warrant + "&turn_over=" + turn_over
	// + "&status=" + status;
	// }

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"occur_time\":\"" + occur_time + "\",\"des\":\"" + des
				+ "\",\"amount\":\"" + amount
				+ "\",\"warrant\":\"" + warrant + "\",\"turn_over\":\""
				+ turn_over + "\",\"status\":\"" + status + "\",\"purpose\":\""
				+ purpose + "\"}";
	}

}
