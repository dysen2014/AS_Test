package com.pactera.financialmanager.ui.model;

/**
 * 工作提醒头
 * @author Administrator
 *
 */
public class CustEvent {

	private String cust_type;  	// 对公||对私
	
	private String num;			// 提醒数量
	
	private String event_type_name;	// 二级标签名称
	
	private String event_type;	// 固定标签类型

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getEvent_type_name() {
		return event_type_name;
	}

	public void setEvent_type_name(String event_type_name) {
		this.event_type_name = event_type_name;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
}
