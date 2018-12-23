package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 固定资产查询信息返回的数据保存的Javabean
 */
public class GudingZichanEntity implements Serializable {
	private String PKID;//主键
	private String CREATE_USER;//创建人
	private String PROPERTY_TYPE;//资产类型
	private String PROPERTY_NAME;//资产名称
	private String PURCHASE_DATE;//购买日期
	private String PURCHASE_PRICE;//购买价格
	private String AREA;//平方数
	private String CERTIFIED_AREA;//已办证面积
	private String QUANTITY;//数量
	private String ACCESS_VALUE;//评估价值
	public String getPKID() {
		return PKID;
	}
	public void setPKID(String pKID) {
		PKID = pKID;
	}
	public String getCREATE_USER() {
		return CREATE_USER;
	}
	public void setCREATE_USER(String cREATE_USER) {
		CREATE_USER = cREATE_USER;
	}
	public String getPROPERTY_TYPE() {
		return PROPERTY_TYPE;
	}
	public void setPROPERTY_TYPE(String pROPERTY_TYPE) {
		PROPERTY_TYPE = pROPERTY_TYPE;
	}
	public String getPROPERTY_NAME() {
		return PROPERTY_NAME;
	}
	public void setPROPERTY_NAME(String pROPERTY_NAME) {
		PROPERTY_NAME = pROPERTY_NAME;
	}
	public String getPURCHASE_DATE() {
		return PURCHASE_DATE;
	}
	public void setPURCHASE_DATE(String pURCHASE_DATE) {
		PURCHASE_DATE = pURCHASE_DATE;
	}
	public String getPURCHASE_PRICE() {
		return PURCHASE_PRICE;
	}
	public void setPURCHASE_PRICE(String pURCHASE_PRICE) {
		PURCHASE_PRICE = pURCHASE_PRICE;
	}
	public String getAREA() {
		return AREA;
	}
	public void setAREA(String aREA) {
		AREA = aREA;
	}
	public String getCERTIFIED_AREA() {
		return CERTIFIED_AREA;
	}
	public void setCERTIFIED_AREA(String cERTIFIED_AREA) {
		CERTIFIED_AREA = cERTIFIED_AREA;
	}
	public String getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}
	public String getACCESS_VALUE() {
		return ACCESS_VALUE;
	}
	
	public void setACCESS_VALUE(String aCCESS_VALUE) {
		ACCESS_VALUE = aCCESS_VALUE;
	}
	
	public GudingZichanEntity(String pKID, String cREATE_USER,
			String pROPERTY_TYPE, String pROPERTY_NAME, String pURCHASE_DATE,
			String pURCHASE_PRICE, String aREA, String cERTIFIED_AREA,
			String qUANTITY, String aCCESS_VALUE) {
		super();
		PKID = pKID;
		CREATE_USER = cREATE_USER;
		PROPERTY_TYPE = pROPERTY_TYPE;
		PROPERTY_NAME = pROPERTY_NAME;
		PURCHASE_DATE = pURCHASE_DATE;
		PURCHASE_PRICE = pURCHASE_PRICE;
		AREA = aREA;
		CERTIFIED_AREA = cERTIFIED_AREA;
		QUANTITY = qUANTITY;
		ACCESS_VALUE = aCCESS_VALUE;
	}
	
	public GudingZichanEntity() {
		super();
	}
	
	@Override
	public String toString() {
		return "GudingZichanEntity [PKID=" + PKID + ", CREATE_USER="
				+ CREATE_USER + ", PROPERTY_TYPE=" + PROPERTY_TYPE
				+ ", PROPERTY_NAME=" + PROPERTY_NAME + ", PURCHASE_DATE="
				+ PURCHASE_DATE + ", PURCHASE_PRICE=" + PURCHASE_PRICE
				+ ", AREA=" + AREA + ", CERTIFIED_AREA=" + CERTIFIED_AREA
				+ ", QUANTITY=" + QUANTITY + ", ACCESS_VALUE=" + ACCESS_VALUE
				+ "]";
	}
	
}
