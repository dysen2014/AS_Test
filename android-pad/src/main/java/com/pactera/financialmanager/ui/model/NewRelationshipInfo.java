package com.pactera.financialmanager.ui.model;

/**
 * 客户关系人（修改后）
 * 
 * @author JAY
 * 
 */
public class NewRelationshipInfo {

	public String pkid = "";// 主键
	public String custid = "";// 客户ID
	public String rel_name = "";// 关系人姓名
	public String rel_type = "";// 关系人类型
	public String rel_phone = "";// 联系电话
	public String rel_cer_no = "";// 关系人证件号码
	public String rel_cer_type = "";// 关系人证件号码类型
	public String family_addr = "";// 联系地址
	public String is_client = "";// 是否为本行客户
	public String is_holder = "";// 是否为本行股东


	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"rel_name\":\"" + rel_name + "\",\"rel_type\":\""
				+ rel_type + "\",\"rel_cer_type\":\"" + rel_cer_type
				+ "\",\"rel_phone\":\"" + rel_phone + "\",\"rel_cer_no\":\""
				+ rel_cer_no + "\",\"is_client\":\"" + is_client
				+ "\",\"family_addr\":\"" + family_addr
				+ "\",\"is_holder\":\"" + is_holder + "\"}";
	}
}
