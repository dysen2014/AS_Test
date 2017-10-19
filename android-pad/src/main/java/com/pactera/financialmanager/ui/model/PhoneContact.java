package com.pactera.financialmanager.ui.model;

/**
 * 联系人电话信息
 * 
 * @author JAY
 * 
 */
public class PhoneContact {
	public String custid;// 客户id
	public String phone_type;// 电话类型
	public String is_frist_selected;// 是否首选
	public String contact_info;// 联系信息
	public String create_date;// 创建时间
	public String data_source;// 数据来源
	public String creatorname;// 创建人名字
	public String creator;//	创建人id
	public String pkid;// 备用字段二

	@Override
	public String toString() {
		return "{\"pkid\":\"" + pkid + "\",\"custid\":\"" + custid
				+ "\",\"phone_type\":\"" + phone_type
				+ "\",\"is_frist_selected\":\"" + is_frist_selected
				+ "\",\"contact_info\":\"" + contact_info + "\"}";
	}

}
