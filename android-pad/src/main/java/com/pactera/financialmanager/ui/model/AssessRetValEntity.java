package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 客户评价查询返回的数据
 */
public class AssessRetValEntity implements Serializable {
	private String custid;//客户id
	private String  cultivale;//培植方向
	private String req_cate;//需求分类
	private String is_care;//是否关注
	private String des;//说明
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCultivale() {
		return cultivale;
	}
	public void setCultivale(String cultivale) {
		this.cultivale = cultivale;
	}
	public String getReq_cate() {
		return req_cate;
	}
	public void setReq_cate(String req_cate) {
		this.req_cate = req_cate;
	}
	public String getIs_care() {
		return is_care;
	}
	public void setIs_care(String is_care) {
		this.is_care = is_care;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public AssessRetValEntity(String custid, String cultivale, String req_cate,
			String is_care, String des) {
		super();
		this.custid = custid;
		this.cultivale = cultivale;
		this.req_cate = req_cate;
		this.is_care = is_care;
		this.des = des;
	}
	
	public AssessRetValEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "AssessRetValEntity [custid=" + custid + ", cultivale="
				+ cultivale + ", req_cate=" + req_cate + ", is_care=" + is_care
				+ ", des=" + des + "]";
	}
}
