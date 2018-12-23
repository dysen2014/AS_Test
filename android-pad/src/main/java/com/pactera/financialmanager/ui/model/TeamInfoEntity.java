package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 客户所属集团信息返回的数据保存的javabean
 */
public class TeamInfoEntity implements Serializable {
	private String pkid;//主键
	private String HIGHER_NAME;//上级企业名称
	private String GROUP_RELATION;//关联关系
	private String GROUP_NAME;//集团总公司名称
	private String HOLDER_PERTAGE;//持股比例
	private String DES;//备注
	public String getPkid() {
		return pkid;
	}
	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public String getHIGHER_NAME() {
		return HIGHER_NAME;
	}
	public void setHIGHER_NAME(String hIGHER_NAME) {
		HIGHER_NAME = hIGHER_NAME;
	}
	public String getGROUP_RELATION() {
		return GROUP_RELATION;
	}
	public void setGROUP_RELATION(String gROUP_RELATION) {
		GROUP_RELATION = gROUP_RELATION;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public String getHOLDER_PERTAGE() {
		return HOLDER_PERTAGE;
	}
	public void setHOLDER_PERTAGE(String hOLDER_PERTAGE) {
		HOLDER_PERTAGE = hOLDER_PERTAGE;
	}
	public String getDES() {
		return DES;
	}
	public void setDES(String dES) {
		DES = dES;
	}
	public TeamInfoEntity(String pkid, String hIGHER_NAME,
			String gROUP_RELATION, String gROUP_NAME, String hOLDER_PERTAGE,
			String dES) {
		super();
		this.pkid = pkid;
		HIGHER_NAME = hIGHER_NAME;
		GROUP_RELATION = gROUP_RELATION;
		GROUP_NAME = gROUP_NAME;
		HOLDER_PERTAGE = hOLDER_PERTAGE;
		DES = dES;
	}
	
	public TeamInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "TeamInfoEntity [pkid=" + pkid + ", HIGHER_NAME=" + HIGHER_NAME
				+ ", GROUP_RELATION=" + GROUP_RELATION + ", GROUP_NAME="
				+ GROUP_NAME + ", HOLDER_PERTAGE=" + HOLDER_PERTAGE + ", DES="
				+ DES + "]";
	}
}
