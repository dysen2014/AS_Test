package com.pactera.financialmanager.ui.model;

/**
 * 他行贷款产品
 * 
 * @author JAY
 * 
 */
public class OtherBankLoanInfo {

	public String custid;// 客户号
	public String pkid;// 主键
	public String loan_type;// 贷款方式
	public String loan_period;// 贷款期限
	public String loan_rate;// 贷款利率
	public String loan_amount;// 贷款金额
	public String loan_bank;// 贷款行
	public String remind_date;// 提醒日期
	public String CREDIT_EXPIRY_DATE;// 到期日期

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"loan_type\":\"" + loan_type + "\",\"loan_period\":\""
				+ loan_period + "\",\"loan_rate\":\"" + loan_rate
				+ "\",\"loan_amount\":\"" + loan_amount + "\",\"loan_bank\":\""
				+ loan_bank +"\",\"CREDIT_EXPIRY_DATE\":\""
						+ CREDIT_EXPIRY_DATE + "\",\"remind_date\":\"" + remind_date + "\"}";
	}

}
