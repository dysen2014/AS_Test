package com.pactera.financialmanager.ui.model;
/**
 * 联系地址信息
 * @author JAY
 *
 */
public class AddressContact {
	public String custid;// 客户id
	public String addr_type;// 电话类型
	public String is_frist_selected;// 是否首选
	public String contact_info;// 联系信息
	public String create_date;// 创建时间
	public String data_source;// 数据来源
	public String creatorname;// 创建人名字
	public String creator;//	创建人id
	public String pkid;// 备用字段二
	
	@Override
	public String toString() {
		return "{\"pkid\":\""+pkid+"\",\"custid\":\"" + custid + "\",\"addr_type\":\""
				+ addr_type + "\",\"is_frist_selected\":\""
				+ is_frist_selected + "\",\"contact_info\":\"" + contact_info
				+ "\"}";
	}

}
