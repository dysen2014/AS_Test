package com.pactera.financialmanager.ui.model;

/**
 * 家庭信息
 * 
 * @author JAY
 * 
 */
public class HomeInfo {
	public String custID = "";// 客户ID
	public String family_num = "";// 家庭人口数
	public String support_num = "";// 供养人数
	public String earning_source = "";// 经济来源
	public String harf_year_earning = "";// 上年家庭总收入
	public String harf_year_consume = "";// 上年家庭总支出
	public String family_asset = "";// 家庭资产总额
	public String finance_asset = "";// 金融资产
	public String account_recv = "";// 应收账款
	public String rest_asset = "";// 其他资产
	public String family_debt = "";// 家庭负债总额
	public String local_loan = "";// 本行贷款
	public String other_loan = "";// 他行贷款
	public String bad_loan = "";// 不良贷款情况
	public String other_burden = "";// 其他负债
	public String family_addr = "";//家庭住址

	@Override
	public String toString() {
		String result = "{\"custID\":\"" + custID + "\",\"family_num\":\"" + family_num
				+ "\",\"support_num\":\"" + support_num
				+ "\",\"family_addr\":\"" + family_addr
				+ "\",\"earning_source\":\"" + earning_source
				+ "\",\"harf_year_earning\":\"" + harf_year_earning
				+ "\",\"harf_year_consume\":\"" + harf_year_consume
				+ "\",\"family_asset\":\"" + family_asset
				+ "\",\"finance_asset\":\"" + finance_asset
				+ "\",\"account_recv\":\"" + account_recv
				+ "\",\"rest_asset\":\"" + rest_asset + "\",\"family_debt\":\""
				+ family_debt + "\",\"local_loan\":\"" + local_loan
				+ "\",\"other_loan\":\"" + other_loan + "\",\"bad_loan\":\""
				+ bad_loan + "\",\"other_burden\":\"" + other_burden + "\"}";
		return result;
	}
}
