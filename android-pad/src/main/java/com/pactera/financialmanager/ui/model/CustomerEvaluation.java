package com.pactera.financialmanager.ui.model;

/**
 * 客户评价
 * 
 * @author JAY
 * 
 */
public class CustomerEvaluation {
	
	public String cultivale;// 培植方向
	public String req_cate;// 需求分类
	public String is_care;// 是否关注
	public String des;// 说明
	public String custid;// 客户ID
	
//	@Override
//	public String toString() {
//		return "&custid=" + custid + "&cultivale=" + cultivale
//				+ "&req_cate=" + req_cate + "&des=" + des
//				+ "&is_care=" + is_care;
//	}

	
	@Override
	public String toString() {
		return "{\"custid\":\"" + custid
				+ "\",\"cultivale\":\"" + cultivale
				+ "\",\"req_cate\":\"" + req_cate
				+ "\",\"des\":\"" + des
				+ "\",\"is_care\":\"" + is_care+
				"\"}";
	}
	
}
