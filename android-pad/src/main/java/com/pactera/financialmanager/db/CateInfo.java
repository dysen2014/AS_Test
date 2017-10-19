package com.pactera.financialmanager.db;

import java.io.Serializable;

/**
 * 对分类码信息进行保存的业务bean
 */
public class CateInfo implements Serializable{
	//分类码信息
	
	/*	{
		"cateLabel":"不可预约",
		"cateName":"bookingFlag",
		"cateValue":"0",
		"createDate":"2014-09-23 00:00:00",
		"lastUpdateDt":"2014-09-23 00:00:00",
		"validFlag":"01",
		"valueDesc":"是否可预约"
		},
		
		{"cateValue":"01",
		 "cateLabel":"代理式","valueDesc":"债券类型","validFlag":"01",
         "lastUpdateDt":"2015-05-02 11:22:34","createDate":"2015-05-02 11:22:34",
         "cateName":"bondType"},
     */
	
	private String cateLabel;
	private String cateName;
	private String cateValue;
	private String createDate;
	private String lastUpdateDt;
	private String validFlag;
	private String valueDesc;
	
	public CateInfo() {
		super();
	}
	
	public CateInfo(String cateLabel, String cateName, String cateValue,
			String createDate, String lastUpdateDt, String validFlag,
			String valueDesc) {
		super();
		this.cateLabel = cateLabel;
		this.cateName = cateName;
		this.cateValue = cateValue;
		this.createDate = createDate;
		this.lastUpdateDt = lastUpdateDt;
		this.validFlag = validFlag;
		this.valueDesc = valueDesc;
	}

	public String getCateLabel() {
		return cateLabel;
	}
	public void setCateLabel(String cateLabel) {
		this.cateLabel = cateLabel;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateValue() {
		return cateValue;
	}
	public void setCateValue(String cateValue) {
		this.cateValue = cateValue;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateDt() {
		return lastUpdateDt;
	}
	public void setLastUpdateDt(String lastUpdateDt) {
		this.lastUpdateDt = lastUpdateDt;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getValueDesc() {
		return valueDesc;
	}
	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}
	@Override
	public String toString() {
		return "CateInfo [cateLabel=" + cateLabel + ", cateName=" + cateName
				+ ", cateValue=" + cateValue + ", createDate=" + createDate
				+ ", lastUpdateDt=" + lastUpdateDt + ", validFlag=" + validFlag
				+ ", valueDesc=" + valueDesc + "]";
	}
}
