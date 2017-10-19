package com.pactera.financialmanager.ui.model;

/**
 * 资金结算偏好与我行业务往来信息
 * 
 * @author JAY
 * 
 */
public class BusinessInfo {
	public String custid="";// custid
	public String type="";// 1：update,2：获取两个数据
	public String MONEY_SETTLE="";//我行业务往来信息
	public String REL_BUSI_INFO="";//资金结算偏好

	@Override
	public String toString() {
		return "{\"custid\":\"" + custid + "\",\"type\":\"" + type
				+ "\",\"MONEY_SETTLE\":\"" + MONEY_SETTLE
				+ "\",\"REL_BUSI_INFO\":\"" + REL_BUSI_INFO + "\"}";
	}
}
