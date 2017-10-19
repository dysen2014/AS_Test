package com.pactera.financialmanager.ui.model;

/**
 * 其他联系方式
 * 
 * @author JAY
 * 
 */
public class OtherContact {
	
	public String custid;// 备用字段二
	public String con_type;// 联系类型
	public String is_frist_selected;// 是否首选
	public String contact_info;// 联系信息
	public String create_date;// 创建时间
	public String data_source;// 数据来源
	public String creatorname;// 创建人名字
	public String creator;//	创建人id
	public String pkid;// 备用字段二
	
	@Override
	public String toString() {
		return "{\"pkid\":\""+pkid+"\",\"custid\":\"" + custid + "\",\"con_type\":\"" + con_type
				+ "\",\"is_frist_selected\":\"" + is_frist_selected
				+ "\",\"contact_info\":\"" + contact_info + "\"}";
	}

}
