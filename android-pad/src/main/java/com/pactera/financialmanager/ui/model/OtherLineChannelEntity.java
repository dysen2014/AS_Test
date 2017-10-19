package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 分流渠道返回的数据
 */
public class OtherLineChannelEntity implements Serializable {
	private String type;//业务类型ID
	private String desc;//业务类型描述
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public OtherLineChannelEntity(String type, String desc) {
		super();
		this.type = type;
		this.desc = desc;
	}
	public OtherLineChannelEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "OtherLineChannelEntity [type=" + type + ", desc=" + desc + "]";
	}

}
