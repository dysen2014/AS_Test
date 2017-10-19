package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 工作事项
 * 
 * @author Administrator
 *
 */
public class CustomerWork implements Serializable {

	private static final long serialVersionUID = -7112102205804400858L;

	private String CST_ID; // 客户号
	private String CST_NAME;// 客户名称
	private String ACT_ID;// 营销活动ID
	private String ACT_NAME;// 营销活动名称
	private String ACT_TYPE;// 营销活动类型
	private String START_DT;// 营销活动开始日期
	private String END_DT;// 营销活动结束日期
	private String OWERID;// 拥有者
	private String BRID;// 客户所在机构
	private String ETL_DT;// 处理日期

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

	public String getACT_ID() {
		return ACT_ID;
	}

	public void setACT_ID(String aCT_ID) {
		ACT_ID = aCT_ID;
	}

	public String getACT_NAME() {
		return ACT_NAME;
	}

	public void setACT_NAME(String aCT_NAME) {
		ACT_NAME = aCT_NAME;
	}

	public String getACT_TYPE() {
		return ACT_TYPE;
	}

	public void setACT_TYPE(String aCT_TYPE) {
		ACT_TYPE = aCT_TYPE;
	}

	public String getSTART_DT() {
		return START_DT;
	}

	public void setSTART_DT(String sTART_DT) {
		START_DT = sTART_DT;
	}

	public String getEND_DT() {
		return END_DT;
	}

	public void setEND_DT(String eND_DT) {
		END_DT = eND_DT;
	}

	public String getOWERID() {
		return OWERID;
	}

	public void setOWERID(String oWERID) {
		OWERID = oWERID;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public String getETL_DT() {
		return ETL_DT;
	}

	public void setETL_DT(String eTL_DT) {
		ETL_DT = eTL_DT;
	}

	public CustomerWork(String cST_ID, String cST_NAME, String aCT_ID,
			String aCT_NAME, String aCT_TYPE, String sTART_DT, String eND_DT,
			String oWERID, String bRID, String eTL_DT) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		ACT_ID = aCT_ID;
		ACT_NAME = aCT_NAME;
		ACT_TYPE = aCT_TYPE;
		START_DT = sTART_DT;
		END_DT = eND_DT;
		OWERID = oWERID;
		BRID = bRID;
		ETL_DT = eTL_DT;
	}

	public CustomerWork() {
		super();
	}
	
}
