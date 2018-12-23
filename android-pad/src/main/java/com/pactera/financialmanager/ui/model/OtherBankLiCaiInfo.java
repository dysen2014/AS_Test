package com.pactera.financialmanager.ui.model;

/**
 * 他行理财产品
 * 
 * @author JAY
 * 
 */
public class OtherBankLiCaiInfo {

	public String custid;// 客户号
	public String pkid;// 主键
	public String prd_name;// 产品名称
	public String exp_amount;// 认购金额
	public String exp_date;// 认购日期
	public String end_date;// 到期日
	public String remind_date;// 提醒日期
	public String exp_profit;// 预期收益率

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"prd_name\":\"" + prd_name + "\",\"exp_amount\":\""
				+ exp_amount + "\",\"exp_date\":\"" + exp_date
				+ "\",\"end_date\":\"" + end_date + "\",\"remind_date\":\""
				+ remind_date + "\",\"exp_profit\":\"" + exp_profit + "\"}";
	}

}
