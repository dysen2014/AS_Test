package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户升降级
 * @author Administrator
 *
 */
public class Cusorder implements Serializable{

	private static final long serialVersionUID = -7335186681526692601L;

	private String CST_ID; // 客户号
	
	private String CST_NAME; // 客户名称
	
	private String EVENT_TYPE; // 事件类型 01 升级 02 降级  
	
	private String LAST_LEVEL; // 上次级别
	
	private String CURRENT_LEVEL; // 本次级别 
	
	private String BRID; // 客户所在机构

	public String getCST_ID() {
		return CST_ID;
	}

	public void setCST_ID(String cST_ID) {
		CST_ID = cST_ID;
	}

	public String getCST_NAME() {
		return CST_NAME;
	}

	public void setCST_NAME(String cST_NAME) {
		CST_NAME = cST_NAME;
	}

	public String getEVENT_TYPE() {
		return EVENT_TYPE;
	}

	public void setEVENT_TYPE(String eVENT_TYPE) {
		EVENT_TYPE = eVENT_TYPE;
	}

	public String getLAST_LEVEL() {
		return LAST_LEVEL;
	}

	public void setLAST_LEVEL(String lAST_LEVEL) {
		LAST_LEVEL = lAST_LEVEL;
	}

	public String getCURRENT_LEVEL() {
		return CURRENT_LEVEL;
	}

	public void setCURRENT_LEVEL(String cURRENT_LEVEL) {
		CURRENT_LEVEL = cURRENT_LEVEL;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public Cusorder() {
		super();
	}

	public Cusorder(String cST_ID, String cST_NAME, String eVENT_TYPE,
			String lAST_LEVEL, String cURRENT_LEVEL, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		EVENT_TYPE = eVENT_TYPE;
		LAST_LEVEL = lAST_LEVEL;
		CURRENT_LEVEL = cURRENT_LEVEL;
		BRID = bRID;
	}
	
}
