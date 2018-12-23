package com.pactera.financialmanager.ui.model;

/**
 * 客户建档关键信息（个人）
 * 
 * @author JAY
 * 
 */
public class BaseCustomerInfo {

	public  String custName = "";// 姓名
	public  String idv_gnd_id = "";// 性别
	public  String cust_psn_card_type = "";// 证件类型
	public  String cust_psn_card_number = "";// 证件号码
	public  String CONTACTNAME = "";// 联系人姓名
	public  String phone_no = "";// 手机号码
	public  boolean isEdit = true;// 是否可编辑
	public  String custID = "";// 客户ID
	public  String cust_rate = "";// 客户星级

	//对私
	public String toJsonForPerson() {
		return "{\"custName\":\"" + custName + "\",\"idv_gnd_id\":\""
				+ idv_gnd_id + "\",\"cust_psn_card_type\":\""
				+ cust_psn_card_type + "\",\"cust_psn_card_number\":\""
				+ cust_psn_card_number + "\",\"phone_no\":\"" + phone_no
				+ "\",\"custID\":\"" + custID + "\"}";

	}
	
	//对公
	public String toJsonForCompany() {
		return "{\"ENAME\":\"" + custName + "\",\"CONTACTNAME\":\""
				+ CONTACTNAME + "\",\"IDENTITYTYPE\":\""
				+ cust_psn_card_type + "\",\"IDENTITYNUM\":\""
				+ cust_psn_card_number + "\",\"PHONENUM\":\"" + phone_no
				+ "\",\"custID\":\"" + custID + "\"}";

	}
}
