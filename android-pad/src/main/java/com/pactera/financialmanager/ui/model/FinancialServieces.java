package com.pactera.financialmanager.ui.model;

/**
 * 金融需求查询更新(对公建档)
 * 
 * @author JAY
 * 
 */
public class FinancialServieces {
	public String custid;// custid
	public String type;// 请求参数(1：update,2：获取两个数据)
	public String FUND_DEMAND;// 资金需求
	public String CEN_BUS_DEMAND;// 中间业务需求
	public String USERED_PRODUCT;// 使用我行产品

	@Override
	public String toString() {
		return "{\"custid\":\"" + custid + "\",\"type\":\"" + type
				+ "\",\"FUND_DEMAND\":\"" + FUND_DEMAND
				+ "\",\"USERED_PRODUCT\":\"" + USERED_PRODUCT
				+ "\",\"CEN_BUS_DEMAND\":\"" + CEN_BUS_DEMAND + "\"}";
	}

}
