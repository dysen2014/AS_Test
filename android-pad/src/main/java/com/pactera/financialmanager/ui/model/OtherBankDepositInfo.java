package com.pactera.financialmanager.ui.model;

/**
 * 他行存款产品
 * 
 * @author JAY
 * 
 */
public class OtherBankDepositInfo {

	public String custid;// 客户号
	public String pkid;// 主键
	public String deposit_type;// 存款类型
	public String deposit_amount;// 存款余额
	public String deposit_period;// 存款期限
	public String deposit_maturity_date;// 存款到期日
	public String deposit_bank;// 存款行
	public String remind_date;// 提醒日期

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"deposit_type\":\"" + deposit_type
				+ "\",\"deposit_amount\":\"" + deposit_amount
				+ "\",\"deposit_period\":\"" + deposit_period
				+ "\",\"deposit_maturity_date\":\"" + deposit_maturity_date
				+ "\",\"deposit_bank\":\"" + deposit_bank
				+ "\",\"remind_date\":\"" + remind_date + "\"}";
	}

}
