package com.pactera.financialmanager.ui.model;

/**
 * 动产信息
 * 
 * @author JAY
 * 
 */
public class MovablesInfo {

	public String pkid = "";// 主键
	public String movable_name = "";// 动产名称
	public String buy_date = "";// 购买时间
	public String buy_price = "";// 购置价格
	public String des = "";// 备注/其他资产
	public String custid = "";// 客户号

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid+ "\",\"movable_name\":\"" + movable_name
				+ "\",\"buy_date\":\"" + buy_date + "\",\"buy_price\":\""
				+ buy_price + "\",\"des\":\"" + des + "\"}";
	}

}
