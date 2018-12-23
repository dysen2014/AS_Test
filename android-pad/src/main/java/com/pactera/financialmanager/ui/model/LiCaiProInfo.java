package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 理财产品返回的数据
 */
public class LiCaiProInfo implements Serializable {

	private static final long serialVersionUID = -7073668425242539242L;
	
	private String oneword; // 一句话营销
	
	private String PRD_NAME; // 产品名称
	
	private String tablename; // 数据来源表名
	
	private String PRD_CODE; // 产品id
	
	private String SUP_PRD_CODE; // 父级目录

	public String getOneword() {
		return oneword;
	}

	public void setOneword(String oneword) {
		this.oneword = oneword;
	}

	public String getPRD_NAME() {
		return PRD_NAME;
	}

	public void setPRD_NAME(String pRD_NAME) {
		PRD_NAME = pRD_NAME;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getPRD_CODE() {
		return PRD_CODE;
	}

	public void setPRD_CODE(String pRD_CODE) {
		PRD_CODE = pRD_CODE;
	}

	public String getSUP_PRD_CODE() {
		return SUP_PRD_CODE;
	}

	public void setSUP_PRD_CODE(String sUP_PRD_CODE) {
		SUP_PRD_CODE = sUP_PRD_CODE;
	}

	public LiCaiProInfo(String oneword, String pRD_NAME, String tablename,
			String pRD_CODE, String sUP_PRD_CODE) {
		super();
		this.oneword = oneword;
		PRD_NAME = pRD_NAME;
		this.tablename = tablename;
		PRD_CODE = pRD_CODE;
		SUP_PRD_CODE = sUP_PRD_CODE;
	}

	public LiCaiProInfo() {
		super();
	}

	@Override
	public String toString() {
		return "LiCaiProInfo [oneword=" + oneword + ", PRD_NAME=" + PRD_NAME
				+ ", tablename=" + tablename + ", PRD_CODE=" + PRD_CODE
				+ ", SUP_PRD_CODE=" + SUP_PRD_CODE + "]";
	}
}
