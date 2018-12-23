package com.pactera.financialmanager.ui.model;

/**
 * 不动产信息
 * 
 * @author JAY
 * 
 */
public class ImMovablesInfo {
	public String pkid = "";// 主键
	public String estate_name = "";// 不动产名称
	public String buy_date = "";// 购买时间
	public String buy_price = "";// 购置价格
	public String des = "";// 备注/其他资产
	public String custid = "";// 客户号
	public String estate_type = "";// 不动产类型
	public String estate_addr = "";// 不动产地址
	public String estate_area = "";// 不动产面积
	public String has_property_cer = "";// 是否办证


	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"estate_name\":\"" + estate_name + "\",\"buy_date\":\""
				+ buy_date + "\",\"buy_price\":\"" + buy_price
				+ "\",\"estate_type\":\"" + estate_type
				+ "\",\"estate_area\":\"" + estate_area
				+ "\",\"estate_addr\":\"" + estate_addr
				+ "\",\"has_property_cer\":\"" + has_property_cer
				+ "\",\"des\":\"" + des + "\"}";
	}

}
